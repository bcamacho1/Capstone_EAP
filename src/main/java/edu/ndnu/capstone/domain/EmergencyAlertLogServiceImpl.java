package edu.ndnu.capstone.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmergencyAlertLogServiceImpl implements EmergencyAlertLogService {

    public long countAllEmergencyAlertLogs() {
        return EmergencyAlertLog.countEmergencyAlertLogs();
    }

    public void deleteEmergencyAlertLog(EmergencyAlertLog emergencyAlertLog) {
        emergencyAlertLog.remove();
    }

    public EmergencyAlertLog findEmergencyAlertLog(Integer id) {
        return EmergencyAlertLog.findEmergencyAlertLog(id);
    }

    public List<EmergencyAlertLog> findAllEmergencyAlertLogs() {
        return EmergencyAlertLog.findAllEmergencyAlertLogs();
    }

    public List<EmergencyAlertLog> findEmergencyAlertLogEntries(int firstResult, int maxResults) {
        return EmergencyAlertLog.findEmergencyAlertLogEntries(firstResult, maxResults);
    }

    public void saveEmergencyAlertLog(EmergencyAlertLog emergencyAlertLog) {
        emergencyAlertLog.persist();
    }

    public EmergencyAlertLog updateEmergencyAlertLog(EmergencyAlertLog emergencyAlertLog) {
        return emergencyAlertLog.merge();
    }
}
