package com.crm.service.serviceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.MessageRepository;
import com.crm.dao.ReportingRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.ReportingService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ReportingServiceImpl implements ReportingService {
    private final ReportingRepository reportingRepository;
    private final MessageRepository messageRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ReportingServiceImpl.class);


    @Autowired
    public ReportingServiceImpl(ReportingRepository reportingRepository, ReportingRepository reportingRepository1, MessageRepository messageRepository, ClientRepository clientRepository, UserRepository userRepository){
        this.reportingRepository = reportingRepository1;
        this.messageRepository = messageRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Reporting> getAllReports() {
        return reportingRepository.findAll();
    }

    @Transactional
    @Override
    public Reporting save(Reporting reporting) {
        if(reportingRepository.existsByTopic(reporting.getTopic())){
            throw new IllegalArgumentException("Topic must be unique. Value already exists: " + reporting.getTopic());
        }

        logger.debug("Test{}", reporting);

        if(!reporting.getMessages().isEmpty()) {
            List<Message> messageList = reporting.getMessages().stream()
                    .map(message -> {
                        Message foundMessage = messageRepository.findById(message.getId())
                                .orElseThrow(() -> new NoSuchEntityException("Message not found for ID: " + message.getId()));
                        foundMessage.getReportings().add(reporting);
                        return foundMessage;
                    })
                    .toList();
            reporting.setMessages(messageList);
        }

        Client existingClient = findEntityInRepository(clientRepository, reporting.getClient().getId(), "Client");
        existingClient.getReportings().add(reporting);

        reporting.setClient(existingClient);

        User existingUser = findEntityInRepository(userRepository, reporting.getUser().getId(), "User");
        existingUser.getReportings().add(reporting);

        reporting.setUser(existingUser);

        return reportingRepository.save(reporting);
    }

    private <T> T findEntityInRepository(JpaRepository<T, Integer> repository, int entityId, String entityName){
        return repository.findById(entityId)
                .orElseThrow(() -> new NoSuchEntityException(entityName + " not found for ID: " + entityId));
    }
}
