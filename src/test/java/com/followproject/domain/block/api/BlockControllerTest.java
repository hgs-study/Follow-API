package com.followproject.domain.block.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.block.entity.Block;
import com.followproject.business.block.service.BlockService;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.service.FollowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "hgsTest@naver.com", roles = "USER")
public class BlockControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BlockService blockService;

    @MockBean
    FollowService followService;

    @MockBean
    AccountService accountService;

    @DisplayName("블락 등록")
    @Test
    public void block() throws Exception {
        final String authenticationEmail = "hgsTest@naver.com";
        final Account firstAccount = new Account("hgstudy_@naver.com1", "password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2", "password123!!");
        final Follow follow = new Follow(firstAccount, secondAccount);
        final Block block = new Block(firstAccount, secondAccount);

        doNothing().when(blockService).block(block);
        given(accountService.findByEmail(authenticationEmail)).willReturn(firstAccount);
        given(followService.findByFromAccountAndToAccount(firstAccount,secondAccount)).willReturn(follow);

        mockMvc.perform(post("/blocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"hgstudy_@naver.com2\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(blockService).block(any());
        verify(accountService).findByEmail(authenticationEmail);
        verify(followService).findByFromAccountAndToAccount(any(), any());
    }

}
