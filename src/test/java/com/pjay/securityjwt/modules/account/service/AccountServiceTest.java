package com.pjay.securityjwt.modules.account.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjay.securityjwt.config.dummy.DummyObject;
import com.pjay.securityjwt.enum_package.TransactionType;
import com.pjay.securityjwt.handler.ex.CustomApiException;
import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.account.domain.AccountRepository;
import com.pjay.securityjwt.modules.account.dto.request.AccountDepositReqDto;
import com.pjay.securityjwt.modules.account.dto.request.AccountSaveReqDto;
import com.pjay.securityjwt.modules.account.dto.request.AccountTransferReqDto;
import com.pjay.securityjwt.modules.account.dto.response.AccountDepositRespDto;
import com.pjay.securityjwt.modules.account.dto.response.AccountListRespDto;
import com.pjay.securityjwt.modules.account.dto.response.AccountSaveRespDto;
import com.pjay.securityjwt.modules.transaction.domain.Transaction;
import com.pjay.securityjwt.modules.transaction.domain.TransactionRepository;
import com.pjay.securityjwt.modules.user.domain.User;
import com.pjay.securityjwt.modules.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest extends DummyObject {

    @InjectMocks // 모든 Mock들이 InjectMocks 로 주입됨
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Spy // 진짜 객체를 InjectMocks에 주입한다.
    private ObjectMapper om;

    @Test
    public void addAccountTest() throws  Exception {
        // given
        Long userId = 1L;

        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(1111L);
        accountSaveReqDto.setPassword(1234L);

        // stub 1
        User user = newMockUser(userId, "pjay", "피제이");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        // stub 2
        when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());

        // stub 3
        Account account = newMockAccount(1L, 1111L, user, 1000L);
        when(accountRepository.save(any())).thenReturn(account);

        // when
        AccountSaveRespDto accountSaveRespDto = accountService.addAccount(userId, accountSaveReqDto);
        String responseBody = om.writeValueAsString(accountSaveRespDto);
        System.out.println("테스트 : "+responseBody);
        // then
        assertThat(accountSaveRespDto.getNumber()).isEqualTo(1111L);
    }

    @Test
    public void accountListByUserTest() throws Exception {
        // given
        Long userId = 1L;

        // stub
        User user = newMockUser(userId, "pjay", "피제이");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        List<Account> accountList = new ArrayList<>();
        for(int i = 1; i <= 3 ; i++){
            accountList.add(newMockAccount(Long.valueOf(i), Long.valueOf(1110+i), user, 1000L));
        }
        when(accountRepository.findByUser_Id(userId)).thenReturn(accountList);

        // when
        AccountListRespDto accountListRespDto = accountService.accountListByUser(userId);
        String responseBody = om.writeValueAsString(accountListRespDto);
        System.out.println("테스트 : "+responseBody);

        // then
        assertThat(accountListRespDto.getFullname()).isEqualTo("피제이");
        assertThat(accountListRespDto.getAccounts().size()).isEqualTo(3);
    }

    @Test
    public void deleteAccountTest() throws Exception {
        // given
        Long number = 1111L;
        Long userId = 2L;

        // stub
        User user = newMockUser(1L, "pjay", "피제이");
        Account account = newMockAccount(1L, number, user, 1000L);
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(account));

        // deleteById 의 경우 stub으로 정의할 필요가 없다.(리턴이 없어 기대값이 없기 때문에)

        // when
        assertThrows(CustomApiException.class, () -> accountService.deleteAccount(number, userId));

        // then
    }

    // 스텁마다 mock 을 새로만들기
    // 완벽한 테스트는 존재할 수 없다. 꼼꼼하게 값을 테스트해봐야 한다.
    @Test
    public void depositTest() throws Exception {
        // given
        AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun(TransactionType.DEPOSIT.name());
        accountDepositReqDto.setTel("01011112222");


        // stub 1
        User user1 = newMockUser(1L, "pjay", "피제이");
        Account account1 = newMockAccount(1L, 1111L, user1, 1000L); // account1 -> 1000원
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(account1)); // 실행안됨


        // stub 2 (스텁이 진행될 때 마다 연관된 객체는 새로 만들어서 주입하기 - 타이밍 때문에 꼬인다)
        User user2 = newMockUser(1L, "pjay", "피제이");
        Account account2 = newMockAccount(1L, 1111L, user2, 1000L);
        Transaction transaction = newMockDepositTransaction(1L, account2); // account1 -> 1100원, transaction -> 1100원
        when(transactionRepository.save(any())).thenReturn(transaction); // 실행안됨

        // when
        AccountDepositRespDto accountDepositRespDto = accountService.deposit(accountDepositReqDto); // 여기서 when 이 실행됨
        System.out.println(accountDepositRespDto.getTransaction().getDepositAccountBalance());
        System.out.println(account1.getBalance());
        String responseBody = om.writeValueAsString(accountDepositRespDto);
        System.out.println("테스트 : "+responseBody);

        // then
        assertThat(account1.getBalance()).isEqualTo(1100L);
        assertThat(accountDepositRespDto.getTransaction().getDepositAccountBalance()).isEqualTo(1100L);
    }


    // 위에서는 정석적인 서비스 테스트(테크닉)
    // 진짜 서비스 테스트를 하고 싶으면, 내가 지금 무엇을 여기서 테스트해야할지 명확히 구분(책임 분리)
    // DTO를 만드는 책임 -> 서비스에 있지만 서비스에서 DTO검증 안할래! - Controller 테스트 해볼 것이니까
    // DB관련된 것도 -> 서비스 것이 아니다.
    // DB관련된 것을 조회했을 때, 그 값을 통해서 어떤 비지니스 로직이 흘러가는 것이 있으면 -> stub으로 정의해서 테스트 해보면 된다.
    @Test
    public void depositTest2() throws Exception {
        // given
        Account account = newMockAccount(1L, 1111L, null, 1000L);
        Long amount = 0L;
        // when
        if(amount <= 0L){
            throw new CustomApiException("0원 이하의 금액을 입력할 수 없습니다");
        }

        account.deposit(100L);

        // then
        assertThat(account.getBalance()).isEqualTo(1100L);
    }

    // 계좌 출금 테스트
    @Test
    public void 계좌출금_test() throws Exception {
        // given
        Long amount = 100L;
        Long password = 1234L;
        Long userId = 1L;

        User pjay = newMockUser(1L, "pjay", "피제이");
        Account pjayAccount = newMockAccount(1L, 1111L, pjay, 1000L);

        // when
        // 0원 체크
        if(amount <= 0L){
            throw new CustomApiException("0원 이하의 금액을 출금할 수 없습니다");
        }
        // 출금 소유자 확인
        pjayAccount.checkOwner(userId);
        // 비밀번호 확인
        pjayAccount.checkSamePassword(password);
        // 잔액확인
        pjayAccount.checkBalance(amount);
        // 출급하기
        pjayAccount.withdraw(amount);

        System.out.println(pjayAccount.getBalance());
        // then
        assertThat(pjayAccount.getBalance()).isEqualTo(900L);

    }

    // 계좌 이체 테스트
    @Test
    public void transfer_test() throws Exception {
        // given
        Long userId = 1L;
        AccountTransferReqDto accountTransferReqDto = new AccountTransferReqDto();
        accountTransferReqDto.setWithdrawNumber(1111L);
        accountTransferReqDto.setDepositNumber(2222L);
        accountTransferReqDto.setWithdrawPassword(1234L);
        accountTransferReqDto.setAmount(100L);
        accountTransferReqDto.setGubun("TRANSFER");

        User pjay = newMockUser(1L, "pjay", "피제이");
        User ssar = newMockUser(2L, "ssar", "쌀");
        Account withdrawAccount = newMockAccount(1L, 1111L, pjay, 1000L);
        Account depositAccount = newMockAccount(2L, 2222L, ssar, 1000L);

        // when
        // 출금계좌와 입금계좌가 동일하면 안됨
        if(accountTransferReqDto.getWithdrawNumber().longValue() == accountTransferReqDto.getDepositNumber().longValue()){
            throw new CustomApiException("입출금계좌가 동일할 수 없습니다");
        }
        // 0원 체크
        if(accountTransferReqDto.getAmount() <= 0L){
            throw new CustomApiException("0원 이하의 금액을 입력할 수 없습니다");
        }
        // 출금 소유자 확인 (로그인한 사람과 동일한지)
        withdrawAccount.checkOwner(userId);
        // 출금계좌 비밀번호 확인
        withdrawAccount.checkSamePassword(accountTransferReqDto.getWithdrawPassword());
        // 출금계좌 잔액 확인
        withdrawAccount.checkBalance(accountTransferReqDto.getAmount());
        // 이체하기
        withdrawAccount.withdraw(accountTransferReqDto.getAmount());
        depositAccount.deposit(accountTransferReqDto.getAmount());

        // then
        assertThat(withdrawAccount.getBalance()).isEqualTo(900L);
        assertThat(depositAccount.getBalance()).isEqualTo(1100L);
    }
}
