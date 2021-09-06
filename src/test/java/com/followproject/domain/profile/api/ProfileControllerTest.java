package com.followproject.domain.profile.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.block.service.BlockService;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.repository.FollowRepository;
import com.followproject.business.follow.service.FollowService;
import com.followproject.business.profile.form.ProfileForm.*;
import com.followproject.common.error.code.ErrorCode;
import com.followproject.common.error.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "hgsTest@naver.com", roles = "USER")
public class ProfileControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    FollowService followService;

    @MockBean
    AccountService accountService;

    @MockBean
    BlockService blockService;

    @DisplayName("자신 프로필 조회")
    @Test
    public void findSelfProfile() throws Exception {
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Follow follow = new Follow(firstAccount,secondAccount);
        final List<Follow> follows = Arrays.asList(follow);
        final Long followCount = 1L;
        final String authenticationEmail = "hgsTest@naver.com";

        given(accountService.findByEmail(authenticationEmail)).willReturn(firstAccount);
        given(followService.findCountByFromAccount(firstAccount)).willReturn(followCount);
        given(followService.findAllByFromAccount(firstAccount)).willReturn(follows);

        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(accountService).findByEmail(authenticationEmail);
        verify(followService).findCountByFromAccount(firstAccount);
        verify(followService).findAllByFromAccount(firstAccount);
    }

    @DisplayName("유저 프로필 조회")
    @Test
    public void findProfileUser() throws Exception {
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Follow follow = new Follow(firstAccount,secondAccount);
        final List<Follow> follows = Arrays.asList(follow);
        final Long followCount = 1L;
        final String authenticationEmail = "hgsTest@naver.com";

        given(accountService.findByEmail(authenticationEmail)).willReturn(firstAccount);
        given(accountService.findByEmail(secondAccount.getEmail())).willReturn(secondAccount);
        given(followService.findCountByFromAccount(secondAccount)).willReturn(followCount);
        given(followService.findAllByFromAccount(secondAccount)).willReturn(follows);

        mockMvc.perform(get("/profiles/{email}",secondAccount.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(accountService).findByEmail(authenticationEmail);
        verify(followService).findCountByFromAccount(secondAccount);
        verify(followService).findAllByFromAccount(secondAccount);
    }


    @DisplayName("유저 프로필 조회 - 블락된 경우")
    @Test
    public void findProfileUserWhenBlock() throws Exception {
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final String authenticationEmail = "hgsTest@naver.com";

        doThrow(new BusinessException(ErrorCode.BLOCKED_PROFILE)).when(blockService).validateBlockedAccount(any(),any());
        given(accountService.findByEmail(authenticationEmail)).willReturn(firstAccount);

        mockMvc.perform(get("/profiles/{email}",secondAccount))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString(ErrorCode.BLOCKED_PROFILE.getCode())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(blockService).validateBlockedAccount(any(),any());
        verify(accountService).findByEmail(authenticationEmail);
    }



}
