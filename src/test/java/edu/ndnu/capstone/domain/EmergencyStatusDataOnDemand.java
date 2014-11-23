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

@Configurable
@Component
@RooDataOnDemand(entity = EmergencyStatus.class)
public class EmergencyStatusDataOnDemand {

    private Random rnd = new SecureRandom();

    private List<EmergencyStatus> data;

    @Autowired
    EmergencyStatusService emergencyStatusService;

    public EmergencyStatus getNewTransientEmergencyStatus(int index) {
        EmergencyStatus obj = new EmergencyStatus();
        setName(obj, index);
        return obj;
    }

    public void setName(EmergencyStatus obj, int index) {
        String name = "name_" + index;
        if (name.length() > 256) {
            name = name.substring(0, 256);
        }
        obj.setName(name);
    }

    public EmergencyStatus getSpecificEmergencyStatus(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        EmergencyStatus obj = data.get(index);
        Integer id = obj.getId();
        return emergencyStatusService.findEmergencyStatus(id);
    }

    public EmergencyStatus getRandomEmergencyStatus() {
        init();
        EmergencyStatus obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return emergencyStatusService.findEmergencyStatus(id);
    }

    public boolean modifyEmergencyStatus(EmergencyStatus obj) {
        return false;
    }

    public void init() {
        int from = 0;
        int to = 10;
        data = emergencyStatusService.findEmergencyStatusEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'EmergencyStatus' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<EmergencyStatus>();
        for (int i = 0; i < 10; i++) {
            EmergencyStatus obj = getNewTransientEmergencyStatus(i);
            try {
                emergencyStatusService.saveEmergencyStatus(obj);
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
