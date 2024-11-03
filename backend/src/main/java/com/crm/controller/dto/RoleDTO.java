package com.crm.controller.dto;

import com.crm.entity.Role;
import com.crm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Integer id;
    private Role.RoleType roleType;
    private Set<User> users = new HashSet<>();
}
