package edu.ndnu.capstone.domain;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Configurable
@Component
@RooDataOnDemand(entity = Emergency.class)
public class EmergencyDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Emergency> data;

	@Autowired
    LocationDataOnDemand locationDataOnDemand;

	@Autowired
    EmergencyStatusDataOnDemand emergencyStatusDataOnDemand;

	@Autowired
    EmergencyTypeDataOnDemand emergencyTypeDataOnDemand;

	@Autowired
    UserDataOnDemand userDataOnDemand;

	@Autowired
    EmergencyService emergencyService;

	public Emergency getNewTransientEmergency(int index) {
        Emergency obj = new Emergency();
        setCreated(obj, index);
        setDescription(obj, index);
        setLocationId(obj, index);
        setStatusId(obj, index);
        setTypeId(obj, index);
        setUserId(obj, index);
        return obj;
    }

	public void setCreated(Emergency obj, int index) {
        Calendar created = Calendar.getInstance();
        obj.setCreated(created);
    }

	public void setDescription(Emergency obj, int index) {
        String description = "description_" + index;
        if (description.length() > 1024) {
            description = description.substring(0, 1024);
        }
        obj.setDescription(description);
    }

	public void setLocationId(Emergency obj, int index) {
        Location locationId = locationDataOnDemand.getRandomLocation();
        obj.setLocationId(locationId);
    }

	public void setStatusId(Emergency obj, int index) {
        EmergencyStatus statusId = emergencyStatusDataOnDemand.getRandomEmergencyStatus();
        obj.setStatusId(statusId);
    }

	public void setTypeId(Emergency obj, int index) {
        EmergencyType typeId = emergencyTypeDataOnDemand.getRandomEmergencyType();
        obj.setTypeId(typeId);
    }

	public void setUserId(Emergency obj, int index) {
        User userId = userDataOnDemand.getRandomUser();
        obj.setUserId(userId);
    }

	public Emergency getSpecificEmergency(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Emergency obj = data.get(index);
        Integer id = obj.getId();
        return emergencyService.findEmergency(id);
    }

	public Emergency getRandomEmergency() {
        init();
        Emergency obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return emergencyService.findEmergency(id);
    }

	public boolean modifyEmergency(Emergency obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = emergencyService.findEmergencyEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Emergency' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Emergency>();
        for (int i = 0; i < 10; i++) {
            Emergency obj = getNewTransientEmergency(i);
            try {
                emergencyService.saveEmergency(obj);
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
