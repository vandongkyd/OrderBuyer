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

import com.fernandocejas.android10.order.domain.Order;
import com.fernandocejas.android10.restrofit.enity.OrderEntity;
import com.fernandocejas.android10.restrofit.enity.OrderEntityResponse;
import com.fernandocejas.android10.restrofit.enity.OrderListEntityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link OrderEntity} (in the data layer) to {@link Order} in the
 * domain layer.
 */
@Singleton
public class OrderEntityDataMapper {

    private final AddressEntityDataMapper addressEntityDataMapper;
    private final ProductEntityDataMapper productEntityDataMapper;
    private final OfferEntityDataMapper offerEntityDataMapper;
    private final CountryEntityDataMapper countryEntityDataMapper;

    @Inject
    OrderEntityDataMapper(AddressEntityDataMapper addressEntityDataMapper,
                          ProductEntityDataMapper productEntityDataMapper,
                          OfferEntityDataMapper offerEntityDataMapper,
                          CountryEntityDataMapper countryEntityDataMapper) {
        this.addressEntityDataMapper = addressEntityDataMapper;
        this.productEntityDataMapper = productEntityDataMapper;
        this.offerEntityDataMapper = offerEntityDataMapper;
        this.countryEntityDataMapper = countryEntityDataMapper;
    }

    /**
     * Transform a {@link OrderEntity} into an {@link Order}.
     *
     * @param orderEntity Object to be transformed.
     * @return {@link Order} if valid {@link OrderEntity} otherwise null.
     */
    public Order transform(OrderEntity orderEntity) {
        Order order = null;
        if (orderEntity != null) {
            order = new Order();
            order.setId(orderEntity.getId());
            order.setUserid(orderEntity.getUserid());
            order.setProviderid(orderEntity.getProviderid());
            order.setAmount(orderEntity.getAmount());
            order.setQuantity(orderEntity.getQuantity());
            order.setWeight(orderEntity.getWeight());
            order.setTax(orderEntity.getTax());
            order.setServicefee(orderEntity.getServicefee());
            order.setDeviverdate(orderEntity.getDeviverdate());
            if (orderEntity.getDeliverto() != null) {
                order.setAddress(addressEntityDataMapper.transform(orderEntity.getDeliverto()));
            }
            order.setIos(orderEntity.getIos());
            order.setCountry_name(orderEntity.getCountry_name());
            order.setCurrency(orderEntity.getCurrency());
            order.setStatus(orderEntity.getStatus());
            order.setCreated_at(orderEntity.getCreated_at());
            if (orderEntity.getProducts() != null && !orderEntity.getProducts().isEmpty()) {
                order.setProducts(productEntityDataMapper.transform(orderEntity.getProducts()));
            }
            if (orderEntity.getOffers() != null && !orderEntity.getOffers().isEmpty()) {
                order.setOffers(offerEntityDataMapper.transform(orderEntity.getOffers()));
            }
            order.setCountry(countryEntityDataMapper.transform(orderEntity.getCountry()));
            order.setDescription(orderEntity.getDescription());
        }
        return order;
    }

    /**
     * Transform a List of {@link OrderEntity} into a Collection of {@link Order}.
     *
     * @param orderEntityCollection Object Collection to be transformed.
     * @return {@link Order} if valid {@link OrderEntity} otherwise null.
     */
    public List<Order> transform(Collection<OrderEntity> orderEntityCollection) {
        final List<Order> orderList = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityCollection) {
            final Order order = transform(orderEntity);
            if (order != null) {
                orderList.add(order);
            }
        }
        return orderList;
    }


    public Order transform(OrderEntityResponse orderEntityResponse) throws Exception {
        Order order = null;
        if (orderEntityResponse != null) {
            if (orderEntityResponse.status() == false) {
                throw new Exception(orderEntityResponse.message());
            }
            order = transform(orderEntityResponse.data());
        }
        return order;
    }

    /**
     * Transform a List of {@link OrderListEntityResponse} into a Collection of {@link Order}.
     *
     * @param orderListEntityResponse Object Collection to be transformed.
     * @return {@link Order} if valid {@link OrderListEntityResponse} otherwise null.
     */
    public List<Order> transform(OrderListEntityResponse orderListEntityResponse) throws Exception {
        List<Order> orderList = null;
        if (orderListEntityResponse != null) {
            if (orderListEntityResponse.status() == false) {
                throw new Exception(orderListEntityResponse.message());
            }
            orderList = transform(orderListEntityResponse.data());
        }
        return orderList;
    }

    public OrderEntity transform(Order order) {
        OrderEntity orderEntity = null;
        if (order != null) {
            orderEntity = new OrderEntity();
            orderEntity.setId(order.getId());
            orderEntity.setUserid(order.getUserid());
            orderEntity.setProviderid(order.getProviderid());
            orderEntity.setAmount(order.getAmount());
            orderEntity.setQuantity(order.getQuantity());
            orderEntity.setWeight(order.getWeight());
            orderEntity.setTax(order.getTax());
            orderEntity.setServicefee(order.getServicefee());
            orderEntity.setDeviverdate(order.getDeviverdate());
            orderEntity.setDeliverto(addressEntityDataMapper.transform(order.getAddress()));
            orderEntity.setIos(order.getIos());
            orderEntity.setCountry_name(order.getCountry_name());
            orderEntity.setCurrency(order.getCurrency());
            orderEntity.setStatus(order.getStatus());
            orderEntity.setCreated_at(order.getCreated_at());
            orderEntity.setProducts(productEntityDataMapper.transformToEntity(order.getProducts()));
            orderEntity.setOffers(offerEntityDataMapper.transformEntity(order.getOffers()));
            orderEntity.setCountry(countryEntityDataMapper.transform(order.getCountry()));
            orderEntity.setDescription(order.getDescription());
        }
        return orderEntity;
    }
}
