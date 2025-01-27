package com.crm.service.unit;


import com.crm.dao.AttachmentRepository;
import com.crm.dao.TaskRepository;
import com.crm.dao.TicketRepository;
import com.crm.dao.UserNotificationRepository;
import com.crm.entity.Attachment;
import com.crm.entity.Task;
import com.crm.entity.Ticket;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.EntityFinder;
import com.crm.utils.EntityProcessorHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

public class EntityProcessorHelperTests {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    AttachmentRepository attachmentRepository;
    @Mock
    UserNotificationRepository userNotificationRepository;

    @InjectMocks
    private EntityProcessorHelper underTest;

    Ticket ticket1;
    Ticket ticket2;
    String entityName;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        ticket1 = new Ticket();
        ticket1.setId(1);
        ticket2 = new Ticket();
        ticket2.setId(2);
        entityName = "Ticket";
    }

    @Nested
    class FinEntityTests {

        @Test
        void shouldReturnCorrectEntity(){
            //given
            //when
            when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket1));
            Ticket returnedTicket = underTest.findEntity(ticketRepository, ticket1.getId(), entityName);

            //then
            verify(ticketRepository, times(1)).findById(1);
            assertThat(returnedTicket).isNotNull();
            assertThat(returnedTicket).isEqualTo(ticket1);
        }

        @Test
        void shouldTrowExceptionWhenEntityNotFound(){
            //given
            when(ticketRepository.findById(1)).thenReturn(Optional.empty());

            //when & then
            verify(ticketRepository, times(1)).findById(1);
            assertThatThrownBy(() -> underTest.findEntity(ticketRepository, 1, entityName))
                    .isInstanceOf(NoSuchEntityException.class)
                    .hasMessageContaining("Ticket not found for ID: 1");
        }
    }

    @Nested
    class FindEntitiesTests {
        @Nested
        class PositiveTest {
            @Test
            void findEntities(){
                //given

                List<Ticket> ticketList = List.of(ticket1, ticket2);

                //when
                when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket1));
                when(ticketRepository.findById(2)).thenReturn(Optional.of(ticket2));

                List<Ticket> foundedTickets = underTest.findEntities(ticketList, ticketRepository, entityName);

                //then
                verify(ticketRepository, times(2)).findById(anyInt());
                assertThat(foundedTickets).hasSize(2);
                assertThat(foundedTickets.get(0)).isEqualTo(ticket1);
                assertThat(foundedTickets.get(1)).isEqualTo(ticket2);
            }
        }
        @Nested
        class NegativeTests {

            @Test
            void shouldReturnEmptyListWhenInputListIsEmpty() {
                // given
                List<Ticket> emptyTicketList = new ArrayList<>();

                // when
                List<Ticket> result = underTest.findEntities(emptyTicketList, ticketRepository, "Ticket");

                // then
                assertThat(result).isEmpty();
            }

            @Test
            void shouldThrowExceptionWhenOneEntityNotFound() {
                // given
                List<Ticket> ticketList = List.of(ticket1, ticket2);

                //when
                when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket1));
                when(ticketRepository.findById(2)).thenReturn(Optional.empty());

                List<Ticket> returnedList = underTest.findEntities(ticketList, ticketRepository, entityName);

                //then
                verify(ticketRepository, times(2)).findById(anyInt());
                assertThat(returnedList).hasSize(1);
                assertThat(returnedList.get(0)).isEqualTo(ticket1);
            }

            @Test
            void shouldThrowExceptionForUnsupportedEntityType() {
                // given
                Task unsupportedEntity = new Task();
                List<Task> entityList = List.of(unsupportedEntity);

                // when & then
                assertThatThrownBy(() -> underTest.findEntities(entityList, taskRepository, "Task"))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("Unknown entity type");
            }
        }
    }

    @Nested
    class ProcessAttachmentsTests {
        Attachment attachmentWithId;
        Attachment attachmentWithoutId;
        List<Attachment> attachmentList = new ArrayList<>();

        @BeforeEach
        void setUp(){
            attachmentWithId = new Attachment();
            attachmentWithId.setId(1);
            attachmentWithoutId = new Attachment();

            attachmentList.addAll(List.of(attachmentWithId, attachmentWithoutId));
        }

        @Nested
        class PositiveTests {

            @Test
            void processAttachments(){
                //given
                //when
                when(attachmentRepository.findById(1)).thenReturn(Optional.of(attachmentWithId));
                List<Attachment> attachments = underTest.processAttachments(
                        attachmentList,
                        ticket1,
                        attachmentRepository,
                        Attachment::setTicket
                );

                //then
                verify(attachmentRepository, times(1)).findById(1);
                assertThat(attachments).hasSize(2);
                assertThat(attachmentWithId.getTicket()).isEqualTo(ticket1);
                assertThat(attachmentWithoutId.getTicket()).isEqualTo(ticket1);
            }
        }
        @Nested
        class NegativeTests {

            @Test
            void shouldReturnEmptyListWhenInputListIsEmpty() {
                // given
                List<Attachment> emptyAttachmentList = new ArrayList<>();

                // when
               List<Attachment> attachments = underTest.processAttachments(
                       emptyAttachmentList,
                       ticket1,
                       attachmentRepository,
                       Attachment::setTicket
               );

                // then
                assertThat(attachments).isEmpty();
            }

            @Test
            void shouldOmitNotExistingEntityAndAddRestOfEntities(){
                //given
                Attachment nonExistingAttachment = new Attachment();
                nonExistingAttachment.setId(2);

                attachmentList.add(nonExistingAttachment);

                //when
                when(attachmentRepository.findById(1)).thenReturn(Optional.of(attachmentWithId));
                when(attachmentRepository.findById(2)).thenReturn(Optional.empty());

                List<Attachment> attachments = underTest.processAttachments(
                        attachmentList,
                        ticket1,
                        attachmentRepository,
                        Attachment::setTicket
                );

                //then
                verify(attachmentRepository, times(2)).findById(anyInt());
                assertThat(attachments).hasSize(2);
            }

        }
    }

    @Nested
    class processUserNotificationTests {

        @Nested
        class PositiveTest {

        }

        @Nested
        class NegativeTest {

        }
    }
}
