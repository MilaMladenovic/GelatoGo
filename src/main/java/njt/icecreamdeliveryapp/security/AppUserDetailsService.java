package njt.icecreamdeliveryapp.security;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import njt.icecreamdeliveryapp.entity.impl.User;

import java.util.List;
import njt.icecreamdeliveryapp.repository.impl.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository users;

    public AppUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = users.findByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPasswordHash(),
                u.isEnabled(),
                true, // accountNonExpired
                true,// credentialsNonExpired
                true,// accountNonLocked
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()))
        );
    }
}
