package self.heresay;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(callSuper = false)
@Setter @Getter
public class CreateUserRequest{

	private String username;
	private String password;

}
