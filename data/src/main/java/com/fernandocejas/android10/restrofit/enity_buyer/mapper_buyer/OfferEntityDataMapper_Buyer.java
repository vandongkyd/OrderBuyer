package com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer;


import com.fernandocejas.android10.order.domain.Model_buyer.Offer_Buyer;
import com.fernandocejas.android10.order.domain.Model_buyer.Order_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.OfferRepository_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.OfferEntityResponse_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.OfferEntity_Buyer;
import com.fernandocejas.android10.restrofit.enity.mapper.AddressEntityDataMapper;
import com.fernandocejas.android10.restrofit.enity.mapper.RateEntityDataMapper;
import com.fernandocejas.android10.restrofit.enity_buyer.OfferListEntityResponse_Buyer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by vandongluong on 3/14/18.
 */
@Singleton
public class OfferEntityDataMapper_Buyer {

    private final AddressEntityDataMapper_Buyer addressEntityDataMapper_buyer;
    private final RateEntityDataMapper rateEntityDataMapper;

    @Inject
    OfferEntityDataMapper_Buyer(AddressEntityDataMapper_Buyer addressEntityDataMapper_buyer,
                          RateEntityDataMapper rateEntityDataMapper) {
        this.addressEntityDataMapper_buyer = addressEntityDataMapper_buyer;
        this.rateEntityDataMapper = rateEntityDataMapper;
    }

    /**
     * Transform a {@link OfferEntity_Buyer} into an {@link Offer_Buyer}.
     *
     * @param offerEntity_buyer Object to be transformed.
     * @return {@link Offer_Buyer} if valid {@link OfferEntity_Buyer} otherwise null.
     */
    public Offer_Buyer transform(OfferEntity_Buyer offerEntity_buyer) {
        Offer_Buyer offer_buyer = null;
        if (offerEntity_buyer != null) {
            offer_buyer = new Offer_Buyer();
            offer_buyer.setProviderid(offerEntity_buyer.getProviderid());
            offer_buyer.setOrderid(offerEntity_buyer.getOrderid());
            offer_buyer.setDescription(offerEntity_buyer.getDescription());
            offer_buyer.setDeviverdate(offerEntity_buyer.getDeviverdate());
            offer_buyer.setProviderfee(offerEntity_buyer.getProviderfee());
            offer_buyer.setShipfee(offerEntity_buyer.getShipfee());
            offer_buyer.setSurchargefee(offerEntity_buyer.getSurchargefee());
            offer_buyer.setOtherfee(offerEntity_buyer.getOtherfee());
            offer_buyer.setCreated_at(offerEntity_buyer.getCreated_at());
            offer_buyer.setName(offerEntity_buyer.getName());
            offer_buyer.setLogo(offerEntity_buyer.getLogo());
            offer_buyer.setWebsite(offerEntity_buyer.getWebsite());
            offer_buyer.setPhone(offerEntity_buyer.getPhone());
            offer_buyer.setEmail(offerEntity_buyer.getEmail());
            if (offerEntity_buyer.getRate() != null) {
                offer_buyer.setRate(rateEntityDataMapper.transform(offerEntity_buyer.getRate()));
            }
            offer_buyer.setAddress(addressEntityDataMapper_buyer.transform(offerEntity_buyer.getAddress()));
        }
        return offer_buyer;
    }

    /**
     * Transform a List of {@link OfferEntity_Buyer} into a Collection of {@link Offer_Buyer}.
     *
     * @param offerEntityCollection_buyer Object Collection to be transformed.
     * @return {@link Offer_Buyer} if valid {@link OfferEntity_Buyer} otherwise null.
     */
    public List<Offer_Buyer> transform(Collection<OfferEntity_Buyer> offerEntityCollection_buyer) {
        final List<Offer_Buyer> offerList = new ArrayList<>();
        for (OfferEntity_Buyer offerEntity_buyer : offerEntityCollection_buyer) {
            final Offer_Buyer Offer = transform(offerEntity_buyer);
            if (Offer != null) {
                offerList.add(Offer);
            }
        }
        return offerList;
    }



    public Offer_Buyer transform(OfferEntityResponse_Buyer offerEntityResponse_buyer) throws Exception {
        Offer_Buyer offerBuyer = null;
        if (offerEntityResponse_buyer != null) {
            if (offerEntityResponse_buyer.status() == false) {
                throw new Exception(offerEntityResponse_buyer.message());
            }
            offerBuyer = transform(offerEntityResponse_buyer.data());
        }
        return offerBuyer;
    }

    /**
     * Transform a List of {@link OfferEntityResponse_Buyer} into a Collection of {@link Offer_Buyer}.
     *
     * @param offerListEntityResponse_buyer Object Collection to be transformed.
     * @return {@link Offer_Buyer} if valid {@link OfferEntityResponse_Buyer} otherwise null.
     */
    public List<Offer_Buyer> transform(OfferListEntityResponse_Buyer offerListEntityResponse_buyer) throws Exception {
        List<Offer_Buyer> offerList = null;
        if (offerListEntityResponse_buyer != null) {
            if (offerListEntityResponse_buyer.status() == false) {
                throw new Exception(offerListEntityResponse_buyer.message());
            }
            offerList = transform(offerListEntityResponse_buyer.data());
        }
        return offerList;
    }



    public OfferEntity_Buyer transform(final Offer_Buyer offer_buyer) {
        OfferEntity_Buyer offerEntity_buyer = null;
        if (offer_buyer != null) {
            offerEntity_buyer = new OfferEntity_Buyer();
            offerEntity_buyer.setProviderid(offer_buyer.getProviderid());
            offerEntity_buyer.setOrderid(offer_buyer.getOrderid());
            offerEntity_buyer.setDescription(offer_buyer.getDescription());
            offerEntity_buyer.setDeviverdate(offer_buyer.getDeviverdate());
            offerEntity_buyer.setProviderfee(offer_buyer.getProviderfee());
            offerEntity_buyer.setShipfee(offer_buyer.getShipfee());
            offerEntity_buyer.setSurchargefee(offer_buyer.getSurchargefee());
            offerEntity_buyer.setOtherfee(offer_buyer.getOtherfee());
            offerEntity_buyer.setCreated_at(offer_buyer.getCreated_at());
            offerEntity_buyer.setName(offer_buyer.getName());
            offerEntity_buyer.setLogo(offer_buyer.getLogo());
            offerEntity_buyer.setWebsite(offer_buyer.getWebsite());
            offerEntity_buyer.setPhone(offer_buyer.getPhone());
            offerEntity_buyer.setEmail(offer_buyer.getEmail());
            if(offer_buyer.getRate()!= null){
                offerEntity_buyer.setRate(rateEntityDataMapper.transform(offer_buyer.getRate()));
            }
            offerEntity_buyer.setAddress(addressEntityDataMapper_buyer.transform(offer_buyer.getAddress()));
        }
        return offerEntity_buyer;
    }

    public List<OfferEntity_Buyer> transformEntity(Collection<Offer_Buyer> offerCollection_buyer) {
        List<OfferEntity_Buyer> offerEntityList_buyer = null;
        if (offerCollection_buyer != null && !offerCollection_buyer.isEmpty()) {
            offerEntityList_buyer = new ArrayList<>();
            for (Offer_Buyer offer : offerCollection_buyer) {
                final OfferEntity_Buyer offerEntity = transform(offer);
                if (offerEntity != null) {
                    offerEntityList_buyer.add(offerEntity);
                }
            }
        }
        return offerEntityList_buyer;
    }

}

