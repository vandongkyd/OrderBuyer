package com.fernandocejas.android10.order.domain.interactor_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.User_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.UserRepository_Buyer;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/12/18.
 */

public class RegisterUser_Buyer extends UseCase<User_Buyer, RegisterUser_Buyer.Params> {

    private final UserRepository_Buyer userRepository_buyer;

    @Inject
    RegisterUser_Buyer(UserRepository_Buyer userRepository_buyer, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository_buyer = userRepository_buyer;
    }

    @Override
    protected Observable<User_Buyer> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository_buyer.signUp_buyer(params.name,
                params.email,
                params.password,
                params.phone,
                params.type,
                params.phone_code);
    }

    public static final class Params {

        final String name;
        final String email;
        final String password;
        final String phone;
        final String type;
        final String phone_code;

        private Params(String name,
                       String email,
                       String password,
                       String phone,
                       String type,
                       String phone_code) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.phone = phone;
            this.type = type;
            this.phone_code = phone_code;
        }

        public static RegisterUser_Buyer.Params forUserRegister(String name,
                                                          String email,
                                                          String password,
                                                          String phone,
                                                          String type,
                                                          String phone_code) {
            return new RegisterUser_Buyer.Params(name, email, password, phone, type, phone_code);
        }
    }
}
