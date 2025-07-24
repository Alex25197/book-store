package com.ap.bookstore.service;

import com.ap.bookstore.exception.BusinessException;
import com.ap.bookstore.exception.ResourceNotFoundException;
import com.ap.bookstore.model.Book;
import com.ap.bookstore.model.Loan;
import com.ap.bookstore.model.User;
import com.ap.bookstore.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    @Transactional
    public Loan createLoan(Long userId, Long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);
        
        // Check if user has active loans
        if (loanRepository.findByUserAndReturnedFalse(user).isPresent()) {
            throw new BusinessException("User already has an active loan");
        }
        
        // Check if book is available
        if (!book.isAvailable()) {
            throw new BusinessException("Book is not available for loan");
        }
        
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        
        // Update book availability
        bookService.updateBookAvailability(bookId, false);
        
        return loanRepository.save(loan);
    }
    
    @Transactional
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "id", loanId));
            
        if (loan.isReturned()) {
            throw new BusinessException("Book has already been returned");
        }
        
        loan.setReturned(true);
        loan.setReturnDate(LocalDateTime.now());
        
        // Update book availability
        bookService.updateBookAvailability(loan.getBook().getId(), true);
        
        return loanRepository.save(loan);
    }
    
    public Loan getActiveLoanByUser(Long userId) {
        User user = userService.getUserById(userId);
        return loanRepository.findByUserAndReturnedFalse(user)
            .orElseThrow(() -> new ResourceNotFoundException("Active loan", "userId", userId));
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "id", id));
    }
} 