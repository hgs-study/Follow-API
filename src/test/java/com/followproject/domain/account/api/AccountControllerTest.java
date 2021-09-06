package com.followproject.domain.account.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.account.util.AccountUtil;
import org.hibernate.type.CharacterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "hgsTest@naver.com", roles = "USER")
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @DisplayName("회원가입")
    @Test
    @WithAnonymousUser
    public void join() throws Exception {
        final Account account = new Account("hgstudy_@naver.com","password123!!");

        doNothing().when(accountService).join(account);

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"hgstudy_@naver.com\", \"password\" : \"password123!\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(account.getEmail())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(accountService).join(any());
    }

    @DisplayName("유저 상세 조회")
    @Test
    public void findAccount() throws Exception {
        final Account account = new Account("hgstudy_@naver.com","password123!!");

        given(accountService.findByEmail(account.getEmail())).willReturn(account);

        mockMvc.perform(get("/accounts/{email}",account.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(account.getEmail())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(accountService).findByEmail(account.getEmail());
    }

    @DisplayName("유저 목록 조회")
    @Test
    public void findAccounts() throws Exception {
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final List<Account> accounts = Arrays.asList(firstAccount, secondAccount);

        given(accountService.findAll()).willReturn(accounts);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(firstAccount.getEmail())))
                .andExpect(content().string(containsString(secondAccount.getEmail())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(accountService).findAll();
    }


}
