package com.pjay.securityjwt.modules.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // jpa query method
    // select * from account where number = :number
    // TODO : 리팩토링 해야함
    Optional<Account> findByNumber(Long number);

}
