package com.pjay.securityjwt.modules.transaction.controller;

import com.pjay.securityjwt.common.ResponseDto;
import com.pjay.securityjwt.config.auth.LoginUser;
import com.pjay.securityjwt.enum_package.TransactionType;
import com.pjay.securityjwt.modules.transaction.dto.response.TransactionListRespDto;
import com.pjay.securityjwt.modules.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/s/account/{number}/transaction")
    public ResponseEntity<?> findTransactionList(
            @PathVariable Long number,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "gubun", defaultValue = "ALL") String gubun,
            @AuthenticationPrincipal LoginUser loginUser
    ){
        TransactionListRespDto transactionListRespDto = transactionService.transactionList(loginUser.getUser().getId(), number, gubun, page);

//        return new ResponseEntity<>(new ResponseDto<>(1, "입출금목록 조회 성공", transactionListRespDto), HttpStatus.OK);
        return ResponseEntity.ok().body(new ResponseDto<>(1, "입출금목록 조회 성공", transactionListRespDto));
    }
}
