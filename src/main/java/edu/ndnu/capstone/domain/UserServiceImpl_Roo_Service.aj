// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect UserServiceImpl_Roo_Service {
    
    declare @type: UserServiceImpl: @Service;
    
    declare @type: UserServiceImpl: @Transactional;
    
    public long UserServiceImpl.countAllUsers() {
        return User.countUsers();
    }
    
    public void UserServiceImpl.deleteUser(User user) {
        user.remove();
    }
    
    public User UserServiceImpl.findUser(Integer id) {
        return User.findUser(id);
    }
    
    public List<User> UserServiceImpl.findAllUsers() {
        return User.findAllUsers();
    }
    
    public List<User> UserServiceImpl.findUserEntries(int firstResult, int maxResults) {
        return User.findUserEntries(firstResult, maxResults);
    }
    
    public void UserServiceImpl.saveUser(User user) {
        user.persist();
    }
    
    public User UserServiceImpl.updateUser(User user) {
        return user.merge();
    }
    
}
