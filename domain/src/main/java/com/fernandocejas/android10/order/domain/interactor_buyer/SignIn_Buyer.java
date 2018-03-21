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
 * This class is an implementation of {@link UseCase} that represents a use case for
 * sigIn.
 */
public class SignIn_Buyer extends UseCase<User_Buyer,SignIn_Buyer.Params> {

    private final UserRepository_Buyer userRepository_buyer;

    @Inject
    SignIn_Buyer(UserRepository_Buyer userRepository_buyer, ThreadExecutor threadExecutor,
           PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository_buyer = userRepository_buyer;
    }

    @Override
    protected Observable<User_Buyer> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository_buyer.signIn_buyer(params.email, params.password);
    }

    public static final class Params {

        private final String email;

        private final String password;

        private Params(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public static SignIn_Buyer.Params forSignIn(String email, String password) {
            return new SignIn_Buyer.Params(email, password);
        }
    }
}
