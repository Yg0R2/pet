package com.yg0r2.user.dao.repository;

import com.yg0r2.user.dao.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsById(long id);

    Optional<UserEntity> findUserEntityByNickName(String nickName);

}
