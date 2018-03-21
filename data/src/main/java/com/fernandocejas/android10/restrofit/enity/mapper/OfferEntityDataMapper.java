/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.restrofit.enity.mapper;

import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.restrofit.enity.OfferEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link OfferEntity} (in the data layer) to {@link Offer} in the
 * domain layer.
 */
@Singleton
public class OfferEntityDataMapper {

    private final AddressEntityDataMapper addressEntityDataMapper;
    private final RateEntityDataMapper rateEntityDataMapper;

    @Inject
    OfferEntityDataMapper(AddressEntityDataMapper addressEntityDataMapper,
                          RateEntityDataMapper rateEntityDataMapper) {
        this.addressEntityDataMapper = addressEntityDataMapper;
        this.rateEntityDataMapper = rateEntityDataMapper;
    }

    /**
     * Transform a {@link OfferEntity} into an {@link Offer}.
     *
     * @param offerEntity Object to be transformed.
     * @return {@link Offer} if valid {@link OfferEntity} otherwise null.
     */
    public Offer transform(OfferEntity offerEntity) {
        Offer offer = null;
        if (offerEntity != null) {
            offer = new Offer();
            offer.setProviderid(offerEntity.getProviderid());
            offer.setOrderid(offerEntity.getOrderid());
            offer.setDescription(offerEntity.getDescription());
            offer.setDeviverdate(offerEntity.getDeviverdate());
            offer.setProviderfee(offerEntity.getProviderfee());
            offer.setShipfee(offerEntity.getShipfee());
            offer.setSurchargefee(offerEntity.getSurchargefee());
            offer.setOtherfee(offerEntity.getOtherfee());
            offer.setCreated_at(offerEntity.getCreated_at());
            offer.setName(offerEntity.getName());
            offer.setLogo(offerEntity.getLogo());
            offer.setWebsite(offerEntity.getWebsite());
            offer.setPhone(offerEntity.getPhone());
            offer.setEmail(offerEntity.getEmail());
            if (offerEntity.getRate() != null) {
                offer.setRate(rateEntityDataMapper.transform(offerEntity.getRate()));
            }
            offer.setAddress(addressEntityDataMapper.transform(offerEntity.getAddress()));
        }
        return offer;
    }

    /**
     * Transform a List of {@link OfferEntity} into a Collection of {@link Offer}.
     *
     * @param offerEntityCollection Object Collection to be transformed.
     * @return {@link Offer} if valid {@link OfferEntity} otherwise null.
     */
    public List<Offer> transform(Collection<OfferEntity> offerEntityCollection) {
        final List<Offer> offerList = new ArrayList<>();
        for (OfferEntity offerEntity : offerEntityCollection) {
            final Offer Offer = transform(offerEntity);
            if (Offer != null) {
                offerList.add(Offer);
            }
        }
        return offerList;
    }

    public OfferEntity transform(final Offer offer) {
        OfferEntity offerEntity = null;
        if (offer != null) {
            offerEntity = new OfferEntity();
            offerEntity.setProviderid(offer.getProviderid());
            offerEntity.setOrderid(offer.getOrderid());
            offerEntity.setDescription(offer.getDescription());
            offerEntity.setDeviverdate(offer.getDeviverdate());
            offerEntity.setProviderfee(offer.getProviderfee());
            offerEntity.setShipfee(offer.getShipfee());
            offerEntity.setSurchargefee(offer.getSurchargefee());
            offerEntity.setOtherfee(offer.getOtherfee());
            offerEntity.setCreated_at(offer.getCreated_at());
            offerEntity.setName(offer.getName());
            offerEntity.setLogo(offer.getLogo());
            offerEntity.setWebsite(offer.getWebsite());
            offerEntity.setPhone(offer.getPhone());
            offerEntity.setEmail(offer.getEmail());
            if(offer.getRate()!= null){
                offerEntity.setRate(rateEntityDataMapper.transform(offer.getRate()));
            }
            offerEntity.setAddress(addressEntityDataMapper.transform(offer.getAddress()));
        }
        return offerEntity;
    }

    public List<OfferEntity> transformEntity(Collection<Offer> offerCollection) {
        List<OfferEntity> offerEntityList = null;
        if (offerCollection != null && !offerCollection.isEmpty()) {
            offerEntityList = new ArrayList<>();
            for (Offer offer : offerCollection) {
                final OfferEntity offerEntity = transform(offer);
                if (offerEntity != null) {
                    offerEntityList.add(offerEntity);
                }
            }
        }
        return offerEntityList;
    }
}
