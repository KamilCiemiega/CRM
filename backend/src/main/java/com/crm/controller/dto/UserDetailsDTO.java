package com.crm.controller.dto;

import java.util.List;

public class UserDetailsDTO extends UserDTO{
    private List<MessageFolderDTO> messageFolders;
    private List<ReportingDetailsDTO> reportings;
    private TaskDetailsDTO taskCreator;
    private TaskDetailsDTO taskWorker;
    private List<UserNotificationDTO> userNotification;
}
