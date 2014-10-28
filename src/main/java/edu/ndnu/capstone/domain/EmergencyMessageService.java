package edu.ndnu.capstone.domain;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.EmergencyMessage.class })
public interface EmergencyMessageService {

	public abstract long countAllEmergencyMessages();


	public abstract void deleteEmergencyMessage(EmergencyMessage emergencyMessage);


	public abstract EmergencyMessage findEmergencyMessage(Integer id);


	public abstract List<EmergencyMessage> findAllEmergencyMessages();


	public abstract List<EmergencyMessage> findEmergencyMessageEntries(int firstResult, int maxResults);


	public abstract void saveEmergencyMessage(EmergencyMessage emergencyMessage);


	public abstract EmergencyMessage updateEmergencyMessage(EmergencyMessage emergencyMessage);

}
