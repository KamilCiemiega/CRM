package com.crm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public FolderController() {

    }

//    @GetMapping("/get-user-folders")
//    public ResponseEntity<?> userFolders(){
//
//    }

}
