package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Token;
import com.fernandocejas.android10.order.domain.repository.StripeRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by jerry on Jan-16-18.
 */

public class GetTokenFromStripe extends UseCase<Token, GetTokenFromStripe.Params> {

    private final StripeRepository stripeRepository;

    @Inject
    GetTokenFromStripe(StripeRepository stripeRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.stripeRepository = stripeRepository;
    }

    @Override
    protected Observable<Token> buildUseCaseObservable(GetTokenFromStripe.Params params) {
        Preconditions.checkNotNull(params);
        return this.stripeRepository.createToken(
                params.card_number,
                params.exp_month,
                params.exp_year,
                params.cvc
        );
    }

    public static final class Params {

        private String card_number;
        private int exp_month;
        private int exp_year;
        private String cvc;

        private Params(String card_number, int exp_month, int exp_year, String cvc) {
            this.card_number = card_number;
            this.exp_month = exp_month;
            this.exp_year = exp_year;
            this.cvc = cvc;
        }

        public static GetTokenFromStripe.Params forGetToken(String card_number, int exp_month, int exp_year, String cvc) {
            return new GetTokenFromStripe.Params(card_number, exp_month, exp_year, cvc);
        }
    }
}
