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
@RooIntegrationTest(entity = Location.class)
public class LocationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    LocationDataOnDemand dod;

    @Autowired
    LocationService locationService;

    @Test
    public void testCountAllLocations() {
        Assert.assertNotNull("Data on demand for 'Location' failed to initialize correctly", dod.getRandomLocation());
        long count = locationService.countAllLocations();
        Assert.assertTrue("Counter for 'Location' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindLocation() {
        Location obj = dod.getRandomLocation();
        Assert.assertNotNull("Data on demand for 'Location' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Location' failed to provide an identifier", id);
        obj = locationService.findLocation(id);
        Assert.assertNotNull("Find method for 'Location' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Location' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllLocations() {
        Assert.assertNotNull("Data on demand for 'Location' failed to initialize correctly", dod.getRandomLocation());
        long count = locationService.countAllLocations();
        Assert.assertTrue("Too expensive to perform a find all test for 'Location', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Location> result = locationService.findAllLocations();
        Assert.assertNotNull("Find all method for 'Location' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Location' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindLocationEntries() {
        Assert.assertNotNull("Data on demand for 'Location' failed to initialize correctly", dod.getRandomLocation());
        long count = locationService.countAllLocations();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Location> result = locationService.findLocationEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Location' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Location' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testSaveLocation() {
        Assert.assertNotNull("Data on demand for 'Location' failed to initialize correctly", dod.getRandomLocation());
        Location obj = dod.getNewTransientLocation(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Location' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Location' identifier to be null", obj.getId());
        try {
            locationService.saveLocation(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        obj.flush();
        Assert.assertNotNull("Expected 'Location' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testDeleteLocation() {
        Location obj = dod.getRandomLocation();
        Assert.assertNotNull("Data on demand for 'Location' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Location' failed to provide an identifier", id);
        obj = locationService.findLocation(id);
        locationService.deleteLocation(obj);
        obj.flush();
        Assert.assertNull("Failed to remove 'Location' with identifier '" + id + "'", locationService.findLocation(id));
    }
}
