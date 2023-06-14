package com.pjay.securityjwt.modules.transaction.service;

import com.pjay.securityjwt.handler.ex.CustomApiException;
import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.account.domain.AccountRepository;
import com.pjay.securityjwt.modules.transaction.domain.Transaction;
import com.pjay.securityjwt.modules.transaction.domain.TransactionRepository;
import com.pjay.securityjwt.modules.transaction.dto.response.TransactionListRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionListRespDto transactionList(Long userId, Long accountNumber, String gubun, int page){
        // 계좌 존재 확인
        Account accountPS = accountRepository.findByNumber(accountNumber).orElseThrow(() -> new CustomApiException("해당 계좌를 찾을 수 없습니다"));

        // 본인 소유의 계좌인지 확인
        accountPS.checkOwner(userId);

        // 계좌내역조회
        List<Transaction> transactionListPS = transactionRepository.findTransactionList(accountPS.getId(), gubun, page);

        return new TransactionListRespDto(transactionListPS, accountPS);
    }

}
