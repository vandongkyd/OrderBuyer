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
package com.fernandocejas.android10.restrofit.repository;

import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.domain.repository.UserRepository;
import com.fernandocejas.android10.restrofit.enity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository {

    private final RetrofitHelper retrofitHelper;

    private final UserEntityDataMapper userEntityDataMapper;

    /**
     * Constructs a {@link UserRepository}.
     *
     * @param retrofitHelper
     * @param userEntityDataMapper
     */
    @Inject
    UserDataRepository(
            RetrofitHelper retrofitHelper,
            UserEntityDataMapper userEntityDataMapper) {
        this.retrofitHelper = retrofitHelper;
        this.userEntityDataMapper = userEntityDataMapper;
    }


    @Override
    public Observable<User> auto_signIn(String token) {
        return retrofitHelper
                .getRestApiService()
                .autoSignIn("Bearer " + token)
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> signUp(String name,
                                   String email,
                                   String password,
                                   String phone,
                                   String type,
                                   String phone_code) {
        return retrofitHelper
                .getRestApiService()
                .signUp(name,
                        email,
                        password,
                        phone,
                        type,
                        phone_code)
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> signIn(String email, String password) {
        return retrofitHelper
                .getRestApiService()
                .signIn(email, password)
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> active(String token, String active_code) {
        return retrofitHelper
                .getRestApiService()
                .activation("Bearer " + token, active_code)
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> resendActiveCode(String token) {
        return retrofitHelper
                .getRestApiService()
                .resendActivationCode("Bearer " + token)
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> retrieveUserInfo(String token) {
        return retrofitHelper
                .getRestApiService()
                .user_info("Bearer " + token)
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> changePassword(String token, String old_password, String password) {
        return retrofitHelper
                .getRestApiService()
                .change_password("Bearer " + token, old_password, password)
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> updateUserInfo(String token, String name, String phone, String avatar) {
        return retrofitHelper
                .getRestApiService()
                .update_profile("Bearer " + token, name, phone, avatar)
                .map(this.userEntityDataMapper::transform);
    }
}
