package com.yg0r2.user.service;

import com.yg0r2.user.dao.model.UserEntity;
import com.yg0r2.user.dao.repository.UserRepository;
import com.yg0r2.user.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public Set<UserEntity> getAll() {
        return new HashSet<>(userRepository.findAll());
    }

    public UserEntity getById(long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User does not exist with the requested id: " + id));
    }

    public UserEntity getByNickName(String nickName) {
        return userRepository.findUserEntityByNickName(nickName)
            .orElseThrow(() -> new UserNotFoundException("User does not exist with the requested nick name: " + nickName));
    }

    public boolean isExistById(long id) {
        return userRepository.existsById(id);
    }

}
