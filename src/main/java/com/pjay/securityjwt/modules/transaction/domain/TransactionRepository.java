package com.pjay.securityjwt.modules.transaction.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, Dao {
}
