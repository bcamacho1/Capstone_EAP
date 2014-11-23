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
@RooIntegrationTest(entity = Evacuation.class)
public class EvacuationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    EvacuationDataOnDemand dod;

    @Autowired
    EvacuationService evacuationService;

    @Test
    public void testCountAllEvacuations() {
        Assert.assertNotNull("Data on demand for 'Evacuation' failed to initialize correctly", dod.getRandomEvacuation());
        long count = evacuationService.countAllEvacuations();
        Assert.assertTrue("Counter for 'Evacuation' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindEvacuation() {
        Evacuation obj = dod.getRandomEvacuation();
        Assert.assertNotNull("Data on demand for 'Evacuation' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Evacuation' failed to provide an identifier", id);
        obj = evacuationService.findEvacuation(id);
        Assert.assertNotNull("Find method for 'Evacuation' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Evacuation' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllEvacuations() {
        Assert.assertNotNull("Data on demand for 'Evacuation' failed to initialize correctly", dod.getRandomEvacuation());
        long count = evacuationService.countAllEvacuations();
        Assert.assertTrue("Too expensive to perform a find all test for 'Evacuation', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Evacuation> result = evacuationService.findAllEvacuations();
        Assert.assertNotNull("Find all method for 'Evacuation' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Evacuation' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindEvacuationEntries() {
        Assert.assertNotNull("Data on demand for 'Evacuation' failed to initialize correctly", dod.getRandomEvacuation());
        long count = evacuationService.countAllEvacuations();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Evacuation> result = evacuationService.findEvacuationEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Evacuation' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Evacuation' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testSaveEvacuation() {
        Assert.assertNotNull("Data on demand for 'Evacuation' failed to initialize correctly", dod.getRandomEvacuation());
        Evacuation obj = dod.getNewTransientEvacuation(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Evacuation' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Evacuation' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Evacuation' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testDeleteEvacuation() {
        Evacuation obj = dod.getRandomEvacuation();
        Assert.assertNotNull("Data on demand for 'Evacuation' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Evacuation' failed to provide an identifier", id);
        obj = evacuationService.findEvacuation(id);
        evacuationService.deleteEvacuation(obj);
        obj.flush();
        Assert.assertNull("Failed to remove 'Evacuation' with identifier '" + id + "'", evacuationService.findEvacuation(id));
    }
}
