package com.yg0r2.user.web.transformer;

import com.yg0r2.core.web.transformer.CoreTransformer;
import com.yg0r2.user.api.model.UserEntry;
import com.yg0r2.user.dao.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer implements CoreTransformer<UserEntry, UserEntity> {

    @Override
    public UserEntry transform(UserEntity userEntity) {
        return new UserEntry.Builder()
            .withId(userEntity.getId())
            .withNickName(userEntity.getNickName())
            .withPassword(userEntity.getPassword())
            .build();
    }

    @Override
    public UserEntity transform(UserEntry userEntry) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userEntry.getId());
        userEntity.setNickName(userEntry.getNickName());
        userEntity.setPassword(userEntry.getPassword());

        return userEntity;
    }

}
