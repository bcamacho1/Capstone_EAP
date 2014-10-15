package edu.ndnu.capstone.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserTypeServiceImpl implements UserTypeService {

	public long countAllUserTypes() {
        return UserType.countUserTypes();
    }

	public void deleteUserType(UserType userType) {
        userType.remove();
    }

	public UserType findUserType(Integer id) {
        return UserType.findUserType(id);
    }

	public List<UserType> findAllUserTypes() {
        return UserType.findAllUserTypes();
    }

	public List<UserType> findUserTypeEntries(int firstResult, int maxResults) {
        return UserType.findUserTypeEntries(firstResult, maxResults);
    }

	public void saveUserType(UserType userType) {
        userType.persist();
    }

	public UserType updateUserType(UserType userType) {
        return userType.merge();
    }
}
