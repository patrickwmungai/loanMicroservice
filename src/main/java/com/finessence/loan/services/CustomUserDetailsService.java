package com.finessence.loan.services;

import com.finessence.loan.entities.Users;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    GlobalFunctions globalFunctions;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String ssoId)
            throws UsernameNotFoundException {
        Users user = globalFunctions.findByUserName(ssoId);
        System.out.println("User : " + user);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                user.getStatus() == 1, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Users user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return authorities;
    }

}
