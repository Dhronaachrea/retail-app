package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

public class ChangePasswordRequestBean {

    private String confirmNewPassword;
    private String newPassword;
    private String oldPassword;

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NonNull
    @Override
    public String toString() {
        return "ChangePasswordRequestBean{" + "confirmNewPassword='" + confirmNewPassword + '\'' + ", newPassword='" + newPassword + '\'' + ", oldPassword='" + oldPassword + '\'' + '}';
    }
}
