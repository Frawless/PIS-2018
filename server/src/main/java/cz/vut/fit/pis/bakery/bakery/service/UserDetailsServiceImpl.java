package cz.vut.fit.pis.bakery.bakery.service;

import cz.vut.fit.pis.bakery.bakery.model.User;
import cz.vut.fit.pis.bakery.bakery.model.Role;
import cz.vut.fit.pis.bakery.bakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        System.out.println("User: " + username);

        if (user == null){
            System.out.println("Not found");
            throw new UsernameNotFoundException("User was not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));

    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<>();

        String roleName = null;
        if (user.getRole() != null)
        {
            roleName = user.getRole().getName();
        }
        authorities.add(new SimpleGrantedAuthority(roleName));
        
        System.out.print("authorities :"+authorities);
        return authorities;
    }


}
