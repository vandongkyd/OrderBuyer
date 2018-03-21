package com.fernandocejas.android10.restrofit.repository;

import com.fernandocejas.android10.order.domain.TestUser;
import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.domain.repository.TestUserRepository;
import com.fernandocejas.android10.order.domain.repository.UserRepository;
import com.fernandocejas.android10.restrofit.enity.mapper.TestUserEntityDataMapper;
import com.fernandocejas.android10.restrofit.enity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link TestUserRepository} for retrieving user data.
 */
@Singleton
public class TestUserDataRepository implements TestUserRepository {

    private final RetrofitHelper retrofitHelper;

    private final TestUserEntityDataMapper userEntityDataMapper;

    /**
     * Constructs a {@link UserRepository}.
     *
     * @param retrofitHelper
     * @param userEntityDataMapper
     */
    @Inject
    TestUserDataRepository(
            RetrofitHelper retrofitHelper,
            TestUserEntityDataMapper userEntityDataMapper) {
        this.retrofitHelper = retrofitHelper;
        this.userEntityDataMapper = userEntityDataMapper;
    }


    @Override
    public Observable<TestUser> auto_signIn(String token) {
        return retrofitHelper
                .getRestApiService()
                .autoSignIn("Bearer " + token)
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<TestUser> signUp(String name,
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
    public Observable<TestUser> signIn(String email, String password) {
        return retrofitHelper
                .getRestApiService()
                .signIn(email, password)
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<TestUser> active(String token, String active_code) {
        return retrofitHelper
                .getRestApiService()
                .activation("Bearer " + token, active_code)
                .map(this.userEntityDataMapper::transform);
    }

}