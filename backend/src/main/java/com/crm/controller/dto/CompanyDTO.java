package com.crm.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
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
    private List<ClientDTO> clientDTOs = new ArrayList<>();
}
