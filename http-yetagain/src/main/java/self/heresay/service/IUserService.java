package self.heresay.service;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import self.heresay.model.User;


public interface IUserService {
	User findByUserName(String username);
	User createNewUser(User user,Set<String> roles);
	boolean isUsernameExist(String username);
	UserDetails loadUserByUsername(String username);
}
