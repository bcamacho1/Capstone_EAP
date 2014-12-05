package edu.ndnu.capstone.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordChange
{
    @NotNull
    @Size(min=5, message="Old Password can not be left blank.")
    @Pattern(regexp = "^[0-9a-zA-Z]{5,}$", message="Old Password must start with a number and be at least 5 characters.")
    private String oldPassword;
    
    @NotNull
    @Size(min=5, message="New Password can not be left blank.")
    @Pattern(regexp = "^[0-9a-zA-Z]{5,}$", message="New Password must start with a number and be at least 5 characters.")
    private String newPassword;
    
    @NotNull
    @Size(min=5, message="New Password can not be left blank.")
    @Pattern(regexp = "^[0-9a-zA-Z]{5,}$", message="New Password must start with a number and be at least 5 characters.")
    private String newPasswordConfirm;

    public String getOldPassword()
    {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }
    
    public String getNewPasswordConfirm()
    {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm)
    {
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
