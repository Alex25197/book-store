package com.ap.bookstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    
    @JsonProperty("Name")
    private String name;
    
    @JsonProperty("Email")
    private String email;
    
    private LocalDateTime createdAt;
    private List<LoanSummaryDTO> loans;

    @Data
    public static class LoanSummaryDTO {
        private Long id;
        private BookSummaryDTO book;
        private LocalDateTime loanDate;
        private LocalDateTime returnDate;
        private boolean returned;
    }

    @Data
    public static class BookSummaryDTO {
        private Long id;
        private String title;
        private String author;
        private String isbn;
    }

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        
        if (user.getLoans() != null) {
            dto.setLoans(user.getLoans().stream()
                .map(loan -> {
                    LoanSummaryDTO loanDTO = new LoanSummaryDTO();
                    loanDTO.setId(loan.getId());
                    loanDTO.setLoanDate(loan.getLoanDate());
                    loanDTO.setReturnDate(loan.getReturnDate());
                    loanDTO.setReturned(loan.isReturned());
                    
                    if (loan.getBook() != null) {
                        BookSummaryDTO bookDTO = new BookSummaryDTO();
                        bookDTO.setId(loan.getBook().getId());
                        bookDTO.setTitle(loan.getBook().getTitle());
                        bookDTO.setAuthor(loan.getBook().getAuthor());
                        bookDTO.setIsbn(loan.getBook().getIsbn());
                        loanDTO.setBook(bookDTO);
                    }
                    
                    return loanDTO;
                })
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
} 