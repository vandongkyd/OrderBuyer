package com.fernandocejas.android10.order.domain.repository_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.User_Buyer;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link User_Buyer} related data.
 */

public interface UserRepository_Buyer {

    /**
     * Get an {@link Observable} which will emit a object of {@link User_Buyer}.
     */
//    Observable<User_Buyer> auto_signIn_buyer(final String token);

    /**
     * Get an {@link Observable} which will emit a {@link User_Buyer}.
     *
     * @param name
     * @param email
     * @param password
     * @param phone
     * @param type
     * @param phone_code
     * @return
     */
    Observable<User_Buyer> signUp_buyer(final String name,
                            final String email,
                            final String password,
                            final String phone,
                            final String type,
                            final String phone_code);

    /**
     * Get an {@link Observable} which will emit a object of {@link User_Buyer}.
     */
    Observable<User_Buyer> signIn_buyer(final String email, final String password);

    /**
     * Get an {@link Observable} which will emit a object of {@link User_Buyer}.
     */
    Observable<User_Buyer> active_buyer(final String token, final String active_code);
//
    Observable<User_Buyer> resendActiveCode_buyer(final String token);
//
//    Observable<User_Buyer> retrieveUserInfo_buyer(final String token);
//
//    Observable<User_Buyer> changePassword_buyer(final String token, final String old_password, final String password);

    Observable<User_Buyer> updateUserInfo_buyer(final String token, final String name, final String phone, final String avatar);


}

