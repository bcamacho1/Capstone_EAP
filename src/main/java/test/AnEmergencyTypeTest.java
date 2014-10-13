package test;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker.Default;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import edu.ndnu.capstone.domain.EmergencyType;
import edu.ndnu.capstone.domain.EmergencyTypeDataOnDemand;

public class AnEmergencyTypeTest {

	@Test
	public void testEmergencyTypeMissingName() {

		EmergencyTypeDataOnDemand dod = new EmergencyTypeDataOnDemand();
		 
		EmergencyType.findEmergencyType(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientEmergencyType(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  EmergencyType user = EmergencyType.findEmergencyType(1);
		  user.setName(null);
		   
		  
		  Set<ConstraintViolation<EmergencyType>> violations =
		    validator.validate(user, Default.class);
		 
		  
		  Assert.assertEquals(1, violations.size());
		 
		  
		  ConstraintViolation<EmergencyType> violation = violations.iterator().next();
		   
		  
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  
		  Assert.assertEquals("name",
				violation.getPropertyPath().toString());
	
	}
}
