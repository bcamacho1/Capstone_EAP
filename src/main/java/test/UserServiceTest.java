package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserServiceImpl;

public class UserServiceTest {

	@Test
	public void testCountAllUsers() {
		UserServiceImpl usi = new UserServiceImpl();
	    
		long count = usi.countAllUsers();
		Assert.assertEquals(count, usi.countAllUsers());
	}
	
	@Test
	public void testDeleteUser() {
		User user = User.findUser(1);
	    
		UserServiceImpl usi = new UserServiceImpl();
		usi.deleteUser(user);
		
		List <User> users = usi.findAllUsers();
		Assert.assertTrue(!users.contains(user));
	}
	
	@Test
	public void testFindUser() {
		UserServiceImpl usi = new UserServiceImpl();
		User user = usi.findUser(1);
	  
		Assert.assertTrue(user != null);
	}
	
	@Test
	public void testSaveUser(User user) {
		UserServiceImpl usi = new UserServiceImpl();
	    usi.saveUser(user);
	    List <User> users = usi.findAllUsers();
	    Assert.assertTrue(users.contains(user));
	}
	
	@Test
	public void testUpdateUser(User user) {
		UserServiceImpl usi = new UserServiceImpl();
	    usi.updateUser(user);
	    List <User> users = usi.findAllUsers();
	    Assert.assertTrue(users.contains(user));
	}
}
