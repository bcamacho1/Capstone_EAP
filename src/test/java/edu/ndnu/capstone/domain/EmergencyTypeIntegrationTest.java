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
@RooIntegrationTest(entity = EmergencyType.class)
public class EmergencyTypeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    EmergencyTypeDataOnDemand dod;

    @Autowired
    EmergencyTypeService emergencyTypeService;

    @Test
    public void testCountAllEmergencyTypes() {
        Assert.assertNotNull("Data on demand for 'EmergencyType' failed to initialize correctly", dod.getRandomEmergencyType());
        long count = emergencyTypeService.countAllEmergencyTypes();
        Assert.assertTrue("Counter for 'EmergencyType' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindEmergencyType() {
        EmergencyType obj = dod.getRandomEmergencyType();
        Assert.assertNotNull("Data on demand for 'EmergencyType' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EmergencyType' failed to provide an identifier", id);
        obj = emergencyTypeService.findEmergencyType(id);
        Assert.assertNotNull("Find method for 'EmergencyType' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'EmergencyType' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllEmergencyTypes() {
        Assert.assertNotNull("Data on demand for 'EmergencyType' failed to initialize correctly", dod.getRandomEmergencyType());
        long count = emergencyTypeService.countAllEmergencyTypes();
        Assert.assertTrue("Too expensive to perform a find all test for 'EmergencyType', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<EmergencyType> result = emergencyTypeService.findAllEmergencyTypes();
        Assert.assertNotNull("Find all method for 'EmergencyType' illegally returned null", result);
        Assert.assertTrue("Find all method for 'EmergencyType' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindEmergencyTypeEntries() {
        Assert.assertNotNull("Data on demand for 'EmergencyType' failed to initialize correctly", dod.getRandomEmergencyType());
        long count = emergencyTypeService.countAllEmergencyTypes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<EmergencyType> result = emergencyTypeService.findEmergencyTypeEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'EmergencyType' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'EmergencyType' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testSaveEmergencyType() {
        Assert.assertNotNull("Data on demand for 'EmergencyType' failed to initialize correctly", dod.getRandomEmergencyType());
        EmergencyType obj = dod.getNewTransientEmergencyType(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'EmergencyType' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'EmergencyType' identifier to be null", obj.getId());
        try {
            emergencyTypeService.saveEmergencyType(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
        }
    }

    @Test
    public void testDeleteEmergencyType() {
        EmergencyType obj = dod.getRandomEmergencyType();
        Assert.assertNotNull("Data on demand for 'EmergencyType' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'EmergencyType' failed to provide an identifier", id);
        obj = emergencyTypeService.findEmergencyType(id);
        obj.flush();
    }
}
