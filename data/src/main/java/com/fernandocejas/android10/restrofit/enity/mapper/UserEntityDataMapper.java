/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.restrofit.enity.mapper;

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.restrofit.enity.UserEntity;
import com.fernandocejas.android10.restrofit.enity.UserEntityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
@Singleton
public class UserEntityDataMapper {

    @Inject
    UserEntityDataMapper() {
    }

    /**
     * Transform a {@link UserEntity} into an {@link User}.
     *
     * @param userEntity Object to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public User transform(UserEntity userEntity) {
        User user = null;
        if (userEntity != null) {
            user = new User();
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
     * Transform a List of {@link UserEntity} into a Collection of {@link User}.
     *
     * @param userEntityCollection Object Collection to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public List<User> transform(Collection<UserEntity> userEntityCollection) {
        final List<User> userList = new ArrayList<>(20);
        for (UserEntity userEntity : userEntityCollection) {
            final User user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }
        return userList;
    }

    /**
     * Transform {@link UserEntityResponse} into {@link User}.
     *
     * @param userEntityResponse Object to be transformed.
     * @return {@link Order} if valid {@link UserEntityResponse} otherwise null.
     */
    public User transform(UserEntityResponse userEntityResponse) throws Exception {
        User user = null;
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
