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
 * Created by vandongluong on 3/20/18.
 */

public class GetMakeOffer_Buyer extends UseCase<Offer_Buyer, GetMakeOffer_Buyer.Params> {

    private final OfferRepository_Buyer offerRepository_buyer;

    @Inject
    GetMakeOffer_Buyer(OfferRepository_Buyer offerRepository_buyer, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.offerRepository_buyer = offerRepository_buyer;
    }

    @Override
    protected Observable<Offer_Buyer> buildUseCaseObservable(GetMakeOffer_Buyer.Params params) {
        Preconditions.checkNotNull(params);
        return this.offerRepository_buyer.make_offer(params.access_token,
                params.orderid,
                params.deviverdate,
                params.providerfee, params.shipfee ,params.surchargefee, params.otherfee, params.description);
    }

    public static final class Params {

        private final String access_token;
        private final String orderid;
        private final String deviverdate;
        private final String providerfee;
        private final String shipfee;
        private final String surchargefee;
        private final String otherfee;
        private final String description;

        private Params(String access_token,
                       String orderid,
                       String deviverdate,
                       String providerfee,
                       String shipfee,
                       String surchargefee,
                       String otherfee,
                       String description) {
            this.access_token = access_token;
            this.orderid = orderid;
            this.deviverdate = deviverdate;
            this.providerfee = providerfee;
            this.shipfee = shipfee;
            this.surchargefee = surchargefee;
            this.otherfee = otherfee;
            this.description = description;
        }

        public static GetMakeOffer_Buyer.Params forMake(String access_token,
                                                         String orderid,
                                                         String deviverdate,
                                                         String providerfee,
                                                         String shipfee,
                                                         String surchargefee,
                                                         String otherfee,
                                                         String description) {
            return new GetMakeOffer_Buyer.Params(access_token, orderid , deviverdate ,providerfee ,shipfee , surchargefee ,otherfee ,description);
        }
    }
}
