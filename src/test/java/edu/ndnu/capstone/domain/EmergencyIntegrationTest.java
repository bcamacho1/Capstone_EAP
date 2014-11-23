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
@RooIntegrationTest(entity = Emergency.class)
public class EmergencyIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    EmergencyDataOnDemand dod;

    @Autowired
    EmergencyService emergencyService;

    @Test
    public void testCountAllEmergencys() {
        Assert.assertNotNull("Data on demand for 'Emergency' failed to initialize correctly", dod.getRandomEmergency());
        long count = emergencyService.countAllEmergencys();
        Assert.assertTrue("Counter for 'Emergency' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindEmergency() {
        Emergency obj = dod.getRandomEmergency();
        Assert.assertNotNull("Data on demand for 'Emergency' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Emergency' failed to provide an identifier", id);
        obj = emergencyService.findEmergency(id);
        Assert.assertNotNull("Find method for 'Emergency' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Emergency' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllEmergencys() {
        Assert.assertNotNull("Data on demand for 'Emergency' failed to initialize correctly", dod.getRandomEmergency());
        long count = emergencyService.countAllEmergencys();
        Assert.assertTrue("Too expensive to perform a find all test for 'Emergency', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Emergency> result = emergencyService.findAllEmergencys();
        Assert.assertNotNull("Find all method for 'Emergency' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Emergency' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindEmergencyEntries() {
        Assert.assertNotNull("Data on demand for 'Emergency' failed to initialize correctly", dod.getRandomEmergency());
        long count = emergencyService.countAllEmergencys();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Emergency> result = emergencyService.findEmergencyEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Emergency' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Emergency' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testSaveEmergency() {
        Assert.assertNotNull("Data on demand for 'Emergency' failed to initialize correctly", dod.getRandomEmergency());
        Emergency obj = dod.getNewTransientEmergency(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Emergency' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Emergency' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Emergency' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testDeleteEmergency() {
        Emergency obj = dod.getRandomEmergency();
        Assert.assertNotNull("Data on demand for 'Emergency' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Emergency' failed to provide an identifier", id);
        obj = emergencyService.findEmergency(id);
        emergencyService.deleteEmergency(obj);
        obj.flush();
        Assert.assertNull("Failed to remove 'Emergency' with identifier '" + id + "'", emergencyService.findEmergency(id));
    }
}
