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
@RooDataOnDemand(entity = UserType.class)
public class UserTypeDataOnDemand {

    private Random rnd = new SecureRandom();

    private List<UserType> data;

    @Autowired
    UserTypeService userTypeService;

    public UserType getNewTransientUserType(int index) {
        UserType obj = new UserType();
        setName(obj, index);
        return obj;
    }

    public void setName(UserType obj, int index) {
        String name = "name_" + index;
        if (name.length() > 256) {
            name = name.substring(0, 256);
        }
        obj.setName(name);
    }

    public UserType getSpecificUserType(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        UserType obj = data.get(index);
        Integer id = obj.getId();
        return userTypeService.findUserType(id);
    }

    public UserType getRandomUserType() {
        init();
        UserType obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return userTypeService.findUserType(id);
    }

    public boolean modifyUserType(UserType obj) {
        return false;
    }

    public void init() {
        int from = 0;
        int to = 10;
        data = userTypeService.findUserTypeEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'UserType' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<UserType>();
        for (int i = 0; i < 10; i++) {
            UserType obj = getNewTransientUserType(i);
            try {
                userTypeService.saveUserType(obj);
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
