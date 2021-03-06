package edu.ndnu.capstone.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmergencyServiceImpl implements EmergencyService {

    public long countAllEmergencies() {
        return Emergency.countEmergencies();
    }

    public void deleteEmergency(Emergency emergency) {
        emergency.remove();
    }

    public Emergency findEmergency(Integer id) {
        return Emergency.findEmergency(id);
    }

    public List<Emergency> findAllEmergencies() {
        return Emergency.findAllEmergencies();
    }

    public List<Emergency> findEmergencyEntries(int firstResult, int maxResults) {
        return Emergency.findEmergencyEntries(firstResult, maxResults);
    }

    public void saveEmergency(Emergency emergency) {
        emergency.persist();
    }

    public Emergency updateEmergency(Emergency emergency) {
        return emergency.merge();
    }
}
