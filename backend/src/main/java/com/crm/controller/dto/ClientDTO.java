package com.crm.controller.dto;

import com.crm.controller.dto.ticket.TicketDTO;
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
    private CompanyDTO company;
    private List<TicketDTO> tickets = new ArrayList<>();
}
