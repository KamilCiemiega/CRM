package com.crm.controller.dto;
import com.crm.entity.Role;

public record UserDto(String firstName, String lastName,String email, Role role) {

}
