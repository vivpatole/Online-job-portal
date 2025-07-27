package Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ShowJob.JobProtal.Entity.User;

public class CustomUserDetails implements UserDetails {
	
	  private User user;

	    public CustomUserDetails(User user1) {
	        this.user = user1;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return Collections.emptyList(); // if you don't have roles
	    }

	    @Override
	    public String getPassword() {
	        return user.getPassword();
	    }

	    @Override
	    public String getUsername() {
	        return user.getName();
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
	

}
