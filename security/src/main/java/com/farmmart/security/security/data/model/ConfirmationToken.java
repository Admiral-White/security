package com.farmmart.security.security.data.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String confirmationToken;
    private LocalDate createdDate;
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;


    ConfirmationToken(User user) {
        this.confirmationToken = UUID.randomUUID().toString();
        this.createdDate = LocalDate.now();
        this.user = user;
    }
}
