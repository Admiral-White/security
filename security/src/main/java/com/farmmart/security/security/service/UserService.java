package com.farmmart.security.security.service;


import com.farmmart.security.security.data.model.User;
import com.farmmart.security.security.data.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.MessageFormat;
import java.util.Optional;

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;



    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    }
}
