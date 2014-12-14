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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
@RooIntegrationTest(entity = EmergencyStatus.class)
public class EmergencyStatusIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    EmergencyStatusDataOnDemand dod;

    @Autowired
    EmergencyStatusService emergencyStatusService;

    @Test
    public void testCountAllEmergencyStatuses() {
        Assert.assertNotNull("Data on demand for 'EmergencyStatus' failed to initialize correctly", dod.getRandomEmergencyStatus());
        long count = emergencyStatusService.countAllEmergencyStatuses();
        Assert.assertTrue("Counter for 'EmergencyStatus' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindEmergencyStatus() {
        EmergencyStatus obj = dod.getRandomEmergencyStatus();
        Assert.assertNotNull("Data on demand for 'EmergencyStatus' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EmergencyStatus' failed to provide an identifier", id);
        obj = emergencyStatusService.findEmergencyStatus(id);
        Assert.assertNotNull("Find method for 'EmergencyStatus' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'EmergencyStatus' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllEmergencyStatuses() {
        Assert.assertNotNull("Data on demand for 'EmergencyStatus' failed to initialize correctly", dod.getRandomEmergencyStatus());
        long count = emergencyStatusService.countAllEmergencyStatuses();
        Assert.assertTrue("Too expensive to perform a find all test for 'EmergencyStatus', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<EmergencyStatus> result = emergencyStatusService.findAllEmergencyStatuses();
        Assert.assertNotNull("Find all method for 'EmergencyStatus' illegally returned null", result);
        Assert.assertTrue("Find all method for 'EmergencyStatus' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindEmergencyStatusEntries() {
        Assert.assertNotNull("Data on demand for 'EmergencyStatus' failed to initialize correctly", dod.getRandomEmergencyStatus());
        long count = emergencyStatusService.countAllEmergencyStatuses();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<EmergencyStatus> result = emergencyStatusService.findEmergencyStatusEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'EmergencyStatus' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'EmergencyStatus' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testSaveEmergencyStatus() {
        Assert.assertNotNull("Data on demand for 'EmergencyStatus' failed to initialize correctly", dod.getRandomEmergencyStatus());
        EmergencyStatus obj = dod.getNewTransientEmergencyStatus(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'EmergencyStatus' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'EmergencyStatus' identifier to be null", obj.getId());
        try {
            emergencyStatusService.saveEmergencyStatus(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
        }
    }

    @Test
    public void testDeleteEmergencyStatus() {
        EmergencyStatus obj = dod.getRandomEmergencyStatus();
        Assert.assertNotNull("Data on demand for 'EmergencyStatus' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EmergencyStatus' failed to provide an identifier", id);
        obj = emergencyStatusService.findEmergencyStatus(id);
        emergencyStatusService.deleteEmergencyStatus(obj);
        obj.flush();
        Assert.assertNull("Failed to remove 'EmergencyStatus' with identifier '" + id + "'", emergencyStatusService.findEmergencyStatus(id));
    }
}
