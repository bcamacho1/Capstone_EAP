package edu.ndnu.capstone.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

	public long countAllLocations() {
        return Location.countLocations();
    }

	public void deleteLocation(Location location) {
        location.remove();
    }

	public Location findLocation(Integer id) {
        return Location.findLocation(id);
    }

	public List<Location> findAllLocations() {
        return Location.findAllLocations();
    }

	public List<Location> findLocationEntries(int firstResult, int maxResults) {
        return Location.findLocationEntries(firstResult, maxResults);
    }

	public void saveLocation(Location location) {
        location.persist();
    }

	public Location updateLocation(Location location) {
        return location.merge();
    }
}
