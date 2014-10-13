package test;

import java.util.Set;
import javax.validation.ConstraintViolation;
import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker.Default;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import edu.ndnu.capstone.domain.Evacuation;
import edu.ndnu.capstone.domain.EvacuationDataOnDemand;

public class EvacuationAreaTest {

	@Test
	public void testEvacuationAreaMissingArea() {

		  EvacuationDataOnDemand dod = new EvacuationDataOnDemand();
		 
		  Evacuation.findEvacuation(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientEvacuation(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  Evacuation user = Evacuation.findEvacuation(1);
		  user.setArea(null);
		   
		  
		  Set<ConstraintViolation<Evacuation>> violations =
		    validator.validate(user, Default.class);
		 
		  
		  Assert.assertEquals(1, violations.size());
		 
		  
		  ConstraintViolation<Evacuation> violation = violations.iterator().next();
		   
		  
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  
		  Assert.assertEquals("area",
				violation.getPropertyPath().toString());
	
	}
}
