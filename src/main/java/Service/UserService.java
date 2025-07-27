package Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ShowJob.JobProtal.Entity.User;
import com.ShowJob.JobProtal.Repositories.UserRepository;

@Service
public class UserService {
	
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    public void saveUser(User user) {
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        user.setRole("ROLE_USER");
	        userRepository.save(user);
	    }

	    public User findByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }

}
