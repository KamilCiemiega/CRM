package com.crm.controller.dto;

public class MessageFolderDto {

        private Integer id;
        private String name;
        private Integer parentFolderId;
        private Integer ownerUserId;
        private UserDto user;

        public MessageFolderDto() {}

        public MessageFolderDto(Integer id, String name, Integer parentFolderId, Integer ownerUserId, UserDto user) {
            this.id = id;
            this.name = name;
            this.parentFolderId = parentFolderId;
            this.ownerUserId = ownerUserId;
            this.user = user;
        }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getParentFolderId() {
            return parentFolderId;
        }

        public void setParentFolderId(Integer parentFolderId) {
            this.parentFolderId = parentFolderId;
        }

        public Integer getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(Integer ownerUserId) {
            this.ownerUserId = ownerUserId;
        }

        public UserDto getUser() {
            return user;
        }

        public void setUser(UserDto user) {
            this.user = user;
        }
}
