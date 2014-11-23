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
@RooDataOnDemand(entity = EmergencyAlertLog.class)
public class EmergencyAlertLogDataOnDemand {

    private Random rnd = new SecureRandom();

    private List<EmergencyAlertLog> data;

    @Autowired
    EmergencyDataOnDemand emergencyDataOnDemand;

    @Autowired
    EmergencyMessageDataOnDemand emergencyMessageDataOnDemand;

    @Autowired
    UserDataOnDemand userDataOnDemand;

    @Autowired
    EmergencyAlertLogService emergencyAlertLogService;

    public EmergencyAlertLog getNewTransientEmergencyAlertLog(int index) {
        EmergencyAlertLog obj = new EmergencyAlertLog();
        setEmergencyId(obj, index);
        setSent(obj, index);
        setTs(obj, index);
        return obj;
    }

    public void setEmergencyId(EmergencyAlertLog obj, int index) {
        Emergency emergencyId = emergencyDataOnDemand.getRandomEmergency();
        obj.setEmergencyId(emergencyId);
    }

    public void setSent(EmergencyAlertLog obj, int index) {
        Integer sent = new Integer(index);
        obj.setSent(sent);
    }

    public void setTs(EmergencyAlertLog obj, int index) {
        Calendar ts = Calendar.getInstance();
        obj.setTs(ts);
    }

    public EmergencyAlertLog getSpecificEmergencyAlertLog(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        EmergencyAlertLog obj = data.get(index);
        Integer id = obj.getId();
        return emergencyAlertLogService.findEmergencyAlertLog(id);
    }

    public EmergencyAlertLog getRandomEmergencyAlertLog() {
        init();
        EmergencyAlertLog obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return emergencyAlertLogService.findEmergencyAlertLog(id);
    }

    public boolean modifyEmergencyAlertLog(EmergencyAlertLog obj) {
        return false;
    }

    public void init() {
        int from = 0;
        int to = 10;
        data = emergencyAlertLogService.findEmergencyAlertLogEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'EmergencyAlertLog' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<EmergencyAlertLog>();
        for (int i = 0; i < 10; i++) {
            EmergencyAlertLog obj = getNewTransientEmergencyAlertLog(i);
            try {
                emergencyAlertLogService.saveEmergencyAlertLog(obj);
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
