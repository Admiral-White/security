package com.farmmart.security.security.service;


import com.farmmart.security.security.data.model.ConfirmationToken;
import com.farmmart.security.security.data.model.User;
import com.farmmart.security.security.data.repository.ConfirmationTokenRepository;
import com.farmmart.security.security.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;


public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    ConfirmationTokenService confirmationTokenService;




    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {


    }


//    public UserService() {
//
//        userRepository = new UserRepository() {
//            @Override
//            public Optional<User> findByEmail(String email) {
//                return Optional.empty();
//            }
//
//            @Override
//            public List<User> findAll() {
//                return null;
//            }
//
//            @Override
//            public List<User> findAll(Sort sort) {
//                return null;
//            }
//
//            @Override
//            public List<User> findAllById(Iterable<Long> iterable) {
//                return null;
//            }
//
//            @Override
//            public <S extends User> List<S> saveAll(Iterable<S> iterable) {
//                return null;
//            }
//
//            @Override
//            public void flush() {
//
//            }
//
//            @Override
//            public <S extends User> S saveAndFlush(S s) {
//                return null;
//            }
//
//            @Override
//            public void deleteInBatch(Iterable<User> iterable) {
//
//            }
//
//            @Override
//            public void deleteAllInBatch() {
//
//            }
//
//            @Override
//            public User getOne(Long aLong) {
//                return null;
//            }
//
//            @Override
//            public <S extends User> List<S> findAll(Example<S> example) {
//                return null;
//            }
//
//            @Override
//            public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
//                return null;
//            }
//
//            @Override
//            public Page<User> findAll(Pageable pageable) {
//                return null;
//            }
//
//            @Override
//            public <S extends User> S save(S s) {
//                return null;
//            }
//
//            @Override
//            public Optional<User> findById(Long aLong) {
//                return Optional.empty();
//            }
//
//            @Override
//            public boolean existsById(Long aLong) {
//                return false;
//            }
//
//            @Override
//            public long count() {
//                return 0;
//            }
//
//            @Override
//            public void deleteById(Long aLong) {
//
//            }
//
//            @Override
//            public void delete(User user) {
//
//            }
//
//            @Override
//            public void deleteAll(Iterable<? extends User> iterable) {
//
//            }
//
//            @Override
//            public void deleteAll() {
//
//            }
//
//            @Override
//            public <S extends User> Optional<S> findOne(Example<S> example) {
//                return Optional.empty();
//            }
//
//            @Override
//            public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
//                return null;
//            }
//
//            @Override
//            public <S extends User> long count(Example<S> example) {
//                return 0;
//            }
//
//            @Override
//            public <S extends User> boolean exists(Example<S> example) {
//                return false;
//            }
//        };
//    }

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
