package com.crm.controller.dto;

import com.crm.entity.User;

import java.util.List;

public class ClientDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private List<User> users;
}
