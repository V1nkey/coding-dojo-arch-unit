package com.example.demo.cleanarchi.domain.usecases.loan;

import com.example.demo.cleanarchi.domain.model.Book;
import com.example.demo.cleanarchi.domain.model.User;
import com.example.demo.cleanarchi.domain.usecases.loan.interactors.LoanControl;
import org.springframework.stereotype.Component;

@Component
public class LoanBook {

    private final LoanControl loanControl;

    public LoanBook(LoanControl loanControl) {
        this.loanControl = loanControl;
    }

    public void execute(User user, Book book) {
        if (loanControl.isBookAlreadyLent(book) || !loanControl.isUserAuthorized(user)) {
            // Stop process and log error
        }

        // Continue process
    }
}
