package self.heresay.service;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import self.heresay.BankTxFacadeRemote;
import self.heresay.UserNotFoundException;
import self.heresay.model.User;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private BankTxFacadeRemote bankTxFacade;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User createNewUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		bankTxFacade.addUser(user);
		return user;
	}

	@Override
	public User findByUserName(String username) {
		User user = bankTxFacade.getUserByName(username);
		if (user == null)
			throw new UserNotFoundException(username);
		else
			return user;
	}


	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = bankTxFacade.getUserByName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
	}

	@Override
	public boolean isUsernameExist(String username) {
		User user = bankTxFacade.getUserByName(username);
		return user != null;
	}

}
