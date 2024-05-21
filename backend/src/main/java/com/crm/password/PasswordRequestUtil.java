package com.crm.password;

public class PasswordRequestUtil {
    private String emailName;
    private String oldPassword;
    private String newPassword;

    public PasswordRequestUtil(String emailName, String oldPassword, String newPassword) {
        this.emailName = emailName;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return emailName;
    }

    public void setEmail(String email) {
        this.emailName = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
