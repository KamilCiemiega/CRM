package com.crm.controller.dto;

import java.util.List;

public class UserDetailsDTO extends UserDTO{
    private List<MessageFolderDTO> messageFoldersDTOs;
    private List<ReportingDTO> reportingDTOs;
    private Integer createdTaskId;
    private Integer workingTaskId;
    private List<UserNotificationDTO> reportingNotificationDTOs;
    private List<UserNotificationDTO> taskNotificationDTOs;
}
