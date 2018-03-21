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
package com.fernandocejas.android10.order.presentation.mapper;

import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Offer} (in the domain layer) to {@link OfferModel} in the
 * presentation layer.
 */
@PerActivity
public class OfferModelDataMapper {

    private final AddressModelDataMapper addressModelDataMapper;
    private final RateModelDataMapper rateModelDataMapper;

    @Inject
    public OfferModelDataMapper(AddressModelDataMapper addressModelDataMapper, RateModelDataMapper rateModelDataMapper) {
        this.addressModelDataMapper = addressModelDataMapper;
        this.rateModelDataMapper = rateModelDataMapper;
    }

    /**
     * Transform a {@link Offer} into an {@link OfferModel}.
     *
     * @param offer Object to be transformed.
     * @return {@link OfferModel}.
     */
    public OfferModel transform(Offer offer) {
        if (offer == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final OfferModel offerModel = new OfferModel();
        offerModel.setProviderid(offer.getProviderid());
        offerModel.setOrderid(offer.getOrderid());
        offerModel.setDescription(offer.getDescription());
        offerModel.setDeviverdate(offer.getDeviverdate());
        offerModel.setProviderfee(offer.getProviderfee());
        offerModel.setShipfee(offer.getShipfee());
        offerModel.setSurchargefee(offer.getSurchargefee());
        offerModel.setOtherfee(offer.getOtherfee());
        offerModel.setCreated_at(offer.getCreated_at());
        offerModel.setLogo(offer.getLogo());
        offerModel.setWebsite(offer.getWebsite());
        offerModel.setPhone(offer.getPhone());
        offerModel.setEmail(offer.getEmail());
        offerModel.setName(offer.getName());
        if(offer.getRate()!= null){
            offerModel.setRate(rateModelDataMapper.transform(offer.getRate()));
        }
        if(offer.getAddress()!= null){
            offerModel.setAddress(addressModelDataMapper.transform(offer.getAddress()));
        }
        return offerModel;
    }

    /**
     * Transform a Collection of {@link Offer} into a Collection of {@link OfferModel}.
     *
     * @param offersCollection Objects to be transformed.
     * @return List of {@link OfferModel}.
     */
    public Collection<OfferModel> transform(Collection<Offer> offersCollection) {
        Collection<OfferModel> offerModelsCollection;

        if (offersCollection != null && !offersCollection.isEmpty()) {
            offerModelsCollection = new ArrayList<>();
            for (Offer Offer : offersCollection) {
                offerModelsCollection.add(transform(Offer));
            }
        } else {
            offerModelsCollection = Collections.emptyList();
        }

        return offerModelsCollection;
    }

    public Offer transform(OfferModel offerModel) {
        if (offerModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Offer offer = new Offer();
        offer.setProviderid(offerModel.getProviderid());
        offer.setOrderid(offerModel.getOrderid());
        offer.setDescription(offerModel.getDescription());
        offer.setDeviverdate(offerModel.getDeviverdate());
        offer.setProviderfee(offerModel.getProviderfee());
        offer.setShipfee(offerModel.getShipfee());
        offer.setSurchargefee(offerModel.getSurchargefee());
        offer.setOtherfee(offerModel.getOtherfee());
        offer.setCreated_at(offerModel.getCreated_at());
        offer.setLogo(offerModel.getLogo());
        offer.setWebsite(offerModel.getWebsite());
        offer.setPhone(offerModel.getPhone());
        offer.setEmail(offerModel.getEmail());
        offer.setName(offerModel.getName());
        if(offerModel.getRate()!= null){
            offer.setRate(rateModelDataMapper.transform(offerModel.getRate()));
        }
        if(offerModel.getAddress()!= null){
            offer.setAddress(addressModelDataMapper.transform(offerModel.getAddress()));
        }
        return offer;
    }
}

