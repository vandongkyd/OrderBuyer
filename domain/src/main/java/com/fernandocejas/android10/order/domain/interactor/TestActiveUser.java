package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.TestUser;
import com.fernandocejas.android10.order.domain.User;
import com.fernandocejas.android10.order.domain.repository.TestUserRepository;
import com.fernandocejas.android10.order.domain.repository.UserRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.TestUserCase;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link TestUserCase} that represents a use case for
 * active user.
 */
public class TestActiveUser extends TestUserCase<TestUser, TestActiveUser.Params> {

    private final TestUserRepository userRepository;

    @Inject
    TestActiveUser(TestUserRepository userRepository, ThreadExecutor threadExecutor,
               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<TestUser> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.active(params.token,
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

        public static TestActiveUser.Params forActiveUser(String token, String active_code) {
            return new TestActiveUser.Params(token, active_code);
        }
    }
}
