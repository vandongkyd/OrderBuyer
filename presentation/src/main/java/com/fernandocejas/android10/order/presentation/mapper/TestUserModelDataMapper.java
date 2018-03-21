package com.fernandocejas.android10.order.presentation.mapper;

import com.fernandocejas.android10.order.domain.TestUser;
import com.fernandocejas.android10.order.presentation.model.TestUserModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link TestUser} (in the domain layer) to {@link TestUserModel} in the
 * presentation layer.
 */
@PerActivity
public class TestUserModelDataMapper {

    @Inject
    public TestUserModelDataMapper() {
    }

    /**
     * Transform a {@link TestUser} into an {@link TestUserModel}.
     *
     * @param user Object to be transformed.
     * @return {@link TestUserModel}.
     */
    public TestUserModel transform(TestUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final TestUserModel userModel = new TestUserModel();
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
     * Transform a Collection of {@link TestUser} into a Collection of {@link TestUserModel}.
     *
     * @param userCollection Objects to be transformed.
     * @return List of {@link TestUserModel}.
     */
    public Collection<TestUserModel> transform(Collection<TestUser> userCollection) {
        Collection<TestUserModel> userModelCollection;

        if (userCollection != null && !userCollection.isEmpty()) {
            userModelCollection = new ArrayList<>();
            for (TestUser user : userCollection) {
                userModelCollection.add(transform(user));
            }
        } else {
            userModelCollection = Collections.emptyList();
        }

        return userModelCollection;
    }
}

