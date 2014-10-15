package edu.ndnu.capstone.domain;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.EmergencyType.class })
public interface EmergencyTypeService {

	public abstract long countAllEmergencyTypes();


	public abstract void deleteEmergencyType(EmergencyType emergencyType);


	public abstract EmergencyType findEmergencyType(Integer id);


	public abstract List<EmergencyType> findAllEmergencyTypes();


	public abstract List<EmergencyType> findEmergencyTypeEntries(int firstResult, int maxResults);


	public abstract void saveEmergencyType(EmergencyType emergencyType);


	public abstract EmergencyType updateEmergencyType(EmergencyType emergencyType);

}
