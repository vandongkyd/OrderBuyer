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
 * retrieve user information.
 */

public class RetrieveUserInformation extends UseCase<User, RetrieveUserInformation.Params> {
    private final UserRepository userRepository;

    @Inject
    RetrieveUserInformation(UserRepository userRepository, ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<User> buildUseCaseObservable(RetrieveUserInformation.Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.retrieveUserInfo(params.token);
    }

    public static final class Params {

        private final String token;

        private Params(String token) {
            this.token = token;
        }

        public static RetrieveUserInformation.Params forRetrieveUserInformation(String token) {
            return new RetrieveUserInformation.Params(token);
        }
    }
}
