// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.Location;

privileged aspect Location_Roo_JavaBean {

	private static int evacuation_area;
	private static int id;
	private static String description;

	public int Location.getId() {
        return id;
    }
    
    public void Location.setId(int id) {
        this.id = id;
    }
    
    public int Location.getEvacuation_area() {
        return this.evacuation_area;
    }
    
    public void Location.setEvacuation_area(int evacuation_area) {
        this.evacuation_area = evacuation_area;
    }
    
    public String Location.getDescription() {
        return this.getDescription();
    }
    
    public void Location.setDescription(String description) {
        this.setDescription(description);
    }
    
}
