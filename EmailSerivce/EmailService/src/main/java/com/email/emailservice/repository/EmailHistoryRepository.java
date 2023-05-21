package com.email.emailservice.repository;

import com.email.emailservice.model.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailHistoryRepository extends JpaRepository<EmailHistory, Long> {
}
