package com.crm.service.serviceImpl.unit.messageServiceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.serviceImpl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class GetMessageTest {
    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    MessageServiceImpl underTest;

    Message message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        message = new Message();
        message.setId(1);
    }

    @Test
    void shouldThrowExceptionWhenMessageByIdNotFound() {
        // given
        when(messageRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> underTest.getMessageById(1))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Can't find message with id");
    }
}
