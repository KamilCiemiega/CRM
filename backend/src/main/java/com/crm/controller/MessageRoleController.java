package com.crm.controller;

import com.crm.controller.dto.MessageRoleDTO;
import com.crm.service.MessageRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class MessageRoleController {

    private final MessageRoleService roleService;

    @Autowired
    public MessageRoleController(MessageRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<MessageRoleDTO>> getAllRoles() {
        List<MessageRoleDTO> roles = roleService.findAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageRoleDTO> createOrUpdateRole(@RequestBody MessageRoleDTO roleDTO) {
        MessageRoleDTO savedRole = roleService.save(roleDTO);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") int id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
