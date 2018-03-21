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
package com.fernandocejas.android10.order.presentation.mapper;

import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link User} (in the domain layer) to {@link UserModel} in the
 * presentation layer.
 */
@PerActivity
public class UserModelDataMapper {

    @Inject
    public UserModelDataMapper() {
    }

    /**
     * Transform a {@link User} into an {@link UserModel}.
     *
     * @param user Object to be transformed.
     * @return {@link UserModel}.
     */
    public UserModel transform(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        userModel.setPhone(user.getPhone());
        userModel.setAvatar(user.getAvatar());
        userModel.setStatus(user.getStatus());
        userModel.setType(user.getType());
        userModel.setActive_code(user.getActive_code());
        userModel.setUpdated_at(user.getUpdated_at());
        userModel.setCreated_at(user.getCreated_at());
        userModel.setToken(user.getToken());
        return userModel;
    }

    /**
     * Transform a Collection of {@link User} into a Collection of {@link UserModel}.
     *
     * @param userCollection Objects to be transformed.
     * @return List of {@link UserModel}.
     */
    public Collection<UserModel> transform(Collection<User> userCollection) {
        Collection<UserModel> userModelCollection;

        if (userCollection != null && !userCollection.isEmpty()) {
            userModelCollection = new ArrayList<>();
            for (User user : userCollection) {
                userModelCollection.add(transform(user));
            }
        } else {
            userModelCollection = Collections.emptyList();
        }

        return userModelCollection;
    }
}

