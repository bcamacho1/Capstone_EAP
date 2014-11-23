package edu.ndnu.capstone.domain;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.UserType.class })
public interface UserTypeService {

    public abstract long countAllUserTypes();


    public abstract void deleteUserType(UserType userType);


    public abstract UserType findUserType(Integer id);


    public abstract List<UserType> findAllUserTypes();


    public abstract List<UserType> findUserTypeEntries(int firstResult, int maxResults);


    public abstract void saveUserType(UserType userType);


    public abstract UserType updateUserType(UserType userType);

}
