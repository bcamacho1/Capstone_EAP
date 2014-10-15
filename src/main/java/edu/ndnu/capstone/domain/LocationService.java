package edu.ndnu.capstone.domain;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.Location.class })
public interface LocationService {

	public abstract long countAllLocations();


	public abstract void deleteLocation(Location location);


	public abstract Location findLocation(Integer id);


	public abstract List<Location> findAllLocations();


	public abstract List<Location> findLocationEntries(int firstResult, int maxResults);


	public abstract void saveLocation(Location location);


	public abstract Location updateLocation(Location location);

}
