package com.followproject.domain.block.repository;

import com.followproject.business.account.entity.Account;
import com.followproject.business.block.entity.Block;
import com.followproject.business.block.repository.BlockRepository;
import com.followproject.business.follow.entity.Follow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BlockRepositoryTest {

    @Mock
    BlockRepository blockRepository;

    @DisplayName("블락 등록")
    @Test
    void save(){
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Block block = new Block(firstAccount, secondAccount);

        given(blockRepository.save(block)).willReturn(block);

        final Block saveBlock = blockRepository.save(block);

        assertEquals(block.getFromAccount(), saveBlock.getFromAccount());
        verify(blockRepository).save(block);
    }

    @DisplayName("블락 조회")
    @Test
    void findByFromAccountAndToAccount(){
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Block block = new Block(firstAccount, secondAccount);

        given(blockRepository.findByFromAccountAndToAccount(firstAccount,secondAccount)).willReturn(block);

        final Block saveBlock = blockRepository.findByFromAccountAndToAccount(firstAccount,secondAccount);

        assertEquals(block.getFromAccount(), saveBlock.getFromAccount());
        verify(blockRepository).findByFromAccountAndToAccount(firstAccount, secondAccount);
    }
}
