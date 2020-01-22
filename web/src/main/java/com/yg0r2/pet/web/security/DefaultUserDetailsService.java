package com.yg0r2.pet.web.security;

import com.yg0r2.user.dao.model.UserEntity;
import com.yg0r2.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getByNickName(username);

        return new User(userEntity.getNickName(), userEntity.getPassword(), List.of());
    }

}
