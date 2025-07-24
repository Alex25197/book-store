package com.ap.bookstore.service;

import com.ap.bookstore.exception.ResourceNotFoundException;
import com.ap.bookstore.model.Book;
import com.ap.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book", "id", id);
        }
        bookRepository.deleteById(id);
    }
    
    @Transactional
    public void updateBookAvailability(Long id, boolean available) {
        Book book = getBookById(id);
        book.setAvailable(available);
        bookRepository.save(book);
    }
} 