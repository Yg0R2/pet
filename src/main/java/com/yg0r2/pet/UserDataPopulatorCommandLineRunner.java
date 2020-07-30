package com.yg0r2.pet;

import com.yg0r2.user.dao.model.UserEntity;
import com.yg0r2.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDataPopulatorCommandLineRunner implements CommandLineRunner {

    private static final String DEFAULT_USER_NICK_NAME = "test";
    private static final String DEFAULT_USER_PASSWORD = "test";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        createDefaultUser();
    }

    private UserEntity createDefaultUser() {
        UserEntity userEntity = new UserEntity();

        userEntity.setNickName(DEFAULT_USER_NICK_NAME);
        userEntity.setPassword(bCryptPasswordEncoder.encode(DEFAULT_USER_PASSWORD));

        return userService.create(userEntity);
    }

}
