package edu.ndnu.capstone.domain;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@RooIntegrationTest(entity = User.class)
public class UserIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    UserDataOnDemand dod;

	@Autowired
    UserService userService;

	@Test
    public void testCountAllUsers() {
        Assert.assertNotNull("Data on demand for 'User' failed to initialize correctly", dod.getRandomUser());
        long count = userService.countAllUsers();
        Assert.assertTrue("Counter for 'User' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindUser() {
        User obj = dod.getRandomUser();
        Assert.assertNotNull("Data on demand for 'User' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'User' failed to provide an identifier", id);
        obj = userService.findUser(id);
        Assert.assertNotNull("Find method for 'User' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'User' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllUsers() {
        Assert.assertNotNull("Data on demand for 'User' failed to initialize correctly", dod.getRandomUser());
        long count = userService.countAllUsers();
        Assert.assertTrue("Too expensive to perform a find all test for 'User', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<User> result = userService.findAllUsers();
        Assert.assertNotNull("Find all method for 'User' illegally returned null", result);
        Assert.assertTrue("Find all method for 'User' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindUserEntries() {
        Assert.assertNotNull("Data on demand for 'User' failed to initialize correctly", dod.getRandomUser());
        long count = userService.countAllUsers();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<User> result = userService.findUserEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'User' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'User' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSaveUser() {
        Assert.assertNotNull("Data on demand for 'User' failed to initialize correctly", dod.getRandomUser());
        User obj = dod.getNewTransientUser(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'User' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'User' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'User' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteUser() {
        User obj = dod.getRandomUser();
        Assert.assertNotNull("Data on demand for 'User' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'User' failed to provide an identifier", id);
        obj = userService.findUser(id);
        userService.deleteUser(obj);
        obj.flush();
        Assert.assertNull("Failed to remove 'User' with identifier '" + id + "'", userService.findUser(id));
    }
}
