// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.Emergency;
import edu.ndnu.capstone.domain.EmergencyStatus;
import edu.ndnu.capstone.domain.EmergencyType;
import edu.ndnu.capstone.domain.Location;
import edu.ndnu.capstone.domain.User;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

privileged aspect Emergency_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User Emergency.userId;
    
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location Emergency.locationId;
    
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private EmergencyType Emergency.typeId;
    
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private EmergencyStatus Emergency.statusId;
    
    @Column(name = "created", updatable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar Emergency.created = java.util.Calendar.getInstance();
    
    @Column(name = "description", length = 1024)
    private String Emergency.description;
    
    public User Emergency.getUserId() {
        return userId;
    }
    
    public void Emergency.setUserId(User userId) {
        this.userId = userId;
    }
    
    public Location Emergency.getLocationId() {
        return locationId;
    }
    
    public void Emergency.setLocationId(Location locationId) {
        this.locationId = locationId;
    }
    
    public EmergencyType Emergency.getTypeId() {
        return typeId;
    }
    
    public void Emergency.setTypeId(EmergencyType typeId) {
        this.typeId = typeId;
    }
    
    public EmergencyStatus Emergency.getStatusId() {
        return statusId;
    }
    
    public void Emergency.setStatusId(EmergencyStatus statusId) {
        this.statusId = statusId;
    }
    
    public Calendar Emergency.getCreated() {
        return created;
    }
    
    public void Emergency.setCreated(Calendar created) {
        this.created = created;
    }
    
    public String Emergency.getDescription() {
        return description;
    }
    
    public void Emergency.setDescription(String description) {
        this.description = description;
    }
    
}