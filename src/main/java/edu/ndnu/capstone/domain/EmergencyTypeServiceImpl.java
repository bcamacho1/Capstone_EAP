package edu.ndnu.capstone.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmergencyTypeServiceImpl implements EmergencyTypeService {

    public long countAllEmergencyTypes() {
        return EmergencyType.countEmergencyTypes();
    }

    public void deleteEmergencyType(EmergencyType emergencyType) {
        emergencyType.remove();
    }

    public EmergencyType findEmergencyType(Integer id) {
        return EmergencyType.findEmergencyType(id);
    }

    public List<EmergencyType> findAllEmergencyTypes() {
        return EmergencyType.findAllEmergencyTypes();
    }

    public List<EmergencyType> findEmergencyTypeEntries(int firstResult, int maxResults) {
        return EmergencyType.findEmergencyTypeEntries(firstResult, maxResults);
    }

    public void saveEmergencyType(EmergencyType emergencyType) {
        emergencyType.persist();
    }

    public EmergencyType updateEmergencyType(EmergencyType emergencyType) {
        return emergencyType.merge();
    }
}
