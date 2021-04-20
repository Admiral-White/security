package com.farmmart.security.security.service;

import com.farmmart.security.security.data.model.ConfirmationToken;
import com.farmmart.security.security.data.model.User;
import com.farmmart.security.security.data.repository.ConfirmationTokenRepository;
import com.farmmart.security.security.data.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    UserRepository userRepository;

    ConfirmationTokenService confirmationTokenService;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken){

        confirmationTokenRepository.save(confirmationToken);
    }

    public void deleteConfirmationToken(Long id){

        confirmationTokenRepository.deleteById(id);

    }

    public void confirmUser(ConfirmationToken confirmationToken){

        final User user = confirmationToken.getUser();

        user.setEnabled(true);

        userRepository.save(user);

        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());


    }


}
