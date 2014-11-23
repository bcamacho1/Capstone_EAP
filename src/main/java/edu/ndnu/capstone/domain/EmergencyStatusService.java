package edu.ndnu.capstone.domain;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.EmergencyStatus.class })
public interface EmergencyStatusService {

    public abstract long countAllEmergencyStatuses();


    public abstract void deleteEmergencyStatus(EmergencyStatus emergencyStatus);


    public abstract EmergencyStatus findEmergencyStatus(Integer id);


    public abstract List<EmergencyStatus> findAllEmergencyStatuses();


    public abstract List<EmergencyStatus> findEmergencyStatusEntries(int firstResult, int maxResults);


    public abstract void saveEmergencyStatus(EmergencyStatus emergencyStatus);


    public abstract EmergencyStatus updateEmergencyStatus(EmergencyStatus emergencyStatus);

}
