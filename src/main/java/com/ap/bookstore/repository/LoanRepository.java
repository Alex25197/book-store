package com.ap.bookstore.repository;

import com.ap.bookstore.model.Loan;
import com.ap.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByUserAndReturnedFalse(User user);
} 