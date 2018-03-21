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
 * Created by vandongluong on 3/6/18.
 */

public class TestSiginIn extends TestUserCase<TestUser, TestSiginIn.Params> {

    private final TestUserRepository userRepository;

    @Inject
    TestSiginIn(TestUserRepository userRepository, ThreadExecutor threadExecutor,
           PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<TestUser> buildUseCaseObservable(TestSiginIn.Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.signIn(params.email, params.password);
    }

    public static final class Params {

        private final String email;

        private final String password;

        private Params(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public static TestSiginIn.Params forSignIn(String email, String password) {
            return new TestSiginIn.Params(email, password);
        }
    }
}
