package com.pjay.securityjwt.modules.transaction.domain;

import com.pjay.securityjwt.config.dummy.DummyObject;
import com.pjay.securityjwt.enum_package.TransactionType;
import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.account.domain.AccountRepository;
import com.pjay.securityjwt.modules.user.domain.User;
import com.pjay.securityjwt.modules.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;

@ActiveProfiles("test")
@DataJpaTest // DB 관련된 Bean이 다 올라온다.
public class TransactionRepositoryImplTest extends DummyObject {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        autoincrementReset();
        dataSetting();
    }

    @Test
    public void findTransactionList_all_test(){
        //given
        Long accountId = 1L;

        //when
        List<Transaction> transactionListPS = transactionRepository.findTransactionList(accountId, "ALL", 0);
        transactionListPS.forEach(transaction -> {
            System.out.println("테스트 : id : "+transaction.getId());
            System.out.println("테스트 : amount : "+transaction.getAmount());
            System.out.println("테스트 : receiver : "+transaction.getReceiver());
            System.out.println("테스트 : sender : "+transaction.getSender());
            System.out.println("테스트 : depositAccount 잔액 : "+transaction.getDepositAccountBalance());
            System.out.println("테스트 : withdrawAccount 잔액 "+transaction.getWithdrawAccountBalance());
            System.out.println("테스트 : ==================================");
        });
    }


    @Test
    public void dataJpa_test1(){
        List<Transaction> transactionList = transactionRepository.findAll();
        transactionList.forEach(transaction -> {
            System.out.println("테스트 : "+transaction.getId());
            System.out.println("테스트 : "+transaction.getSender());
            System.out.println("테스트 : "+transaction.getReceiver());
            System.out.println("테스트 : "+transaction.getGubun());
            System.out.println("테스트 : ===========================");
        });
    }

    @Test
    public void dataJpa_test2(){
        List<Transaction> transactionList = transactionRepository.findAll();
        transactionList.forEach(transaction -> {
            System.out.println("테스트 : "+transaction.getId());
            System.out.println("테스트 : "+transaction.getSender());
            System.out.println("테스트 : "+transaction.getReceiver());
            System.out.println("테스트 : "+transaction.getGubun());
            System.out.println("테스트 : ===========================");
        });
    }

    private void dataSetting() {
        User ssar = userRepository.save(newUser("ssar", "쌀"));
        User cos = userRepository.save(newUser("cos", "코스,"));
        User love = userRepository.save(newUser("love", "러브"));
        User admin = userRepository.save(newUser("admin", "관리자"));

        Account ssarAccount1 = accountRepository.save(newAccount(1111L, ssar));
        Account cosAccount = accountRepository.save(newAccount(2222L, cos));
        Account loveAccount = accountRepository.save(newAccount(3333L, love));
        Account ssarAccount2 = accountRepository.save(newAccount(4444L, ssar));

        Transaction withdrawTransaction1 = transactionRepository
                .save(newWithdrawTransaction(ssarAccount1, accountRepository));
        Transaction depositTransaction1 = transactionRepository
                .save(newDepositTransaction(cosAccount, accountRepository));
        Transaction transferTransaction1 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, cosAccount, accountRepository));
        Transaction transferTransaction2 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, loveAccount, accountRepository));
        Transaction transferTransaction3 = transactionRepository
                .save(newTransferTransaction(cosAccount, ssarAccount1, accountRepository));
    }

    private void autoincrementReset(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE account_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE transaction_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }
}
