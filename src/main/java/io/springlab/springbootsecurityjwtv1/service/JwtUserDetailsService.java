package io.springlab.springbootsecurityjwtv1.service;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("priyo")){
            return new User("priyo","$2y$12$ixY/FIpYaMTs5zwixxs2NOBkPNiQGY2LRIffUf2w.RwpjHNLiiVdy",new ArrayList<>());
        }
        else{
             throw new UsernameNotFoundException("Invalid Credentials");
        }
    }
}
