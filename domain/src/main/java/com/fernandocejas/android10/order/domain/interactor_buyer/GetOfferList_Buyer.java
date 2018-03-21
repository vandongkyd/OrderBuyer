package com.fernandocejas.android10.order.domain.interactor_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Offer_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.OfferRepository_Buyer;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/16/18.
 */

public class GetOfferList_Buyer extends UseCase<List<Offer_Buyer>, GetOfferList_Buyer.Params> {

    private final OfferRepository_Buyer offerRepository_buyer;

    @Inject
    GetOfferList_Buyer(OfferRepository_Buyer offerRepository_buyer, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.offerRepository_buyer = offerRepository_buyer;
    }

    @Override
    protected Observable<List<Offer_Buyer>> buildUseCaseObservable(GetOfferList_Buyer.Params params) {
        Preconditions.checkNotNull(params);
        return this.offerRepository_buyer.myoffer_buyer(params.access_token);
    }

    public static final class Params {

        private final String access_token;

        private Params(String access_token) {
            this.access_token = access_token;
        }

        public static GetOfferList_Buyer.Params forOffer(String access_token) {
            return new GetOfferList_Buyer.Params(access_token);
        }
    }
}
