package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.TestUser;
import com.fernandocejas.android10.order.domain.User;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link TestUser} related data.
 */

public interface TestUserRepository {
    /**
     * Get an {@link Observable} which will emit a object of {@link TestUser}.
     */
    Observable<TestUser> auto_signIn(final String token);

    /**
     * Get an {@link Observable} which will emit a {@link TestUser}.
     *
     * @param name
     * @param email
     * @param password
     * @param phone
     * @param type
     * @param phone_code
     * @return
     */
    Observable<TestUser> signUp(final String name,
                            final String email,
                            final String password,
                            final String phone,
                            final String type,
                            final String phone_code);

    /**
     * Get an {@link Observable} which will emit a object of {@link TestUser}.
     */
    Observable<TestUser> signIn(final String email, final String password);

    /**
     * Get an {@link Observable} which will emit a object of {@link User}.
     */
    Observable<TestUser> active(final String token, final String active_code);
    


}
