package test;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker.Default;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import edu.ndnu.capstone.domain.UserType;
import edu.ndnu.capstone.domain.UserTypeDataOnDemand;

public class AUserTypeTest {

	@Test
	public void testUserTypeMissingName() {

		  UserTypeDataOnDemand dod = new UserTypeDataOnDemand();
		 
		  UserType.findUserType(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientUserType(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  UserType user = UserType.findUserType(1);
		  user.setName(null);
		   
		  
		  Set<ConstraintViolation<UserType>> violations =
		    validator.validate(user, Default.class);
		 
		  
		  Assert.assertEquals(1, violations.size());
		 
		  
		  ConstraintViolation<UserType> violation = violations.iterator().next();
		   
		  
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  
		  Assert.assertEquals("name",
				violation.getPropertyPath().toString());
	
	}
}
