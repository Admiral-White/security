package com.farmmart.security.web;

import com.farmmart.security.security.data.model.ConfirmationToken;
import com.farmmart.security.security.data.model.User;
import com.farmmart.security.security.data.repository.ConfirmationTokenRepository;
import com.farmmart.security.security.service.ConfirmationTokenService;
import com.farmmart.security.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
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

    @GetMapping("/sign-up")
    public String signup(){

        return "sign-up";

    }


    @PostMapping("/sign-up")
    public String signUp(User user){

        userService.signUpUser(user);

        return "redirect:/sign-in";
    }

    @GetMapping("/confirm")
    public String confirmMail(@RequestParam("token") String token){


        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

        optionalConfirmationToken.ifPresent(userService::confirmUser);

        return "/sign-in";


    }


}
