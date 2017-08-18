package com.greatbit.miit.ext.security;

import com.greatbit.miit.ext.entity.Operator;
import com.greatbit.miit.ext.entity.repo.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Operator operator = operatorRepository.findByUsername(username);
        if (operator != null) {
            User user = new User(username, operator.getPassword(), operator.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList()));
            return user;
        } else throw new UsernameNotFoundException(username);
    }
}
