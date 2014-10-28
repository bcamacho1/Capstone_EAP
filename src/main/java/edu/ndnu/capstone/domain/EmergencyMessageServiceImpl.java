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

	public void deleteEmergencyMessage(EmergencyMessage emergencyMessage) {
        emergencyMessage.remove();
    }

	public EmergencyMessage findEmergencyMessage(Integer id) {
        return EmergencyMessage.findEmergencyMessage(id);
    }

	public List<EmergencyMessage> findAllEmergencyMessages() {
        return EmergencyMessage.findAllEmergencyMessages();
    }

	public List<EmergencyMessage> findEmergencyMessageEntries(int firstResult, int maxResults) {
        return EmergencyMessage.findEmergencyMessageEntries(firstResult, maxResults);
    }

	public void saveEmergencyMessage(EmergencyMessage emergencyMessage) {
        emergencyMessage.persist();
    }

	public EmergencyMessage updateEmergencyMessage(EmergencyMessage emergencyMessage) {
        return emergencyMessage.merge();
    }
}
