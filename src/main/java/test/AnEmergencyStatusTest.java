package test;


import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker.Default;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import edu.ndnu.capstone.domain.EmergencyStatus;
import edu.ndnu.capstone.domain.EmergencyStatusDataOnDemand;

public class AnEmergencyStatusTest {

	@Test
	public void testEmergencyStatusMissingName() {

		EmergencyStatusDataOnDemand dod = new EmergencyStatusDataOnDemand();
		 
		EmergencyStatus.findAllEmergencyStatuses();

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientEmergencyStatus(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  List <EmergencyStatus> statuses = EmergencyStatus.findAllEmergencyStatuses();
		  //iterate through the list setting to null.
		  
		  while(!statuses.isEmpty()){
			  EmergencyStatus temp = statuses.remove(0);
			  temp.setName(null);
		  
		   
		  
		  Set<ConstraintViolation<EmergencyStatus>> violations =
		    validator.validate(temp, Default.class);
		  
		  
		  Assert.assertEquals(1, violations.size());
		 
		  
		  ConstraintViolation<EmergencyStatus> violation = violations.iterator().next();
		   
		  
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  
		  Assert.assertEquals("name",
				violation.getPropertyPath().toString());
		  }
	
	}
}
