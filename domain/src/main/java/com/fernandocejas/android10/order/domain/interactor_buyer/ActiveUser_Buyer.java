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

public class ActiveUser_Buyer extends UseCase<User_Buyer, ActiveUser_Buyer.Params> {

    private final UserRepository_Buyer userRepository_buyer;

    @Inject
    ActiveUser_Buyer(UserRepository_Buyer userRepository_buyer, ThreadExecutor threadExecutor,
               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository_buyer = userRepository_buyer;
    }

    @Override
    protected Observable<User_Buyer> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository_buyer.active_buyer(params.token,
                params.active_code);
    }

    public static final class Params {

        final String token;
        final String active_code;

        private Params(String token,
                       String active_code) {
            this.token = token;
            this.active_code = active_code;
        }

        public static ActiveUser_Buyer.Params forActiveUser(String token, String active_code) {
            return new ActiveUser_Buyer.Params(token, active_code);
        }
    }
}
