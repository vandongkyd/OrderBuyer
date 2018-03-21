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
package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.User;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link User} related data.
 */
public interface UserRepository {

    /**
     * Get an {@link Observable} which will emit a object of {@link User}.
     */
    Observable<User> auto_signIn(final String token);

    /**
     * Get an {@link Observable} which will emit a {@link User}.
     *
     * @param name
     * @param email
     * @param password
     * @param phone
     * @param type
     * @param phone_code
     * @return
     */
    Observable<User> signUp(final String name,
                            final String email,
                            final String password,
                            final String phone,
                            final String type,
                            final String phone_code);

    /**
     * Get an {@link Observable} which will emit a object of {@link User}.
     */
    Observable<User> signIn(final String email, final String password);

    /**
     * Get an {@link Observable} which will emit a object of {@link User}.
     */
    Observable<User> active(final String token, final String active_code);

    Observable<User> resendActiveCode(final String token);

    Observable<User> retrieveUserInfo(final String token);

    Observable<User> changePassword(final String token, final String old_password, final String password);

    Observable<User> updateUserInfo(final String token, final String name, final String phone, final String avatar);


}
