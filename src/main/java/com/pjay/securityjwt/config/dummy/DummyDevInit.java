package com.pjay.securityjwt.config.dummy;

import com.pjay.securityjwt.modules.account.domain.AccountRepository;
import com.pjay.securityjwt.modules.user.domain.User;
import com.pjay.securityjwt.modules.user.domain.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DummyDevInit extends DummyObject{

    @Profile("dev") // prod 모드에서는 실행되면 안된다.
    @Bean
    CommandLineRunner init(UserRepository userRepository, AccountRepository accountRepository){
        return args -> {
          // 서버 실행시에 무조건 실행된다.
            User pjay = userRepository.save(newUser("pjay", "피제이"));
            User test = userRepository.save(newUser("test", "테스트"));
            accountRepository.save(newAccount(1111L, pjay));
            accountRepository.save(newAccount(2222L, pjay));
            accountRepository.save(newAccount(3333L, test));
        };
    }

}
