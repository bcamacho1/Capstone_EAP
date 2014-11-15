package edu.ndnu.capstone.domain;
import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.EmergencyMessage.class })
public interface EmergencyMessageService {

	public abstract long countAllEmergencyMessages();
	
	
	public abstract long countAllEmergencyMessagesByUser(Integer user_id);


	public abstract void deleteEmergencyMessage(EmergencyMessage emergencyMessage);


	public abstract EmergencyMessage findEmergencyMessage(Integer id);

	
	public abstract EmergencyMessage findEmergencyMessageByUserAndType(Integer user_id, Integer type_id);
	
	
	public abstract List<EmergencyMessage> findEmergencyMessageByUser(Integer user_id);
	
	
	public abstract EmergencyMessage findDefaultEmergencyMessageByType(Integer type_id);
	

	public abstract List<EmergencyMessage> findAllEmergencyMessages();


	public abstract List<EmergencyMessage> findEmergencyMessageEntries(int firstResult, int maxResults);

	
	public abstract List<EmergencyMessage> findEmergencyMessageEntriesByUser(Integer user_id, int firstResult, int maxResults);
	

	public abstract void saveEmergencyMessage(EmergencyMessage emergencyMessage);


	public abstract EmergencyMessage updateEmergencyMessage(EmergencyMessage emergencyMessage);

}
