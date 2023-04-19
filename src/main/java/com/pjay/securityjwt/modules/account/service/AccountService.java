package com.pjay.securityjwt.modules.account.service;

import com.pjay.securityjwt.enum_package.TransactionType;
import com.pjay.securityjwt.handler.ex.CustomApiException;
import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.account.domain.AccountRepository;
import com.pjay.securityjwt.modules.account.dto.request.AccountDepositReqDto;
import com.pjay.securityjwt.modules.account.dto.request.AccountSaveReqDto;
import com.pjay.securityjwt.modules.account.dto.response.AccountDepositRespDto;
import com.pjay.securityjwt.modules.account.dto.response.AccountListRespDto;
import com.pjay.securityjwt.modules.account.dto.response.AccountSaveRespDto;
import com.pjay.securityjwt.modules.transaction.domain.Transaction;
import com.pjay.securityjwt.modules.transaction.domain.TransactionRepository;
import com.pjay.securityjwt.modules.user.domain.User;
import com.pjay.securityjwt.modules.user.domain.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Transactional
    public AccountSaveRespDto addAccount(Long userId, AccountSaveReqDto accountSaveReqDto){
        // User가 DB에 있는지 검증
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다.")
        );

        // 해당 계좌가 DB에 있는지 중복여부를 체크
        Optional<Account> accountOP = accountRepository.findByNumber(accountSaveReqDto.getNumber());
        if(accountOP.isPresent()){
            throw new CustomApiException("해당 계좌가 이미 존재합니다.");
        }
        // 계좌 등록
        Account accountPS = accountRepository.save(accountSaveReqDto.toEntity(userPS));

        // DTO를 응답
        return new AccountSaveRespDto(accountPS);
    }

    public AccountListRespDto accountListByUser(Long userId){
        User userPS = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("유저를 찾을 수 없습니다"));

        // 유저의 모든 계좌목록
        List<Account> accountListPS = accountRepository.findByUser_Id(userId);

        return new AccountListRespDto(userPS, accountListPS);
    }

    @Transactional
    public void deleteAccount(Long accountNumber, Long userId){
        // 1. 계좌 확인
        Account accountPS = accountRepository.findByNumber(accountNumber).orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다."));
        // 2. 계좌 소유자 확인
        accountPS.checkOwner(userId);
        // 3. 계좌 삭제
        accountRepository.deleteById(accountPS.getId());
    }

    // ATM -> 누군가의 계좌
    // 인증이 필요없음
    @Transactional
    public AccountDepositRespDto deposit(AccountDepositReqDto accountDepositReqDto){
        // 0원 체크
        if(accountDepositReqDto.getAmount() <= 0L){
            throw new CustomApiException("0원 이하의 금액을 입력할 수 없습니다");
        }
        // 입금계좌가 있는지 확인
        Account depositAccountPS = accountRepository.findByNumber(accountDepositReqDto.getNumber()).orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다"));

        // 입금 (해당 계좌 balance 조정 - update문 - 더티체킹)
        depositAccountPS.deposit(accountDepositReqDto.getAmount());

        // 거래내역 남기기
        Transaction transaction = Transaction.builder()
                .depositAccount(depositAccountPS)
                .withdrawAccount(null)
                .depositAccountBalance(depositAccountPS.getBalance())
                .withdrawAccountBalance(null)
                .amount(accountDepositReqDto.getAmount())
                .gubun(TransactionType.DEPOSIT)
                .sender("ATM")
                .receiver(accountDepositReqDto.getNumber()+"")
                .tel(accountDepositReqDto.getTel())
                .build();

        Transaction transactionPS = transactionRepository.save(transaction);

        return new AccountDepositRespDto(depositAccountPS, transactionPS);
    }
}
