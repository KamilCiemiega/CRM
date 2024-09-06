package com.crm.controller.dto;

import com.crm.entity.Role;

public class NewUserDto extends UserDto{
    private String password;

    public NewUserDto() {
        super();
    }

    public NewUserDto(Integer id, String firstName, String lastName, String email, Role role, String password) {
        super(id,firstName, lastName, email, role);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
