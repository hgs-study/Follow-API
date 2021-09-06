package com.followproject.domain.block.service;

import com.followproject.business.account.entity.Account;
import com.followproject.business.block.entity.Block;
import com.followproject.business.block.repository.BlockRepository;
import com.followproject.business.block.service.BlockService;
import com.followproject.business.follow.entity.Follow;
import com.followproject.common.error.code.ErrorCode;
import com.followproject.common.error.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlockServiceTest {

    @InjectMocks
    BlockService blockService;

    @Mock
    BlockRepository blockRepository;

    @DisplayName("블락")
    @Test
    void block() {
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Block block = new Block(firstAccount, secondAccount);

        given(blockRepository.save(block)).willReturn(block);

        blockService.block(block);

        verify(blockRepository).save(block);
    }
}
