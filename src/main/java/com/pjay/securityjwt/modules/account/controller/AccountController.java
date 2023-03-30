package com.pjay.securityjwt.modules.account.controller;

import com.pjay.securityjwt.common.ResponseDto;
import com.pjay.securityjwt.config.auth.LoginUser;
import com.pjay.securityjwt.modules.account.dto.request.AccountSaveReqDto;
import com.pjay.securityjwt.modules.account.dto.response.AccountSaveRespDto;
import com.pjay.securityjwt.modules.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
}
