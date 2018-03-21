package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.domain.repository.ProductRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 */

public class ParseProductFromUrl extends UseCase<Product, ParseProductFromUrl.Params> {

    private final ProductRepository productRepository;

    @Inject
    ParseProductFromUrl(ProductRepository productRepository, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.productRepository = productRepository;
    }

    @Override
    protected Observable<Product> buildUseCaseObservable(ParseProductFromUrl.Params params) {
        Preconditions.checkNotNull(params);
        return this.productRepository.parse(params.token, params.url);
    }

    public static final class Params {

        private final String token;

        private final String url;

        private Params(String token, String url) {
            this.token = token;
            this.url = url;
        }

        public static ParseProductFromUrl.Params forParseUrl(String token, String url) {
            return new ParseProductFromUrl.Params(token, url);
        }
    }
}

