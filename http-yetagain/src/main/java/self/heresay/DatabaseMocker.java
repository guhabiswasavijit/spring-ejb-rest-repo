package self.heresay;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


@Configuration
class DatabaseMocker {
	@Value("${create.demo.user}")
	private boolean createUser;
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	CommandLineRunner initDatabase(UserController userController) {
		return args -> {
			Set<String> roles = new HashSet<String>();
			roles.addAll(Arrays.asList(new String[]{"USER","ADMIN"}));
			CreateUserRequest cnuRequest = new CreateUserRequest();
			cnuRequest.setUsername("Mert");
			cnuRequest.setEmail("mert@devil.com");
			cnuRequest.setPassword("mert123");
			cnuRequest.setRoles(roles);
			CreateUserRequest cnuRequest2 = new CreateUserRequest();
			cnuRequest2.setUsername("Mert2");
			cnuRequest2.setEmail("mert2@devil.com");
			cnuRequest2.setPassword("mert1234");
			cnuRequest2.setRoles(roles);
			CreateUserRequest cnuRequest3 = new CreateUserRequest();
			cnuRequest3.setUsername("Mert3");
			cnuRequest3.setEmail("mert3@devil.com");
			cnuRequest3.setPassword("mert12345");
			cnuRequest3.setRoles(roles);
			if(createUser) {
				userController.createUser(cnuRequest);
				userController.createUser(cnuRequest2);
				userController.createUser(cnuRequest3);
			}
		};
	}
}
