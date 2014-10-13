package test;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker.Default;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import edu.ndnu.capstone.domain.EmergencyDataOnDemand;
import edu.ndnu.capstone.domain.Emergency;

public class AnEmergencyTest {
	
	@Test
	public void testEmergencyMissingUserID() {

		  EmergencyDataOnDemand dod = new EmergencyDataOnDemand();
		 
		  Emergency.findEmergency(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientEmergency(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Emergency emergency = Emergency.findEmergency(1);
		  emergency.setUserId(null);
		   
		  Set<ConstraintViolation<Emergency>> violations =
		    validator.validate(emergency, Default.class);
		 
		  Assert.assertEquals(1, violations.size());
		 
		  ConstraintViolation<Emergency> violation = violations.iterator().next();
		   
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  Assert.assertEquals("user_id",
				violation.getPropertyPath().toString());
	
	}
	
	@Test
	public void testEmergencyMissingLocationID() {

		  EmergencyDataOnDemand dod = new EmergencyDataOnDemand();
		 
		  Emergency.findEmergency(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientEmergency(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Emergency emergency = Emergency.findEmergency(1);
		  emergency.setLocationId(null);
		   
		  
		  Set<ConstraintViolation<Emergency>> violations =
		    validator.validate(emergency, Default.class);
		 
		  
		  Assert.assertEquals(1, violations.size());
		 
		  
		  ConstraintViolation<Emergency> violation = violations.iterator().next();
		   
		  
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  
		  Assert.assertEquals("location_id",
				violation.getPropertyPath().toString());
	
	}
	
	@Test
	public void testEmergencyMissingTypeID() {

		  EmergencyDataOnDemand dod = new EmergencyDataOnDemand();
		 
		  Emergency.findEmergency(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientEmergency(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Emergency emergency = Emergency.findEmergency(1);
		  emergency.setTypeId(null);
		   
		  Set<ConstraintViolation<Emergency>> violations =
		    validator.validate(emergency, Default.class);
		 
		  Assert.assertEquals(1, violations.size());
		 
		  ConstraintViolation<Emergency> violation = violations.iterator().next();
		   
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  Assert.assertEquals("type_id",
				violation.getPropertyPath().toString());
	
	}
	
	@Test
	public void testEmergencyMissingStatusID() {

		  EmergencyDataOnDemand dod = new EmergencyDataOnDemand();
		 
		  Emergency.findEmergency(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientEmergency(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Emergency emergency = Emergency.findEmergency(1);
		  emergency.setStatusId(null);
		   
		  Set<ConstraintViolation<Emergency>> violations =
		    validator.validate(emergency, Default.class);
		 
		  Assert.assertEquals(1, violations.size());
		 
		  ConstraintViolation<Emergency> violation = violations.iterator().next();
		   
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  Assert.assertEquals("status_id",
				violation.getPropertyPath().toString());
	
	}
	
	@Test
	public void testEmergencyMissingDate() {

		  EmergencyDataOnDemand dod = new EmergencyDataOnDemand();
		 
		  Emergency.findEmergency(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientEmergency(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Emergency emergency = Emergency.findEmergency(1);
		  emergency.setCreated(null);
		   
		  Set<ConstraintViolation<Emergency>> violations =
		    validator.validate(emergency, Default.class);
		 
		  Assert.assertEquals(1, violations.size());
		 
		  ConstraintViolation<Emergency> violation = violations.iterator().next();
		   
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  Assert.assertEquals("created",
				violation.getPropertyPath().toString());
	
	}
}
