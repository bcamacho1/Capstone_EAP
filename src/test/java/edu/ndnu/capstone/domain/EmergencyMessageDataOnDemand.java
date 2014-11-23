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
@RooDataOnDemand(entity = EmergencyMessage.class)
public class EmergencyMessageDataOnDemand {

    private Random rnd = new SecureRandom();

    private List<EmergencyMessage> data;

    @Autowired
    EmergencyTypeDataOnDemand emergencyTypeDataOnDemand;

    @Autowired
    UserDataOnDemand userDataOnDemand;

    @Autowired
    EmergencyMessageService emergencyMessageService;

    public EmergencyMessage getNewTransientEmergencyMessage(int index) {
        EmergencyMessage obj = new EmergencyMessage();
        setMessage(obj, index);
        return obj;
    }

    public void setMessage(EmergencyMessage obj, int index) {
        String message = "message_" + index;
        obj.setMessage(message);
    }

    public EmergencyMessage getSpecificEmergencyMessage(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        EmergencyMessage obj = data.get(index);
        Integer id = obj.getId();
        return emergencyMessageService.findEmergencyMessage(id);
    }

    public EmergencyMessage getRandomEmergencyMessage() {
        init();
        EmergencyMessage obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return emergencyMessageService.findEmergencyMessage(id);
    }

    public boolean modifyEmergencyMessage(EmergencyMessage obj) {
        return false;
    }

    public void init() {
        int from = 0;
        int to = 10;
        data = emergencyMessageService.findEmergencyMessageEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'EmergencyMessage' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<EmergencyMessage>();
        for (int i = 0; i < 10; i++) {
            EmergencyMessage obj = getNewTransientEmergencyMessage(i);
            try {
                emergencyMessageService.saveEmergencyMessage(obj);
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
