package self.heresay;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(callSuper = false)
@Setter @Getter
public class CreateUserRequest{

	private String username;
	private String password;
	private String email;
	private Set<String> roles;

}
