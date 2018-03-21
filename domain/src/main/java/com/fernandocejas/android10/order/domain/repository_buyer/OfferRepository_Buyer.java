package com.fernandocejas.android10.order.domain.repository_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Offer_Buyer;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/16/18.
 */

public interface OfferRepository_Buyer {
    /**
     * Get an {@link Observable} which will emit a List of {@link Offer_Buyer}.
     */
    Observable<List<Offer_Buyer>> myoffer_buyer(String token);
    Observable<Offer_Buyer> make_offer (String token,
                                        String orderid,
                                        String deviverdate,
                                        String providerfee,
                                        String shipfee,
                                        String surchargefee,
                                        String otherfee,
                                        String description);

    Observable<Offer_Buyer> company (String token, String email);
}
