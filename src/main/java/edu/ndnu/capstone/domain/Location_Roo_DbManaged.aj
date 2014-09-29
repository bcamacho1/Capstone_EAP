// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.Emergency;
import edu.ndnu.capstone.domain.Location;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

privileged aspect Location_Roo_DbManaged {
    
    @OneToMany(mappedBy = "locationId")
    private Set<Emergency> Location.emergencies;
    
    @Column(name = "name", length = 256)
    @NotNull
    private String Location.name;
    
    @Column(name = "address", length = 1024)
    @NotNull
    private String Location.address;
    
    @Column(name = "city", length = 1024)
    @NotNull
    private String Location.city;
    
    @Column(name = "state", length = 64)
    @NotNull
    private String Location.state;
    
    @Column(name = "zipcode", length = 64)
    @NotNull
    private String Location.zipcode;
    
    @Column(name = "evacuation_area")
    private Integer Location.evacuationArea;
    
    @Column(name = "latitude", length = 64)
    private String Location.latitude;
    
    @Column(name = "longitude", length = 64)
    private String Location.longitude;
    
    @Column(name = "description", length = 1024)
    private String Location.description;
    
    public Set<Emergency> Location.getEmergencies() {
        return emergencies;
    }
    
    public void Location.setEmergencies(Set<Emergency> emergencies) {
        this.emergencies = emergencies;
    }
    
    public String Location.getName() {
        return name;
    }
    
    public void Location.setName(String name) {
        this.name = name;
    }
    
    public String Location.getAddress() {
        return address;
    }
    
    public void Location.setAddress(String address) {
        this.address = address;
    }
    
    public String Location.getCity() {
        return city;
    }
    
    public void Location.setCity(String city) {
        this.city = city;
    }
    
    public String Location.getState() {
        return state;
    }
    
    public void Location.setState(String state) {
        this.state = state;
    }
    
    public String Location.getZipcode() {
        return zipcode;
    }
    
    public void Location.setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    public Integer Location.getEvacuationArea() {
        return evacuationArea;
    }
    
    public void Location.setEvacuationArea(Integer evacuationArea) {
        this.evacuationArea = evacuationArea;
    }
    
    public String Location.getLatitude() {
        return latitude;
    }
    
    public void Location.setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String Location.getLongitude() {
        return longitude;
    }
    
    public void Location.setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String Location.getDescription() {
        return description;
    }
    
    public void Location.setDescription(String description) {
        this.description = description;
    }
    
}