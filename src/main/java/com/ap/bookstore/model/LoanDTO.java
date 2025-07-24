package com.ap.bookstore.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LoanDTO {
    private Long id;
    private BookDTO book;
    private UserDTO user;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private boolean returned;

    @Data
    public static class BookDTO {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private boolean available;
    }

    @Data
    public static class UserDTO {
        private Long id;
        private String name;
        private String email;
    }

    public static LoanDTO fromLoan(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setLoanDate(loan.getLoanDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setReturned(loan.isReturned());

        if (loan.getBook() != null) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(loan.getBook().getId());
            bookDTO.setTitle(loan.getBook().getTitle());
            bookDTO.setAuthor(loan.getBook().getAuthor());
            bookDTO.setIsbn(loan.getBook().getIsbn());
            bookDTO.setAvailable(loan.getBook().isAvailable());
            dto.setBook(bookDTO);
        }

        if (loan.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(loan.getUser().getId());
            userDTO.setName(loan.getUser().getName());
            userDTO.setEmail(loan.getUser().getEmail());
            dto.setUser(userDTO);
        }

        return dto;
    }
} 