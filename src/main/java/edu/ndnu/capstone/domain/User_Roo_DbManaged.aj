// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.User;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

privileged aspect User_Roo_DbManaged {
    
    @Column(name = "name", length = 256)
    @NotNull
    private String User.name;
    
    @Column(name = "email", length = 256)
    @NotNull
    private String User.email;
    
    @Column(name = "username", length = 256)
    private String User.username;
    
    public String User.getName() {
        return name;
    }
    
    public void User.setName(String name) {
        this.name = name;
    }
    
    public String User.getEmail() {
        return email;
    }
    
    public void User.setEmail(String email) {
        this.email = email;
    }
    
    public String User.getUsername() {
        return username;
    }
    
    public void User.setUsername(String username) {
        this.username = username;
    }
    
}
