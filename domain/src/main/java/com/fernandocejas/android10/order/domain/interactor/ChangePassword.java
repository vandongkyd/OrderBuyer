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
 * change password.
 */

public class ChangePassword extends UseCase<User, ChangePassword.Params> {
    private final UserRepository userRepository;

    @Inject
    ChangePassword(UserRepository userRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<User> buildUseCaseObservable(ChangePassword.Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.changePassword(params.token, params.old_password, params.password);
    }

    public static final class Params {

        private final String token;
        private final String old_password;
        private final String password;


        private Params(String token, String old_password, String password) {
            this.token = token;
            this.old_password = old_password;
            this.password = password;
        }

        public static ChangePassword.Params forChangePassword(String token, String old_password, String password) {
            return new ChangePassword.Params(token, old_password, password);
        }
    }
}
