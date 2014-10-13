package edu.ndnu.capstone.model;
import java
//import org.springframework.roo.addon.dbre.RooDbManaged;
//import org.springframework.roo.addon.javabean.RooJavaBean;
//import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
//import org.springframework.roo.addon.tostring.RooToString;

//@RooJavaBean
//@RooToString
//@RooJpaActiveRecord(versionField = "", table = "location")
//@RooDbManaged(automaticallyDelete = true)
public class Location {
	private int id;
	private String name;
	private String evacuation_area;
	private String latitude;
	private String longitude;
	private String description;
	
	public Location(){
		name = "Location A";
		evacuation_area = "school enterence";
		
	}
	
	public Location(int id, String name, String evacuation_area, String latitude, String longitude){
		
	}
}
