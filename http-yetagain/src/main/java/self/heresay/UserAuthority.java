package self.heresay;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	private String role;
	public UserAuthority(String role) {
		this.role = role;
	}
	@Override
	public String getAuthority() {
		return this.role;
	}
}
