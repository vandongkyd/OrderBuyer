package com.fernandocejas.android10.order.presentation.mapper_buyer;



import com.fernandocejas.android10.order.domain.Model_buyer.User_Buyer;
import com.fernandocejas.android10.order.presentation.Model_buyer.UserModel_Buyer;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by vandongluong on 3/19/18.
 */
@PerActivity
public class UserModelDataMapper_Buyer {
    @Inject
    public UserModelDataMapper_Buyer() {
    }

    public UserModel_Buyer transform(User_Buyer user_buyer) {
        if (user_buyer == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final UserModel_Buyer userModel = new UserModel_Buyer();
            userModel.setId(user_buyer.getId());
            userModel.setName(user_buyer.getName());
            userModel.setEmail(user_buyer.getEmail());
            userModel.setPhone(user_buyer.getPhone());
            userModel.setPhonecode(user_buyer.getPhonecode());
            userModel.setAvatar(user_buyer.getAvatar());
            userModel.setStatus(user_buyer.getStatus());
            userModel.setType(user_buyer.getType());
            userModel.setDocument(user_buyer.getDocument());
            userModel.setUpdated_at(user_buyer.getUpdated_at());
            userModel.setCreated_at(user_buyer.getCreated_at());
            userModel.setToken(user_buyer.getToken());
        return userModel;
    }

    /**
     * Transform a Collection of {@link User_Buyer} into a Collection of {@link UserModel_Buyer}.
     *
     * @param userCollectionbuyer Objects to be transformed.
     * @return List of {@link UserModel_Buyer}.
     */
    public Collection<UserModel_Buyer> transform(Collection<User_Buyer> userCollectionbuyer) {
        Collection<UserModel_Buyer> userModelCollectionbuyer;

        if (userCollectionbuyer != null && !userCollectionbuyer.isEmpty()) {
            userModelCollectionbuyer = new ArrayList<>();
            for (User_Buyer user : userCollectionbuyer) {
                userModelCollectionbuyer.add(transform(user));
            }
        } else {
            userModelCollectionbuyer = Collections.emptyList();
        }

        return userModelCollectionbuyer;
    }
}
