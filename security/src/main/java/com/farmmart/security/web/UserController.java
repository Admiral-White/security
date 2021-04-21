package com.farmmart.security.web;

import com.farmmart.security.security.service.ConfirmationTokenService;
import com.farmmart.security.security.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {

    private final UserService userService;

    private final ConfirmationTokenService confirmationTokenService;

    public UserController(UserService userService, ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
    }


    @GetMapping("/sign-in")
    public String signIn(){

        return "sign-in";
    }


}
