package com.crm.controller.dto;

import com.crm.entity.Company;
import com.crm.entity.User;
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
public class ClientDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
<<<<<<< HEAD
    private CompanyDTO company;
    private List<MessageParticipantDTO> messageParticipants = new ArrayList<>();
    private List<ReportingDetailsDTO> reportings = new ArrayList<>();
=======
    private Company company;
    private List<TicketDTO> ticket = new ArrayList<>();
>>>>>>> 9421af8 (mostly working on TicketService save method)
}
