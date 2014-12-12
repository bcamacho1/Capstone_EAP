package edu.ndnu.capstone.domain;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.Emergency.class })
public interface EmergencyService {

    public abstract long countAllEmergencies();


    public abstract void deleteEmergency(Emergency emergency);


    public abstract Emergency findEmergency(Integer id);


    public abstract List<Emergency> findAllEmergencies();


    public abstract List<Emergency> findEmergencyEntries(int firstResult, int maxResults);


    public abstract void saveEmergency(Emergency emergency);


    public abstract Emergency updateEmergency(Emergency emergency);

}
