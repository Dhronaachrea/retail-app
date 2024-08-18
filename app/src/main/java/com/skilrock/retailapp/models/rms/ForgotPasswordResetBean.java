package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordResetBean {

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("newPassword")
    @Expose
    private String newPassword;

    @SerializedName("confirmNewPassword")
    @Expose
    private String confirmNewPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public String toString() {
        return "ForgotPasswordResetBean{" + "userName='" + userName + '\'' + ", mobileNumber='" + mobileNumber + '\'' + ", otp='" + otp + '\'' + ", newPassword='" + newPassword + '\'' + ", confirmNewPassword='" + confirmNewPassword + '\'' + '}';
    }
}
