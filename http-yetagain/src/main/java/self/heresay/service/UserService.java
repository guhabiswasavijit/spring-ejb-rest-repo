package self.heresay.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import self.heresay.BankTxFacadeLocal;
import self.heresay.UserAuthority;
import self.heresay.UserNotFoundException;
import self.heresay.model.Role;
import self.heresay.model.User;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private BankTxFacadeLocal bankTxFacade;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User createNewUser(User user,Set<String> roles) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Role> roleSet = new HashSet<Role>();
		roles.forEach(role -> {
			roleSet.add(bankTxFacade.findRoleByName(role));
		});
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
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getRoles().forEach(role -> {
			authorities.add(new UserAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
	}

	@Override
	public boolean isUsernameExist(String username) {
		User user = bankTxFacade.getUserByName(username);
		return user != null;
	}

}
