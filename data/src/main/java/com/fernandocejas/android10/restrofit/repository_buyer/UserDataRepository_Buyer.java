package com.fernandocejas.android10.restrofit.repository_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.User_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.UserRepository_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer.UserEntityDataMapper_Buyer;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper_Buyer;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/12/18.
 */
@Singleton
public class UserDataRepository_Buyer implements UserRepository_Buyer {

    private final RetrofitHelper_Buyer retrofitHelper;

    private final UserEntityDataMapper_Buyer userEntityDataMapper_buyer;

    /**
     * Constructs a {@link UserRepository_Buyer}.
     *
     * @param retrofitHelper
     * @param userEntityDataMapper_buyer
     */
    @Inject
    UserDataRepository_Buyer(
            RetrofitHelper_Buyer retrofitHelper,
            UserEntityDataMapper_Buyer userEntityDataMapper_buyer) {
        this.retrofitHelper = retrofitHelper;
        this.userEntityDataMapper_buyer = userEntityDataMapper_buyer;
    }

    @Override
    public Observable<User_Buyer> signUp_buyer(String name,
                                               String email,
                                               String password,
                                               String phone,
                                               String type,
                                               String phone_code) {
        return retrofitHelper
                .getRestApiService()
                .signUp_buyer(name,
                        email,
                        password,
                        phone,
                        type,
                        phone_code)
                .map(this.userEntityDataMapper_buyer::transform);
    }

    @Override
    public Observable<User_Buyer> signIn_buyer(String email, String password) {
        return retrofitHelper
                .getRestApiService()
                .signIn_buyer(email, password)
                .map(this.userEntityDataMapper_buyer::transform);
    }

    @Override
    public Observable<User_Buyer> active_buyer(String token, String active_code) {
        return retrofitHelper
                .getRestApiService()
                .activation_buyer("Bearer " + token, active_code)
                .map(this.userEntityDataMapper_buyer::transform);
    }

    @Override
    public Observable<User_Buyer> resendActiveCode_buyer(String token) {
        return null;
    }

    @Override
    public Observable<User_Buyer> updateUserInfo_buyer(String token, String name, String phone, String avatar) {
        return null;
    }
}
