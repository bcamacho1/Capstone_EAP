// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.EmergencyStatus;
import edu.ndnu.capstone.domain.EmergencyStatusServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect EmergencyStatusServiceImpl_Roo_Service {
    
    declare @type: EmergencyStatusServiceImpl: @Service;
    
    declare @type: EmergencyStatusServiceImpl: @Transactional;
    
    public long EmergencyStatusServiceImpl.countAllEmergencyStatuses() {
        return EmergencyStatus.countEmergencyStatuses();
    }
    
    public void EmergencyStatusServiceImpl.deleteEmergencyStatus(EmergencyStatus emergencyStatus) {
        emergencyStatus.remove();
    }
    
    public EmergencyStatus EmergencyStatusServiceImpl.findEmergencyStatus(Integer id) {
        return EmergencyStatus.findEmergencyStatus(id);
    }
    
    public List<EmergencyStatus> EmergencyStatusServiceImpl.findAllEmergencyStatuses() {
        return EmergencyStatus.findAllEmergencyStatuses();
    }
    
    public List<EmergencyStatus> EmergencyStatusServiceImpl.findEmergencyStatusEntries(int firstResult, int maxResults) {
        return EmergencyStatus.findEmergencyStatusEntries(firstResult, maxResults);
    }
    
    public void EmergencyStatusServiceImpl.saveEmergencyStatus(EmergencyStatus emergencyStatus) {
        emergencyStatus.persist();
    }
    
    public EmergencyStatus EmergencyStatusServiceImpl.updateEmergencyStatus(EmergencyStatus emergencyStatus) {
        return emergencyStatus.merge();
    }
    
}