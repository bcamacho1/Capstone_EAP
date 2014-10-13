package test;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker.Default;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import edu.ndnu.capstone.domain.Emergency;
import edu.ndnu.capstone.domain.EmergencyServiceImpl;

public class EmergencyServiceTest {

	@Test
	public void testCountEmergencys() {
	  
		EmergencyServiceImpl esi = new EmergencyServiceImpl();
	 
		Emergency.findAllEmergencys();

		AnnotationDrivenStaticEntityMockingControl.expectReturn(
	    esi.findAllEmergencys());
	 
		AnnotationDrivenStaticEntityMockingControl.playback();
	 
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();
	 
		List <Emergency> emers = Emergency.findAllEmergencys();
		long count = emers.size();
	   
		Assert.assertTrue( count == esi.countAllEmergencys());
	}
	
	@Test
	public void testRemoveEmergency() {
	  
		EmergencyServiceImpl esi = new EmergencyServiceImpl();
	 
		Emergency.findEmergency(1);

		AnnotationDrivenStaticEntityMockingControl.expectReturn(
	    esi.findEmergency(0));
	 
		AnnotationDrivenStaticEntityMockingControl.playback();
	 
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();
	 
		Emergency emergency = Emergency.findEmergency(1);
		esi.deleteEmergency(emergency);
	   
		Set<ConstraintViolation<Emergency>> violations =
	    validator.validate(emergency, Default.class);
	 
		Assert.assertEquals(1, violations.size());
	 
		ConstraintViolation<Emergency> violation = violations.iterator().next();
	   
		Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
	    violation.getMessageTemplate());
	    List<Emergency> emers = esi.findAllEmergencys();
		Assert.assertTrue(!emers.contains(emergency));
	}
	
	@Test
	public void testFindEmergency() {
	  
		EmergencyServiceImpl esi = new EmergencyServiceImpl();
		
		Emergency emergency = esi.findEmergency(1);
	   
		Assert.assertTrue(emergency != null);
	}
	
	@Test
	public void testFindAllEmergencys() {
	  
		EmergencyServiceImpl esi = new EmergencyServiceImpl();

		AnnotationDrivenStaticEntityMockingControl.expectReturn(
	    esi.findAllEmergencys());
	 
		AnnotationDrivenStaticEntityMockingControl.playback();
	 
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();
	 
		List<Emergency> emers = esi.findAllEmergencys();
	 
		Assert.assertEquals(emers, esi.findAllEmergencys());
	}
	
	@Test
	public void testSaveEmergency() {
	  
		//todo
	}
	
	@Test
	public void testUpdateEmergency() {
	  
		//todo
	}
}
