package com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer;

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.domain.Model_buyer.User_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.UserEntityResponse_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.UserEntity_Buyer;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link UserEntity_Buyer} (in the data layer) to {@link User_Buyer} in the
 * domain layer.
 */
@Singleton
public class UserEntityDataMapper_Buyer {
    @Inject
    UserEntityDataMapper_Buyer() {
    }
    /**
     * Transform a {@link UserEntity_Buyer} into an {@link User_Buyer}.
     *
     * @param userEntity_buyer Object to be transformed.
     * @return {@link User_Buyer} if valid {@link UserEntity_Buyer} otherwise null.
     */

    public User_Buyer transform(UserEntity_Buyer userEntity_buyer) {
        User_Buyer user_buyer = null;
        if (userEntity_buyer != null) {
            user_buyer = new User_Buyer();
            user_buyer.setId(userEntity_buyer.getId());
            user_buyer.setName(userEntity_buyer.getName());
            user_buyer.setEmail(userEntity_buyer.getEmail());
            user_buyer.setPhone(userEntity_buyer.getPhone());
            user_buyer.setPhonecode(userEntity_buyer.getPhonecode());
            user_buyer.setAvatar(userEntity_buyer.getAvatar());
            user_buyer.setStatus(userEntity_buyer.getStatus());
            user_buyer.setType(userEntity_buyer.getType());
            user_buyer.setDocument(userEntity_buyer.getDocument());
            user_buyer.setUpdated_at(userEntity_buyer.getUpdated_at());
            user_buyer.setCreated_at(userEntity_buyer.getCreated_at());
            user_buyer.setToken(userEntity_buyer.getToken());
        }
        return user_buyer;
    }
    /**
     * Transform a List of {@link UserEntity_Buyer} into a Collection of {@link User_Buyer}.
     *
     * @param userEntityCollection_buyer Object Collection to be transformed.
     * @return {@link User_Buyer} if valid {@link UserEntity_Buyer} otherwise null.
     */
    public List<User_Buyer> transform(Collection<UserEntity_Buyer> userEntityCollection_buyer) {
        final List<User_Buyer> userList_buyer = new ArrayList<>(20);
        for (UserEntity_Buyer userEntity_buyer : userEntityCollection_buyer) {
            final User_Buyer user_buyer = transform(userEntity_buyer);
            if (user_buyer != null) {
                userList_buyer.add(user_buyer);
            }
        }
        return userList_buyer;
    }
    /**
     * Transform {@link UserEntityResponse_Buyer} into {@link User_Buyer}.
     *
     * @param userEntityResponse_buyer Object to be transformed.
     * @return {@link Order} if valid {@link UserEntityResponse_Buyer} otherwise null.
     */
    public User_Buyer transform(UserEntityResponse_Buyer userEntityResponse_buyer) throws Exception {
        User_Buyer user_buyer = null;
        if (userEntityResponse_buyer != null) {
            if (userEntityResponse_buyer.status() == false) {
                throw new Exception(userEntityResponse_buyer.message());
            }
            UserEntity_Buyer userEntity_buyer = userEntityResponse_buyer.data();
            user_buyer = transform(userEntity_buyer);
        }
        return user_buyer;
    }

}
