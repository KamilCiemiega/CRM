package com.crm.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Timestamp createdAt;
<<<<<<< HEAD
    @JsonIgnore
=======
>>>>>>> 9421af8 (mostly working on TicketService save method)
    private List<ClientDTO> clients = new ArrayList<>();
}
