package com.followproject.domain.follow.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.service.FollowService;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "hgsTest@naver.com", roles = "USER")
public class FollowControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    FollowService followService;

    @MockBean
    AccountService accountService;

    @DisplayName("팔로우")
    @Test
    public void follow() throws Exception {
        final String authenticationEmail = "hgsTest@naver.com";
        final Account firstAccount = new Account(authenticationEmail,"password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Follow follow = new Follow(firstAccount,secondAccount);

        doNothing().when(followService).follow(follow);

        mockMvc.perform(post("/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"hgstudy_@naver.com2\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(followService).follow(any());
    }

    @DisplayName("언팔로우")
    @Test
    public void unfollow() throws Exception {
        final String authenticationEmail = "hgsTest@naver.com";
        final Account firstAccount = new Account(authenticationEmail,"password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");

        doNothing().when(followService).unfollow(firstAccount, secondAccount);

        mockMvc.perform(delete("/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"hgstudy_@naver.com2\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(followService).unfollow(any(), any());
    }

}
