package com.farmmart.security.security.service;


import com.farmmart.security.security.data.model.ConfirmationToken;
import com.farmmart.security.security.data.model.User;
import com.farmmart.security.security.data.repository.ConfirmationTokenRepository;
import com.farmmart.security.security.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.MessageFormat;
import java.util.Optional;

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    ConfirmationTokenService confirmationTokenService;




    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    public UserService() {

        userRepository = null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        else{

            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
        }
    }

    public void signUpUser(User user){

        final String encryptPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encryptPassword);

        final User createdUser = userRepository.save(user);

        final ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);


    }

    public void sendConfirmationMail(String userMail, String token){


        EmailSenderService emailSenderService = new EmailSenderService();

        final SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(userMail);

        mailMessage.setSubject("Mail confirmation link");

        mailMessage.setFrom("<MAIL>");

        mailMessage.setText("Thanks for registering. please click on the link below to activate your account." + "http://localhost:8080/sign-up/confirm?token=" + token);

        emailSenderService.sendEmail(mailMessage);

    }

    public void confirmUser(ConfirmationToken confirmationToken) {

        final User user = confirmationToken.getUser();

        user.setEnabled(true);

        userRepository.save(user);

        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());

    }
}
