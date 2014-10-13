package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;



import edu.ndnu.capstone.domain.Location;
import edu.ndnu.capstone.domain.LocationServiceImpl;
import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserServiceImpl;

public class LocationServiceTest {

	@Test
	public void testDeleteLocation() {
	  
		LocationServiceImpl lsi = new LocationServiceImpl();
	 
		Location local = lsi.findLocation(1);
		
		lsi.deleteLocation(local);
		List<Location> locations = lsi.findAllLocations();
		
		Assert.assertTrue(!locations.contains(local));
	}
	
	@Test
	public void testFindLocation() {
	  
		LocationServiceImpl lsi = new LocationServiceImpl();
	 
		Location local = lsi.findLocation(1);
	  
		Assert.assertEquals(local, lsi.findLocation(1));
	}
	
	@Test
	public void testFindAllLocations() {
	  
		LocationServiceImpl lsi = new LocationServiceImpl();
	 
		List<Location> locals = lsi.findAllLocations();
	  
		Assert.assertEquals(locals, lsi.findAllLocations());
	}
	
	@Test
	public void testSaveLocation(Location local) {
		LocationServiceImpl lsi = new LocationServiceImpl();
	    lsi.saveLocation(local);
	    List <Location> locals = lsi.findAllLocations();
	    Assert.assertTrue(locals.contains(local));
	}
	
	@Test
	public void testUpdateLocation(Location local) {
		LocationServiceImpl lsi = new LocationServiceImpl();
	    lsi.updateLocation(local);
	    List <Location> locals = lsi.findAllLocations();
	    Assert.assertTrue(locals.contains(local));
	}
}
