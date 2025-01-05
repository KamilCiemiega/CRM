package com.crm.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO extends UserDTO{
    private List<MessageFolderDTO> messageFolders = new ArrayList<>();
    private List<ReportingDetailsDTO> reportings = new ArrayList<>();
    private TaskDetailsDTO taskCreator;
    private TaskDetailsDTO taskWorker;
    private List<UserNotificationDTO> userNotification = new ArrayList<>();
    private List<MessageParticipantDTO> messageParticipants = new ArrayList<>();
}
