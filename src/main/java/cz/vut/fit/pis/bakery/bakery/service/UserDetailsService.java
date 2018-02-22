package cz.vut.fit.pis.bakery.bakery.service;

import cz.vut.fit.pis.bakery.bakery.model.BakeryUser;
import cz.vut.fit.pis.bakery.bakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        BakeryUser user = userRepository.findByEmail(s);
        System.out.println("User: " + s);

        if (user == null){
            System.out.println("Not found");
            throw new UsernameNotFoundException("User was not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities(user));

    }

    private List<GrantedAuthority> getGrantedAuthorities(BakeryUser user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        System.out.print("authorities :"+authorities);
        return authorities;
    }


}
