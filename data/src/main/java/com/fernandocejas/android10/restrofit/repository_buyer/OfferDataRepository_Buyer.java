package com.fernandocejas.android10.restrofit.repository_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Offer_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.OfferRepository_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer.OfferEntityDataMapper_Buyer;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper_Buyer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/16/18.
 */
@Singleton
public class OfferDataRepository_Buyer implements OfferRepository_Buyer {

    private final RetrofitHelper_Buyer retrofitHelper;
    private final OfferEntityDataMapper_Buyer offerEntityDataMapper_buyer;

    @Inject
    OfferDataRepository_Buyer(RetrofitHelper_Buyer retrofitHelper,
                                     OfferEntityDataMapper_Buyer offerEntityDataMapper_buyer) {
        this.retrofitHelper = retrofitHelper;
        this.offerEntityDataMapper_buyer = offerEntityDataMapper_buyer;
    }

    @Override
    public Observable<List<Offer_Buyer>> myoffer_buyer(String token) {
        return retrofitHelper
                .getRestApiService()
                .my_offers_offset("Bearer " + token)
                .map(this.offerEntityDataMapper_buyer::transform);
    }

    @Override
    public Observable<Offer_Buyer> make_offer(String token,
                                              String orderid,
                                              String deviverdate,
                                              String providerfee,
                                              String shipfee,
                                              String surchargefee,
                                              String otherfee, String description) {
        return retrofitHelper
                .getRestApiService()
                .make_offer("Bearer " + token,
                        orderid,
                        deviverdate,
                        providerfee,
                        shipfee,
                        surchargefee,
                        otherfee,
                        description)
                .map(this.offerEntityDataMapper_buyer::transform);
    }

    @Override
    public Observable<Offer_Buyer> company(String token, String email) {
        return retrofitHelper
                .getRestApiService()
                .company("Bearer" + token, email)
                .map(this.offerEntityDataMapper_buyer::transform);
    }
}
