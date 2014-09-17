// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.UserType;
import edu.ndnu.capstone.domain.UserTypeServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect UserTypeServiceImpl_Roo_Service {
    
    declare @type: UserTypeServiceImpl: @Service;
    
    declare @type: UserTypeServiceImpl: @Transactional;
    
    public long UserTypeServiceImpl.countAllUserTypes() {
        return UserType.countUserTypes();
    }
    
    public void UserTypeServiceImpl.deleteUserType(UserType userType) {
        userType.remove();
    }
    
    public UserType UserTypeServiceImpl.findUserType(Integer id) {
        return UserType.findUserType(id);
    }
    
    public List<UserType> UserTypeServiceImpl.findAllUserTypes() {
        return UserType.findAllUserTypes();
    }
    
    public List<UserType> UserTypeServiceImpl.findUserTypeEntries(int firstResult, int maxResults) {
        return UserType.findUserTypeEntries(firstResult, maxResults);
    }
    
    public void UserTypeServiceImpl.saveUserType(UserType userType) {
        userType.persist();
    }
    
    public UserType UserTypeServiceImpl.updateUserType(UserType userType) {
        return userType.merge();
    }
    
}
