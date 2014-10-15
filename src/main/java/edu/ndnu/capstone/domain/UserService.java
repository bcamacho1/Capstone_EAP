package edu.ndnu.capstone.domain;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.User.class })
public interface UserService {

	public abstract long countAllUsers();


	public abstract void deleteUser(User user);


	public abstract User findUser(Integer id);


	public abstract List<User> findAllUsers();


	public abstract List<User> findUserEntries(int firstResult, int maxResults);


	public abstract void saveUser(User user);


	public abstract User updateUser(User user);

}
