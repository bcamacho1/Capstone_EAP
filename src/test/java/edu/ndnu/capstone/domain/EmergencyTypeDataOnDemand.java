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
@RooDataOnDemand(entity = EmergencyType.class)
public class EmergencyTypeDataOnDemand {

    private Random rnd = new SecureRandom();

    private List<EmergencyType> data;

    @Autowired
    EmergencyTypeService emergencyTypeService;

    public EmergencyType getNewTransientEmergencyType(int index) {
        EmergencyType obj = new EmergencyType();
        setName(obj, index);
        setCategory(obj, index);
        return obj;
    }

    public void setName(EmergencyType obj, int index) {
        String name = "name_" + index;
        if (name.length() > 256) {
            name = name.substring(0, 256);
        }
        obj.setName(name);
    }
    
    public void setCategory(EmergencyType obj, int index) {
        Integer category = new Integer(index);
        obj.setCategory(category);
    }

    public EmergencyType getSpecificEmergencyType(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        EmergencyType obj = data.get(index);
        Integer id = obj.getId();
        return emergencyTypeService.findEmergencyType(id);
    }

    public EmergencyType getRandomEmergencyType() {
        init();
        EmergencyType obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return emergencyTypeService.findEmergencyType(id);
    }

    public boolean modifyEmergencyType(EmergencyType obj) {
        return false;
    }

    public void init() {
        int from = 0;
        int to = 10;
        data = emergencyTypeService.findEmergencyTypeEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'EmergencyType' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<EmergencyType>();
        for (int i = 0; i < 10; i++) {
            EmergencyType obj = getNewTransientEmergencyType(i);
            try {
                emergencyTypeService.saveEmergencyType(obj);
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
