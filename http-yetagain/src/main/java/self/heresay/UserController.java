package self.heresay;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import self.heresay.model.Role;
import self.heresay.model.User;
import self.heresay.service.IUserService;

@RestController
@RequestMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController {

	private IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping("/create")
	public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {

		if (request.getUsername() == null || request.getUsername().equals("")) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDUSERNAME);
		}
		
		if (request.getPassword() == null || request.getPassword().equals("")) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDPASSWORD);
		}
		boolean isUsernameExist = userService.isUsernameExist(request.getUsername());
		if (isUsernameExist) {
			throw new BadCredentialsException(Constants.MESSAGE_SAMEUSERNAMEEXIST);
		}
        User rUser = new User();
        rUser.setEnabled(true);
        rUser.setPassword(request.getPassword());
        rUser.setUsername(request.getUsername());
        rUser.setEmail(request.getEmail());
		User user = userService.createNewUser(rUser,request.getRoles());
		CreateUserResponse response = new CreateUserResponse();
		response.setUsername(user.getUsername());
		return response;
	}

}
