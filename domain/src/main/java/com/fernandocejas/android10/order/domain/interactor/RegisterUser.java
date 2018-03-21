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
package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.domain.repository.UserRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * register user.
 */
public class RegisterUser extends UseCase<User, RegisterUser.Params> {

    private final UserRepository userRepository;

    @Inject
    RegisterUser(UserRepository userRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<User> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.signUp(params.name,
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

        public static RegisterUser.Params forUserRegister(String name,
                                                          String email,
                                                          String password,
                                                          String phone,
                                                          String type,
                                                          String phone_code) {
            return new RegisterUser.Params(name, email, password, phone, type, phone_code);
        }
    }
}
