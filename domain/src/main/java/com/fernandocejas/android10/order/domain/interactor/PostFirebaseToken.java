package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.MetaData;
import com.fernandocejas.android10.order.domain.repository.MetaDataRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PostFirebaseToken extends UseCase<MetaData, PostFirebaseToken.Params> {

    private final MetaDataRepository metaDataRepository;

    @Inject
    PostFirebaseToken(MetaDataRepository metaDataRepository, ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.metaDataRepository = metaDataRepository;
    }

    @Override
    protected Observable<MetaData> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.metaDataRepository.postFirebaseToken(params.token, params.firebase_token);
    }

    public static final class Params {

        private final String token;
        private final String firebase_token;

        private Params(String token, String firebase_token) {
            this.token = token;
            this.firebase_token = firebase_token;
        }

        public static Params forPostToken(String token, String firebase_token) {
            return new Params(token, firebase_token);
        }
    }
}
