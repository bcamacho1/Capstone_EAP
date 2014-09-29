package edu.ndnu.capstone.domain;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "emergency_status")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "emergencies" })
public class EmergencyStatus {
}
