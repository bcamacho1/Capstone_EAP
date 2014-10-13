package test;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker.Default;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import edu.ndnu.capstone.domain.Location;
import edu.ndnu.capstone.domain.LocationDataOnDemand;

public class ALocationTest {
	
	@Test
	public void testLocationMissingAddress() {
	  
	  LocationDataOnDemand dod = new LocationDataOnDemand();
	 
	  Location.findLocation(1);

	  AnnotationDrivenStaticEntityMockingControl.expectReturn(
	     dod.getNewTransientLocation(0));
	 
	  AnnotationDrivenStaticEntityMockingControl.playback();
	 
	  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
	  validator.afterPropertiesSet();
	 
	  Location location = Location.findLocation(1);
	  location.setAddress(null);
	   
	  Set<ConstraintViolation<Location>> violations =
	    validator.validate(location, Default.class);
	 
	  Assert.assertEquals(1, violations.size());
	 
	  ConstraintViolation<Location> violation = violations.iterator().next();
	   
	  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
	    violation.getMessageTemplate());
	    
	  Assert.assertEquals("address",
			violation.getPropertyPath().toString());
	}
	
	@Test
	public void testLocationMissingState() {

		  LocationDataOnDemand dod = new LocationDataOnDemand();
		 
		  Location.findLocation(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientLocation(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Location location = Location.findLocation(1);
		  location.setState(null);
		   
		  Set<ConstraintViolation<Location>> violations =
		    validator.validate(location, Default.class);
		 
		  Assert.assertEquals(1, violations.size());
		 
		  ConstraintViolation<Location> violation = violations.iterator().next();
		   
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  Assert.assertEquals("state",
				violation.getPropertyPath().toString());
	
	}
	
	@Test
	public void testLocationMissingCity() {

		  LocationDataOnDemand dod = new LocationDataOnDemand();
		 
		  Location.findLocation(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientLocation(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Location location = Location.findLocation(1);
		  location.setCity(null);
		   
		  Set<ConstraintViolation<Location>> violations =
		    validator.validate(location, Default.class);
		 
		  Assert.assertEquals(1, violations.size());
		 
		  ConstraintViolation<Location> violation = violations.iterator().next();
		   
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  Assert.assertEquals("city",
				violation.getPropertyPath().toString());
	
	}
	
	@Test
	public void testLocationMissingZipCode() {

		  LocationDataOnDemand dod = new LocationDataOnDemand();
		 
		  Location.findLocation(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientLocation(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Location location = Location.findLocation(1);
		  location.setZipcode(null);
		   
		  Set<ConstraintViolation<Location>> violations =
		    validator.validate(location, Default.class);
		 
		  Assert.assertEquals(1, violations.size());
		 
		  ConstraintViolation<Location> violation = violations.iterator().next();
		   
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  Assert.assertEquals("zipcode",
				violation.getPropertyPath().toString());
	
	}
	
	@Test
	public void testLocationMissingEvacuationArea() {

		  LocationDataOnDemand dod = new LocationDataOnDemand();
		 
		  Location.findLocation(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientLocation(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Location location = Location.findLocation(1);
		  location.setEvacuationArea(null);
		   
		  Set<ConstraintViolation<Location>> violations =
		    validator.validate(location, Default.class);
		 
		  Assert.assertEquals(1, violations.size());
		 
		  ConstraintViolation<Location> violation = violations.iterator().next();
		   
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  Assert.assertEquals("evacuation_area",
				violation.getPropertyPath().toString());
	
	}
}
