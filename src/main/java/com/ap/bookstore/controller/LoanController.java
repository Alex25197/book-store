package com.ap.bookstore.controller;

import com.ap.bookstore.model.Loan;
import com.ap.bookstore.model.LoanDTO;
import com.ap.bookstore.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loan", description = "Loan management APIs")
public class LoanController {
    
    @Autowired
    private LoanService loanService;
    
    @Operation(
        summary = "Create a new loan",
        description = "Creates a new loan for a user to borrow a book"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Loan created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or user already has an active loan"),
        @ApiResponse(responseCode = "404", description = "User or book not found")
    })
    @PostMapping
    public ResponseEntity<Loan> createLoan(
        @Parameter(description = "ID of the user borrowing the book") @RequestParam Long userId,
        @Parameter(description = "ID of the book being borrowed") @RequestParam Long bookId
    ) {
        return ResponseEntity.ok(loanService.createLoan(userId, bookId));
    }
    
    @Operation(
        summary = "Return a book",
        description = "Marks a loan as returned and makes the book available again"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book returned successfully"),
        @ApiResponse(responseCode = "400", description = "Book already returned"),
        @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    @PostMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(
        @Parameter(description = "ID of the loan to return") @PathVariable Long id
    ) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }
    
    @Operation(
        summary = "Get active loan by user",
        description = "Returns the active loan for a specific user"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Active loan found"),
        @ApiResponse(responseCode = "404", description = "No active loan found for user")
    })
    @GetMapping("/users/{userId}/active")
    public ResponseEntity<Loan> getActiveLoanByUser(
        @Parameter(description = "ID of the user to check") @PathVariable Long userId
    ) {
        return ResponseEntity.ok(loanService.getActiveLoanByUser(userId));
    }

    @Operation(
        summary = "Get loan by ID",
        description = "Returns a loan and its associated book information based on the loan ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Loan found successfully"),
        @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(
        @Parameter(description = "ID of the loan to retrieve") @PathVariable Long id
    ) {
        Loan loan = loanService.getLoanById(id);
        return ResponseEntity.ok(LoanDTO.fromLoan(loan));
    }
} 