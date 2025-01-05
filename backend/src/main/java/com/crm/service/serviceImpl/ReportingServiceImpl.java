package com.crm.service.serviceImpl;

import com.crm.dao.MessageRepository;
import com.crm.dao.ReportingRepository;
import com.crm.entity.Message;
import com.crm.entity.Reporting;
import com.crm.entity.Task;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportingServiceImpl implements ReportingService {
    private final ReportingRepository reportingRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ReportingServiceImpl(ReportingRepository reportingRepository, ReportingRepository reportingRepository1, MessageRepository messageRepository){
        this.reportingRepository = reportingRepository1;
        this.messageRepository = messageRepository;
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

        if(!reporting.getMessages().isEmpty()){
          List<Message> messageList = reporting.getMessages().stream()
                  .map(message -> {
                      Message foundMessage = messageRepository.findById(message.getId())
                              .orElseThrow(() -> new NoSuchEntityException("Message not found for ID: " + message.getId()));
                      foundMessage.getReportings().add(reporting);
                      return foundMessage;
                  })
                  .toList();
          reporting.setMessages(messageList);
//            List<Message> messageList = reporting.getMessages().stream()
//                    .peek(message -> message.getReportings().add(reporting))
//                    .toList();
//
//            reporting.setMessages(messageList);
        }

        return reportingRepository.save(reporting);
    }
}
