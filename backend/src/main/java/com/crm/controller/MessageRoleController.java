package com.crm.controller;

import com.crm.controller.dto.MessageDTO;
import com.crm.controller.dto.MessageRoleDTO;
import com.crm.entity.MessageRole;
import com.crm.service.MessageRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class MessageRoleController {

    private final MessageRoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageRoleController(MessageRoleService roleService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<MessageRoleDTO> saveNewRole(@RequestBody MessageRoleDTO roleDTO) {
        MessageRole savedRole = roleService.save(modelMapper.map(roleDTO, MessageRole.class));

        return new ResponseEntity<>(modelMapper.map(savedRole, MessageRoleDTO.class), HttpStatus.CREATED);
    }

    @PostMapping("/{message-role-id}")
    public ResponseEntity<MessageRoleDTO> updateRole(@PathVariable("message-role-id") int messageRoleId, @RequestBody MessageRoleDTO roleDTO) {
        MessageRole updatedRole = roleService.update(messageRoleId, modelMapper.map(roleDTO, MessageRole.class));

        return new ResponseEntity<>(modelMapper.map(updatedRole, MessageRoleDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageRoleDTO> getRoleById(@PathVariable("id") int roleId) {
        return new ResponseEntity<>(modelMapper.map(roleService.findById(roleId), MessageRoleDTO.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MessageRoleDTO>> getAllRoles() {
        List<MessageRoleDTO> roles = roleService.findAllRoles()
                .stream()
                .map(role -> modelMapper.map(role, MessageRoleDTO.class))
                .toList();

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageRoleDTO> deleteRole(@PathVariable("id") int id) {
        MessageRoleDTO deletedRole =  modelMapper.map(roleService.deleteRole(id), MessageRoleDTO.class);

        return new ResponseEntity<>(deletedRole, HttpStatus.OK);
    }
}
