package com.farmmart.security.security.configuration;


import com.farmmart.security.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final UserService userService;


    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                .antMatchers("/sign-up/**", "/sign-in/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/sign-in")
                .permitAll();


    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{


        authenticationManagerBuilder.userDetailsService(userService);


//        authenticationManagerBuilder.passwordEncoder(bCryptPasswordEncoder());

    }

}
