package edu.ndnu.capstone.domain;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.EmergencyAlertLog.class })
public interface EmergencyAlertLogService {

    public abstract long countAllEmergencyAlertLogs();


    public abstract void deleteEmergencyAlertLog(EmergencyAlertLog emergencyAlertLog);


    public abstract EmergencyAlertLog findEmergencyAlertLog(Integer id);


    public abstract List<EmergencyAlertLog> findAllEmergencyAlertLogs();


    public abstract List<EmergencyAlertLog> findEmergencyAlertLogEntries(int firstResult, int maxResults);


    public abstract void saveEmergencyAlertLog(EmergencyAlertLog emergencyAlertLog);


    public abstract EmergencyAlertLog updateEmergencyAlertLog(EmergencyAlertLog emergencyAlertLog);

}
