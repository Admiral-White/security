package com.farmmart.security.security.data.repository;

import com.farmmart.security.security.data.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {


}
