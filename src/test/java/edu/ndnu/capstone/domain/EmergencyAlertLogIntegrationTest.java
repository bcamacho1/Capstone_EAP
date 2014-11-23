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
@RooIntegrationTest(entity = EmergencyAlertLog.class)
public class EmergencyAlertLogIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    EmergencyAlertLogDataOnDemand dod;

    @Autowired
    EmergencyAlertLogService emergencyAlertLogService;

    @Test
    public void testCountAllEmergencyAlertLogs() {
        Assert.assertNotNull("Data on demand for 'EmergencyAlertLog' failed to initialize correctly", dod.getRandomEmergencyAlertLog());
        long count = emergencyAlertLogService.countAllEmergencyAlertLogs();
        Assert.assertTrue("Counter for 'EmergencyAlertLog' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindEmergencyAlertLog() {
        EmergencyAlertLog obj = dod.getRandomEmergencyAlertLog();
        Assert.assertNotNull("Data on demand for 'EmergencyAlertLog' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EmergencyAlertLog' failed to provide an identifier", id);
        obj = emergencyAlertLogService.findEmergencyAlertLog(id);
        Assert.assertNotNull("Find method for 'EmergencyAlertLog' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'EmergencyAlertLog' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllEmergencyAlertLogs() {
        Assert.assertNotNull("Data on demand for 'EmergencyAlertLog' failed to initialize correctly", dod.getRandomEmergencyAlertLog());
        long count = emergencyAlertLogService.countAllEmergencyAlertLogs();
        Assert.assertTrue("Too expensive to perform a find all test for 'EmergencyAlertLog', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<EmergencyAlertLog> result = emergencyAlertLogService.findAllEmergencyAlertLogs();
        Assert.assertNotNull("Find all method for 'EmergencyAlertLog' illegally returned null", result);
        Assert.assertTrue("Find all method for 'EmergencyAlertLog' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindEmergencyAlertLogEntries() {
        Assert.assertNotNull("Data on demand for 'EmergencyAlertLog' failed to initialize correctly", dod.getRandomEmergencyAlertLog());
        long count = emergencyAlertLogService.countAllEmergencyAlertLogs();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<EmergencyAlertLog> result = emergencyAlertLogService.findEmergencyAlertLogEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'EmergencyAlertLog' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'EmergencyAlertLog' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testSaveEmergencyAlertLog() {
        Assert.assertNotNull("Data on demand for 'EmergencyAlertLog' failed to initialize correctly", dod.getRandomEmergencyAlertLog());
        EmergencyAlertLog obj = dod.getNewTransientEmergencyAlertLog(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'EmergencyAlertLog' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'EmergencyAlertLog' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'EmergencyAlertLog' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testDeleteEmergencyAlertLog() {
        EmergencyAlertLog obj = dod.getRandomEmergencyAlertLog();
        Assert.assertNotNull("Data on demand for 'EmergencyAlertLog' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EmergencyAlertLog' failed to provide an identifier", id);
        obj = emergencyAlertLogService.findEmergencyAlertLog(id);
        emergencyAlertLogService.deleteEmergencyAlertLog(obj);
        obj.flush();
        Assert.assertNull("Failed to remove 'EmergencyAlertLog' with identifier '" + id + "'", emergencyAlertLogService.findEmergencyAlertLog(id));
    }
}
