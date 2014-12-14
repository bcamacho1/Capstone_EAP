package edu.ndnu.capstone.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorizedUserServiceImpl implements AuthorizedUserService {

    public long countAllUsers() {
        return AuthorizedUser.countUsers();
    }

    public void deleteUser(AuthorizedUser user) {
        user.remove();
    }

    public AuthorizedUser findUser(Integer id) {
        return AuthorizedUser.findUser(id);
    }

    public AuthorizedUser findUserByUsername(String username) {
        return AuthorizedUser.findUserByUsername(username);
    }
    
    public AuthorizedUser findUserByEmail(String email) {
        return AuthorizedUser.findUserByEmail(email);
    }

    public List<AuthorizedUser> findAllUsers() {
        return AuthorizedUser.findAllUsers();
    }

    public List<AuthorizedUser> findUserEntries(int firstResult, int maxResults) {
        return AuthorizedUser.findUserEntries(firstResult, maxResults);
    }
    
    public List<AuthorizedUser> searchUsers(String name) {
        return AuthorizedUser.searchUsers(name);
    }

    public void saveUser(AuthorizedUser user) {
        user.persist();
    }

    public AuthorizedUser updateUser(AuthorizedUser user) {
        return user.merge();
    }
}
