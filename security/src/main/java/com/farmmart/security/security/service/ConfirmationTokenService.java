package com.farmmart.security.security.service;

import com.farmmart.security.security.data.model.ConfirmationToken;
import com.farmmart.security.security.data.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken){

        confirmationTokenRepository.save(confirmationToken);
    }
}
