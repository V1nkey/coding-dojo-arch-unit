package com.example.demo.cleanarchi.domain.usecases.loan.interactors;

import com.example.demo.cleanarchi.domain.model.Book;
import com.example.demo.cleanarchi.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class LoanControl {

    public boolean isBookAlreadyLent(Book book) {
        return false;
    }

    public boolean isUserAuthorized(User user) {
        return true;
    }
}
