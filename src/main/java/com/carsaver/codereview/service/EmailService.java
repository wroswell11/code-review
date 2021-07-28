package com.carsaver.codereview.service;

import com.carsaver.codereview.model.User;
import com.carsaver.codereview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    public void sendConfirmation(String email) {
        System.out.println("sending confirmation to " + email);
    }

}
