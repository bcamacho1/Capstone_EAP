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
@RooDataOnDemand(entity = Evacuation.class)
public class EvacuationDataOnDemand {

    private Random rnd = new SecureRandom();

    private List<Evacuation> data;

    @Autowired
    EvacuationService evacuationService;

    public Evacuation getNewTransientEvacuation(int index) {
        Evacuation obj = new Evacuation();
        setArea(obj, index);
        return obj;
    }

    public void setArea(Evacuation obj, int index) {
        String area = "area_" + index;
        if (area.length() > 45) {
            area = area.substring(0, 45);
        }
        obj.setArea(area);
    }

    public Evacuation getSpecificEvacuation(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Evacuation obj = data.get(index);
        Integer id = obj.getId();
        return evacuationService.findEvacuation(id);
    }

    public Evacuation getRandomEvacuation() {
        init();
        Evacuation obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return evacuationService.findEvacuation(id);
    }

    public boolean modifyEvacuation(Evacuation obj) {
        return false;
    }

    public void init() {
        int from = 0;
        int to = 10;
        data = evacuationService.findEvacuationEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Evacuation' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<Evacuation>();
        for (int i = 0; i < 10; i++) {
            Evacuation obj = getNewTransientEvacuation(i);
            try {
                evacuationService.saveEvacuation(obj);
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
