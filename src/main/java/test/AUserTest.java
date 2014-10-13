package test;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker.Default;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserDataOnDemand;

public class AUserTest {

	@Test
	public void testUserMissingName() {

		  UserDataOnDemand dod = new UserDataOnDemand();
		 
		  User.findUser(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientUser(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  User user = User.findUser(1);
		  user.setName(null);
		   
		  
		  Set<ConstraintViolation<User>> violations =
		    validator.validate(user, Default.class);
		 
		  
		  Assert.assertEquals(1, violations.size());
		 
		  
		  ConstraintViolation<User> violation = violations.iterator().next();
		   
		  
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  
		  Assert.assertEquals("name",
				violation.getPropertyPath().toString());
	
	}
	
	@Test
	public void testUserMissingEmail() {

		  UserDataOnDemand dod = new UserDataOnDemand();
		 
		  User.findUser(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientUser(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  User user = User.findUser(1);
		  user.setEmail(null);
		   
		  
		  Set<ConstraintViolation<User>> violations =
		    validator.validate(user, Default.class);
		 
		  
		  Assert.assertEquals(1, violations.size());
		 
		  
		  ConstraintViolation<User> violation = violations.iterator().next();
		   
		  
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  
		  Assert.assertEquals("email",
				violation.getPropertyPath().toString());
	
	}
	
	@Test
	public void testUserMissingUsername() {

		  UserDataOnDemand dod = new UserDataOnDemand();
		 
		  User.findUser(1);

		  AnnotationDrivenStaticEntityMockingControl.expectReturn(
		     dod.getNewTransientUser(0));
		 
		  AnnotationDrivenStaticEntityMockingControl.playback();
		 
		  LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		  validator.afterPropertiesSet();
		 
		  User user = User.findUser(1);
		  user.setUsername(null);
		   
		  
		  Set<ConstraintViolation<User>> violations =
		    validator.validate(user, Default.class);
		 
		  
		  Assert.assertEquals(1, violations.size());
		 
		  
		  ConstraintViolation<User> violation = violations.iterator().next();
		   
		  
		  Assert.assertEquals("{javax.validation.constraints.NotNull.message}",
		    violation.getMessageTemplate());
		    
		  
		  Assert.assertEquals("name",
				violation.getPropertyPath().toString());
	
	}
	
}
