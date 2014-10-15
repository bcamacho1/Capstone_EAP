package edu.ndnu.capstone.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmergencyStatusServiceImpl implements EmergencyStatusService {

	public long countAllEmergencyStatuses() {
        return EmergencyStatus.countEmergencyStatuses();
    }

	public void deleteEmergencyStatus(EmergencyStatus emergencyStatus) {
        emergencyStatus.remove();
    }

	public EmergencyStatus findEmergencyStatus(Integer id) {
        return EmergencyStatus.findEmergencyStatus(id);
    }

	public List<EmergencyStatus> findAllEmergencyStatuses() {
        return EmergencyStatus.findAllEmergencyStatuses();
    }

	public List<EmergencyStatus> findEmergencyStatusEntries(int firstResult, int maxResults) {
        return EmergencyStatus.findEmergencyStatusEntries(firstResult, maxResults);
    }

	public void saveEmergencyStatus(EmergencyStatus emergencyStatus) {
        emergencyStatus.persist();
    }

	public EmergencyStatus updateEmergencyStatus(EmergencyStatus emergencyStatus) {
        return emergencyStatus.merge();
    }
}
