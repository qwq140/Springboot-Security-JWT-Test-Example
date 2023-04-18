package com.pjay.securityjwt.modules.account.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjay.securityjwt.config.dummy.DummyObject;
import com.pjay.securityjwt.modules.account.domain.Account;
import com.pjay.securityjwt.modules.account.domain.AccountRepository;
import com.pjay.securityjwt.modules.account.dto.request.AccountSaveReqDto;
import com.pjay.securityjwt.modules.account.dto.response.AccountListRespDto;
import com.pjay.securityjwt.modules.account.dto.response.AccountSaveRespDto;
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
}
