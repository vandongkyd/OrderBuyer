package com.fernandocejas.android10.restrofit.enity.mapper;

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.TestUser;
import com.fernandocejas.android10.restrofit.enity.UserEntity;
import com.fernandocejas.android10.restrofit.enity.UserEntityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link TestUser} in the
 * domain layer.
 */
@Singleton
public class TestUserEntityDataMapper {
    @Inject
    TestUserEntityDataMapper() {
    }

    /**
     * Transform a {@link UserEntity} into an {@link TestUser}.
     *
     * @param userEntity Object to be transformed.
     * @return {@link TestUser} if valid {@link UserEntity} otherwise null.
     */
    public TestUser transform(UserEntity userEntity) {
        TestUser user = null;
        if (userEntity != null) {
            user = new TestUser();
            user.setId(userEntity.getId());
            user.setName(userEntity.getName());
            user.setEmail(userEntity.getEmail());
            user.setPhone(userEntity.getPhone());
            user.setAvatar(userEntity.getAvatar());
            user.setStatus(userEntity.getStatus());
            user.setType(userEntity.getType());
            user.setActive_code(userEntity.getActive_code());
            user.setUpdated_at(userEntity.getUpdated_at());
            user.setCreated_at(userEntity.getCreated_at());
            user.setToken(userEntity.getToken());
        }
        return user;
    }

    /**
     * Transform a List of {@link UserEntity} into a Collection of {@link TestUser}.
     *
     * @param userEntityCollection Object Collection to be transformed.
     * @return {@link TestUser} if valid {@link UserEntity} otherwise null.
     */
    public List<TestUser> transform(Collection<UserEntity> userEntityCollection) {
        final List<TestUser> userList = new ArrayList<>(20);
        for (UserEntity userEntity : userEntityCollection) {
            final TestUser user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }
        return userList;
    }

    /**
     * Transform {@link UserEntityResponse} into {@link TestUser}.
     *
     * @param userEntityResponse Object to be transformed.
     * @return {@link Order} if valid {@link UserEntityResponse} otherwise null.
     */
    public TestUser transform(UserEntityResponse userEntityResponse) throws Exception {
        TestUser user = null;
        if (userEntityResponse != null) {
            if (userEntityResponse.status() == false) {
                throw new Exception(userEntityResponse.message());
            }
            UserEntity userEntity = userEntityResponse.data();
            user = transform(userEntity);
        }
        return user;
    }
}
