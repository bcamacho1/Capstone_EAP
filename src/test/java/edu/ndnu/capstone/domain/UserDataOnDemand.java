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

@Component
@Configurable
@RooDataOnDemand(entity = User.class)
public class UserDataOnDemand {

    private Random rnd = new SecureRandom();

    private List<User> data;

    @Autowired
    UserTypeDataOnDemand userTypeDataOnDemand;

    @Autowired
    UserService userService;

    public User getNewTransientUser(int index) {
        User obj = new User();
        setActive(obj, index);
        setCreated(obj, index);
        setDescription(obj, index);
        setEmail(obj, index);
        setName(obj, index);
        setPhone(obj, index);
        setTypeId(obj, index);
        return obj;
    }

    public void setActive(User obj, int index) {
        Integer active = new Integer(index);
        obj.setActive(active);
    }

    public void setCreated(User obj, int index) {
        Calendar created = Calendar.getInstance();
        obj.setCreated(created);
    }

    public void setDescription(User obj, int index) {
        String description = "description_" + index;
        if (description.length() > 1024) {
            description = description.substring(0, 1024);
        }
        obj.setDescription(description);
    }

    public void setEmail(User obj, int index) {
        String email = "foo" + index + "@bar.com";
        if (email.length() > 256) {
            email = new Random().nextInt(10) + email.substring(1, 256);
        }
        obj.setEmail(email);
    }

    public void setName(User obj, int index) {
        String name = "name_" + index;
        if (name.length() > 256) {
            name = name.substring(0, 256);
        }
        obj.setName(name);
    }

    public void setPhone(User obj, int index) {
        String phone = "phone_" + index;
        if (phone.length() > 24) {
            phone = phone.substring(0, 24);
        }
        obj.setPhone(phone);
    }

    public void setTypeId(User obj, int index) {
        UserType typeId = userTypeDataOnDemand.getRandomUserType();
        obj.setTypeId(typeId);
    }

    public User getSpecificUser(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        User obj = data.get(index);
        Integer id = obj.getId();
        return userService.findUser(id);
    }

    public User getRandomUser() {
        init();
        User obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return userService.findUser(id);
    }

    public boolean modifyUser(User obj) {
        return false;
    }

    public void init() {
        int from = 0;
        int to = 10;
        data = userService.findUserEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'User' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User obj = getNewTransientUser(i);
            try {
                userService.saveUser(obj);
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
