package com.fernandocejas.android10.order.domain.interactor_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Offer_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.OfferRepository_Buyer;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/20/18.
 */

public class GetCompany extends UseCase<Offer_Buyer, GetCompany.Params> {
    private final OfferRepository_Buyer offerRepository_buyer;

    @Inject
    GetCompany(OfferRepository_Buyer offerRepository_buyer, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.offerRepository_buyer = offerRepository_buyer;
    }

    @Override
    protected Observable<Offer_Buyer> buildUseCaseObservable(GetCompany.Params params) {
        return this.offerRepository_buyer.company(params.access_token,
                params.email);
    }
    public static final class Params {

        private final String access_token;
        private final String email;

        private Params(String access_token,String email) {
            this.access_token = access_token;
            this.email = email;
        }

        public static GetCompany.Params forCompony(String access_token, String email) {
            return new GetCompany.Params(access_token, email);
        }
    }
}
