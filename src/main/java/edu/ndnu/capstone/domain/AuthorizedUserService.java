package edu.ndnu.capstone.domain;
import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { edu.ndnu.capstone.domain.AuthorizedUser.class })
public interface AuthorizedUserService {

    public abstract long countAllUsers();


    public abstract void deleteUser(AuthorizedUser user);


    public abstract AuthorizedUser findUser(Integer id);


    public abstract AuthorizedUser findUserByUsername(String username);
    
    
    public abstract AuthorizedUser findUserByEmail(String email);

    
    public abstract List<AuthorizedUser> findAllUsers();


    public abstract List<AuthorizedUser> findUserEntries(int firstResult, int maxResults);
    
    
    public abstract List<AuthorizedUser> searchUsers(String name);


    public abstract void saveUser(AuthorizedUser user);


    public abstract AuthorizedUser updateUser(AuthorizedUser user);

}
