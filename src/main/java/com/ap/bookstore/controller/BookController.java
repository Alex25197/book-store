package com.ap.bookstore.controller;

import com.ap.bookstore.model.Book;
import com.ap.bookstore.service.BookService;
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
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "Book management APIs")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @Operation(
        summary = "Create a new book",
        description = "Creates a new book with the provided information"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }
    
    @Operation(
        summary = "Get a book by ID",
        description = "Returns a book based on the ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book found successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
        @Parameter(description = "ID of the book to retrieve") @PathVariable Long id
    ) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
    
    @Operation(
        summary = "Get all books",
        description = "Returns a list of all books"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of books retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    
    @Operation(
        summary = "Delete a book",
        description = "Deletes a book based on the ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
        @Parameter(description = "ID of the book to delete") @PathVariable Long id
    ) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
} 