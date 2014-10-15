package edu.ndnu.capstone.domain;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Location.class)
public class LocationDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Location> data;

	@Autowired
    LocationService locationService;

	public Location getNewTransientLocation(int index) {
        Location obj = new Location();
        setAddress(obj, index);
        setCity(obj, index);
        setDescription(obj, index);
        setEvacuationArea(obj, index);
        setLatitude(obj, index);
        setLongitude(obj, index);
        setName(obj, index);
        setState(obj, index);
        setZipcode(obj, index);
        return obj;
    }

	public void setAddress(Location obj, int index) {
        String address = "address_" + index;
        if (address.length() > 1024) {
            address = address.substring(0, 1024);
        }
        obj.setAddress(address);
    }

	public void setCity(Location obj, int index) {
        String city = "city_" + index;
        if (city.length() > 1024) {
            city = city.substring(0, 1024);
        }
        obj.setCity(city);
    }

	public void setDescription(Location obj, int index) {
        String description = "description_" + index;
        if (description.length() > 1024) {
            description = description.substring(0, 1024);
        }
        obj.setDescription(description);
    }

	public void setEvacuationArea(Location obj, int index) {
        Integer evacuationArea = new Integer(index);
        obj.setEvacuationArea(evacuationArea);
    }

	public void setLatitude(Location obj, int index) {
        String latitude = "latitude_" + index;
        if (latitude.length() > 64) {
            latitude = latitude.substring(0, 64);
        }
        obj.setLatitude(latitude);
    }

	public void setLongitude(Location obj, int index) {
        String longitude = "longitude_" + index;
        if (longitude.length() > 64) {
            longitude = longitude.substring(0, 64);
        }
        obj.setLongitude(longitude);
    }

	public void setName(Location obj, int index) {
        String name = "name_" + index;
        if (name.length() > 256) {
            name = name.substring(0, 256);
        }
        obj.setName(name);
    }

	public void setState(Location obj, int index) {
        String state = "state_" + index;
        if (state.length() > 64) {
            state = state.substring(0, 64);
        }
        obj.setState(state);
    }

	public void setZipcode(Location obj, int index) {
        String zipcode = "zipcode_" + index;
        if (zipcode.length() > 64) {
            zipcode = zipcode.substring(0, 64);
        }
        obj.setZipcode(zipcode);
    }

	public Location getSpecificLocation(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Location obj = data.get(index);
        Integer id = obj.getId();
        return locationService.findLocation(id);
    }

	public Location getRandomLocation() {
        init();
        Location obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return locationService.findLocation(id);
    }

	public boolean modifyLocation(Location obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = locationService.findLocationEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Location' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Location>();
        for (int i = 0; i < 10; i++) {
            Location obj = getNewTransientLocation(i);
            try {
                locationService.saveLocation(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
}
