package com.crm.controller.dto;

import com.crm.entity.Role;
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
public class RoleDTO {
    private Integer id;
    private Role.RoleType roleType;
    private List<User> users = new ArrayList<>();
}
