package edu.ndnu.capstone.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    public long countAllUsers() {
        return User.countUsers();
    }

    public void deleteUser(User user) {
        user.remove();
    }

    public User findUser(Integer id) {
        return User.findUser(id);
    }
    
    public User findUserByEmail(String email) {
        return User.findUserByEmail(email);
    }

    public List<User> findAllUsers() {
        return User.findAllUsers();
    }

    public List<User> findUserEntries(int firstResult, int maxResults) {
        return User.findUserEntries(firstResult, maxResults);
    }
    
    public List<User> searchUsers(String name) {
        return User.searchUsers(name);
    }

    public void saveUser(User user) {
        user.persist();
    }

    public User updateUser(User user) {
        return user.merge();
    }
}
