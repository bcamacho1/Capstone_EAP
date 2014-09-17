// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.Location;
import edu.ndnu.capstone.domain.LocationDataOnDemand;
import edu.ndnu.capstone.domain.LocationService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect LocationDataOnDemand_Roo_DataOnDemand {
    
    declare @type: LocationDataOnDemand: @Component;
    
    private Random LocationDataOnDemand.rnd = new SecureRandom();
    
    private List<Location> LocationDataOnDemand.data;
    
    @Autowired
    LocationService LocationDataOnDemand.locationService;
    
    public Location LocationDataOnDemand.getNewTransientLocation(int index) {
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
    
    public void LocationDataOnDemand.setAddress(Location obj, int index) {
        String address = "address_" + index;
        if (address.length() > 1024) {
            address = address.substring(0, 1024);
        }
        obj.setAddress(address);
    }
    
    public void LocationDataOnDemand.setCity(Location obj, int index) {
        String city = "city_" + index;
        if (city.length() > 1024) {
            city = city.substring(0, 1024);
        }
        obj.setCity(city);
    }
    
    public void LocationDataOnDemand.setDescription(Location obj, int index) {
        String description = "description_" + index;
        if (description.length() > 1024) {
            description = description.substring(0, 1024);
        }
        obj.setDescription(description);
    }
    
    public void LocationDataOnDemand.setEvacuationArea(Location obj, int index) {
        Integer evacuationArea = new Integer(index);
        obj.setEvacuationArea(evacuationArea);
    }
    
    public void LocationDataOnDemand.setLatitude(Location obj, int index) {
        String latitude = "latitude_" + index;
        if (latitude.length() > 64) {
            latitude = latitude.substring(0, 64);
        }
        obj.setLatitude(latitude);
    }
    
    public void LocationDataOnDemand.setLongitude(Location obj, int index) {
        String longitude = "longitude_" + index;
        if (longitude.length() > 64) {
            longitude = longitude.substring(0, 64);
        }
        obj.setLongitude(longitude);
    }
    
    public void LocationDataOnDemand.setName(Location obj, int index) {
        String name = "name_" + index;
        if (name.length() > 256) {
            name = name.substring(0, 256);
        }
        obj.setName(name);
    }
    
    public void LocationDataOnDemand.setState(Location obj, int index) {
        String state = "state_" + index;
        if (state.length() > 64) {
            state = state.substring(0, 64);
        }
        obj.setState(state);
    }
    
    public void LocationDataOnDemand.setZipcode(Location obj, int index) {
        String zipcode = "zipcode_" + index;
        if (zipcode.length() > 64) {
            zipcode = zipcode.substring(0, 64);
        }
        obj.setZipcode(zipcode);
    }
    
    public Location LocationDataOnDemand.getSpecificLocation(int index) {
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
    
    public Location LocationDataOnDemand.getRandomLocation() {
        init();
        Location obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return locationService.findLocation(id);
    }
    
    public boolean LocationDataOnDemand.modifyLocation(Location obj) {
        return false;
    }
    
    public void LocationDataOnDemand.init() {
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
