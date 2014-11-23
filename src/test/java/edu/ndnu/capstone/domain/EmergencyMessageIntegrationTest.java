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
@RooIntegrationTest(entity = EmergencyMessage.class)
public class EmergencyMessageIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    EmergencyMessageDataOnDemand dod;

    @Autowired
    EmergencyMessageService emergencyMessageService;

    @Test
    public void testCountAllEmergencyMessages() {
        Assert.assertNotNull("Data on demand for 'EmergencyMessage' failed to initialize correctly", dod.getRandomEmergencyMessage());
        long count = emergencyMessageService.countAllEmergencyMessages();
        Assert.assertTrue("Counter for 'EmergencyMessage' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindEmergencyMessage() {
        EmergencyMessage obj = dod.getRandomEmergencyMessage();
        Assert.assertNotNull("Data on demand for 'EmergencyMessage' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EmergencyMessage' failed to provide an identifier", id);
        obj = emergencyMessageService.findEmergencyMessage(id);
        Assert.assertNotNull("Find method for 'EmergencyMessage' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'EmergencyMessage' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllEmergencyMessages() {
        Assert.assertNotNull("Data on demand for 'EmergencyMessage' failed to initialize correctly", dod.getRandomEmergencyMessage());
        long count = emergencyMessageService.countAllEmergencyMessages();
        Assert.assertTrue("Too expensive to perform a find all test for 'EmergencyMessage', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<EmergencyMessage> result = emergencyMessageService.findAllEmergencyMessages();
        Assert.assertNotNull("Find all method for 'EmergencyMessage' illegally returned null", result);
        Assert.assertTrue("Find all method for 'EmergencyMessage' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindEmergencyMessageEntries() {
        Assert.assertNotNull("Data on demand for 'EmergencyMessage' failed to initialize correctly", dod.getRandomEmergencyMessage());
        long count = emergencyMessageService.countAllEmergencyMessages();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<EmergencyMessage> result = emergencyMessageService.findEmergencyMessageEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'EmergencyMessage' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'EmergencyMessage' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testSaveEmergencyMessage() {
        Assert.assertNotNull("Data on demand for 'EmergencyMessage' failed to initialize correctly", dod.getRandomEmergencyMessage());
        EmergencyMessage obj = dod.getNewTransientEmergencyMessage(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'EmergencyMessage' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'EmergencyMessage' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'EmergencyMessage' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testDeleteEmergencyMessage() {
        EmergencyMessage obj = dod.getRandomEmergencyMessage();
        Assert.assertNotNull("Data on demand for 'EmergencyMessage' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EmergencyMessage' failed to provide an identifier", id);
        obj = emergencyMessageService.findEmergencyMessage(id);
        emergencyMessageService.deleteEmergencyMessage(obj);
        obj.flush();
        Assert.assertNull("Failed to remove 'EmergencyMessage' with identifier '" + id + "'", emergencyMessageService.findEmergencyMessage(id));
    }
}
