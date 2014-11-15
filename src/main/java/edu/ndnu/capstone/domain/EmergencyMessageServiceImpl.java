package edu.ndnu.capstone.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmergencyMessageServiceImpl implements EmergencyMessageService {

	public long countAllEmergencyMessages() {
        return EmergencyMessage.countEmergencyMessages();
    }
	
	public long countAllEmergencyMessagesByUser(Integer user_id) {
        return EmergencyMessage.countEmergencyMessagesByUser(user_id);
    }

	public void deleteEmergencyMessage(EmergencyMessage emergencyMessage) {
        emergencyMessage.remove();
    }

	public EmergencyMessage findEmergencyMessage(Integer id) {
        return EmergencyMessage.findEmergencyMessage(id);
    }
	
	public EmergencyMessage findEmergencyMessageByUserAndType(Integer user_id, Integer type_id) {
	    return EmergencyMessage.findEmergencyMessageByUserAndType(user_id, type_id);
	}
	    
	public List<EmergencyMessage> findEmergencyMessageByUser(Integer user_id) {
	    return EmergencyMessage.findEmergencyMessageByUser(user_id);
	}
	    
	public EmergencyMessage findDefaultEmergencyMessageByType(Integer type_id) {
	    return EmergencyMessage.findDefaultEmergencyMessageByType(type_id);
	}

	public List<EmergencyMessage> findAllEmergencyMessages() {
        return EmergencyMessage.findAllEmergencyMessages();
    }

	public List<EmergencyMessage> findEmergencyMessageEntries(int firstResult, int maxResults) {
        return EmergencyMessage.findEmergencyMessageEntries(firstResult, maxResults);
    }
	
	public List<EmergencyMessage> findEmergencyMessageEntriesByUser(Integer user_id, int firstResult, int maxResults) {
        return EmergencyMessage.findEmergencyMessageEntriesByUser(user_id, firstResult, maxResults);
    }

	public void saveEmergencyMessage(EmergencyMessage emergencyMessage) {
        emergencyMessage.persist();
    }

	public EmergencyMessage updateEmergencyMessage(EmergencyMessage emergencyMessage) {
        return emergencyMessage.merge();
    }
}
