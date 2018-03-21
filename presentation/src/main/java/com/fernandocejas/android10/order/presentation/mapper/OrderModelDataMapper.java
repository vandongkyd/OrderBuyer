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

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Order} (in the domain layer) to {@link OrderModel} in the
 * presentation layer.
 */
@PerActivity
public class OrderModelDataMapper {

    private final AddressModelDataMapper addressModelDataMapper;
    private final ProductModelDataMapper productModelDataMapper;
    private final OfferModelDataMapper offerModelDataMapper;
    private final CountryModelDataMapper countryModelDataMapper;

    @Inject
    public OrderModelDataMapper(AddressModelDataMapper addressModelDataMapper,
                                ProductModelDataMapper productModelDataMapper,
                                OfferModelDataMapper offerModelDataMapper,
                                CountryModelDataMapper countryModelDataMapper) {
        this.addressModelDataMapper = addressModelDataMapper;
        this.productModelDataMapper = productModelDataMapper;
        this.offerModelDataMapper = offerModelDataMapper;
        this.countryModelDataMapper = countryModelDataMapper;
    }

    /**
     * Transform a {@link Order} into an {@link OrderModel}.
     *
     * @param order Object to be transformed.
     * @return {@link OrderModel}.
     */
    public OrderModel transform(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final OrderModel orderModel = new OrderModel();
        orderModel.setId(order.getId());
        orderModel.setUserid(order.getUserid());
        orderModel.setProviderid(order.getProviderid());
        orderModel.setAmount(order.getAmount());
        orderModel.setQuantity(order.getQuantity());
        orderModel.setWeight(order.getWeight());
        orderModel.setTax(order.getTax());
        orderModel.setServicefee(order.getServicefee());
        orderModel.setDeviverdate(order.getDeviverdate());
        if(order.getAddress()!= null){
            orderModel.setAddressModel(addressModelDataMapper.transform(order.getAddress()));
        }
        orderModel.setIos(order.getIos());
        orderModel.setCountry_name(order.getCountry_name());
        orderModel.setCurrency(order.getCurrency());
        orderModel.setStatus(order.getStatus());
        orderModel.setCreated_at(order.getCreated_at());
        orderModel.setProducts(productModelDataMapper.transform(order.getProducts()));
        orderModel.setOffers(offerModelDataMapper.transform(order.getOffers()));
        if(order.getCountry()!= null){
            orderModel.setCountry(countryModelDataMapper.transform(order.getCountry()));
        }
        orderModel.setDescription(order.getDescription());
        return orderModel;
    }

    /**
     * Transform a Collection of {@link Order} into a Collection of {@link OrderModel}.
     *
     * @param ordersCollection Objects to be transformed.
     * @return List of {@link OrderModel}.
     */
    public Collection<OrderModel> transform(Collection<Order> ordersCollection) {
        Collection<OrderModel> orderModelsCollection;

        if (ordersCollection != null && !ordersCollection.isEmpty()) {
            orderModelsCollection = new ArrayList<>();
            for (Order order : ordersCollection) {
                orderModelsCollection.add(transform(order));
            }
        } else {
            orderModelsCollection = Collections.emptyList();
        }

        return orderModelsCollection;
    }

}
