package com.greatbit.xgn.console.service;

import com.greatbit.xgn.console.domain.LoginUser;
import com.greatbit.xgn.console.domain.LoginUserRepository;
import com.greatbit.xgn.console.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private LoginUserRepository loginUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser op = loginUserRepository.findByUsername(username);
        List<GrantedAuthority> list = new ArrayList<>();
        op.getRoles().forEach((Role r) -> list.add(new SimpleGrantedAuthority(r.getRoleName())));
        User user = new User(op.getUsername(), op.getPassword(), list);
        System.out.println("user:" + user);
        return user;
    }
}