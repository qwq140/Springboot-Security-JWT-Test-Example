package com.pjay.securityjwt.modules.account.controller;

import com.pjay.securityjwt.common.ResponseDto;
import com.pjay.securityjwt.config.auth.LoginUser;
import com.pjay.securityjwt.modules.account.dto.request.AccountDepositReqDto;
import com.pjay.securityjwt.modules.account.dto.request.AccountSaveReqDto;
import com.pjay.securityjwt.modules.account.dto.request.AccountTransferReqDto;
import com.pjay.securityjwt.modules.account.dto.request.AccountWithdrawReqDto;
import com.pjay.securityjwt.modules.account.dto.response.*;
import com.pjay.securityjwt.modules.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountSaveReqDto accountSaveReqDto, BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser){

        AccountSaveRespDto accountSaveRespDto = accountService.addAccount(loginUser.getUser().getId(), accountSaveReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "계좌등록 성공.", accountSaveRespDto), HttpStatus.CREATED);
    }

    // 인증이 필요하고, account 테이블에 login한 유저의 계좌만 주세요.
    @GetMapping("/s/account/login-user")
    public ResponseEntity<?> findUserAccount(@AuthenticationPrincipal LoginUser loginUser){
        AccountListRespDto accountListRespDto = accountService.accountListByUser(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계조목록조회 성공", accountListRespDto), HttpStatus.OK);
    }

    @DeleteMapping("/s/account/{number}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long number, @AuthenticationPrincipal LoginUser loginUser){
        accountService.deleteAccount(number, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 삭제 완료", null), HttpStatus.OK);
    }

    @PostMapping("/account/deposit")
    public ResponseEntity<?> depositAccount(@RequestBody @Valid AccountDepositReqDto accountDepositReqDto, BindingResult bindingResult){
        AccountDepositRespDto accountDepositRespDto = accountService.deposit(accountDepositReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 입금 완료", accountDepositRespDto), HttpStatus.CREATED);
    }

    @PostMapping("/account/withdraw")
    public ResponseEntity<?> withdrawAccount(@RequestBody @Valid AccountWithdrawReqDto accountWithdrawReqDto, BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser){
        AccountWithdrawRespDto accountWithdrawRespDto = accountService.withdraw(accountWithdrawReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 춢급 완료", accountWithdrawRespDto), HttpStatus.CREATED);
    }

    @PostMapping("/account/transfer")
    public ResponseEntity<?> transferAccount(@RequestBody @Valid AccountTransferReqDto accountTransferReqDto, BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser){
        AccountTransferRespDto accountTransferRespDto = accountService.transfer(accountTransferReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 이체 완료", accountTransferRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/s/account/{number}")
    public ResponseEntity<?> findDetailAccount(@PathVariable Long number, @RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal LoginUser loginUser){
        AccountDetailRespDto accountDetailRespDto = accountService.detailAccount(number, loginUser.getUser().getId(), page);
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 상세보기 완료", accountDetailRespDto), HttpStatus.OK);
    }
}
