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
 * Created by vandongluong on 3/19/18.
 */

public class ResendActiveCode_Buyer extends UseCase<User_Buyer, ResendActiveCode_Buyer.Params> {

    private final UserRepository_Buyer userRepository_buyer;

    @Inject
    ResendActiveCode_Buyer(UserRepository_Buyer userRepository_buyer, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository_buyer = userRepository_buyer;
    }

    @Override
    protected Observable<User_Buyer> buildUseCaseObservable(ResendActiveCode_Buyer.Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository_buyer.resendActiveCode_buyer(params.token);
    }

    public static final class Params {

        final String token;

        private Params(String token) {
            this.token = token;
        }

        public static ResendActiveCode_Buyer.Params forResendActiveCode(String token) {
            return new ResendActiveCode_Buyer.Params(token);
        }
    }
}
