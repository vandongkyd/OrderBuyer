package com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer;




import com.fernandocejas.android10.order.domain.Model_buyer.Order_Buyer;
import com.fernandocejas.android10.restrofit.enity.mapper.CountryEntityDataMapper;
import com.fernandocejas.android10.restrofit.enity_buyer.OrderEnity_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.OrderEntityResponse_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.OrderListEntityResponse_Buyer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by vandongluong on 3/15/18.
 */
@Singleton
public class OrderEntityDataMapper_Buyer {
    private final AddressEntityDataMapper_Buyer addressEntityDataMapper_buyer;
    private final ProductEntityDataMapper_Buyer productEntityDataMapper_buyer;
    private final OfferEntityDataMapper_Buyer offerEntityDataMapper_buyer;
    private final CountryEntityDataMapper countryEntityDataMapper;
    @Inject
    OrderEntityDataMapper_Buyer(AddressEntityDataMapper_Buyer addressEntityDataMapper_buyer,
                                ProductEntityDataMapper_Buyer productEntityDataMapper_buyer,
                                OfferEntityDataMapper_Buyer offerEntityDataMapper_buyer,
                                CountryEntityDataMapper countryEntityDataMapper){
        this.addressEntityDataMapper_buyer = addressEntityDataMapper_buyer;
        this.productEntityDataMapper_buyer = productEntityDataMapper_buyer;
        this.offerEntityDataMapper_buyer = offerEntityDataMapper_buyer;
        this.countryEntityDataMapper = countryEntityDataMapper;
    }


    /**
     * Transform a {@link OrderEnity_Buyer} into an {@link Order_Buyer}.
     *
     * @param orderEntity_buyer Object to be transformed.
     * @return {@link Order_Buyer} if valid {@link OrderEnity_Buyer} otherwise null.
     */
    public Order_Buyer transform(OrderEnity_Buyer orderEntity_buyer) {
        Order_Buyer order_buyer = null;
        if (orderEntity_buyer != null) {
            order_buyer = new Order_Buyer();
            order_buyer.setId(orderEntity_buyer.getId());
            order_buyer.setUserid(orderEntity_buyer.getUserid());
            order_buyer.setProviderid(orderEntity_buyer.getProviderid());
            order_buyer.setAmount(orderEntity_buyer.getAmount());
            order_buyer.setQuantity(orderEntity_buyer.getQuantity());
            order_buyer.setWeitght(orderEntity_buyer.getWeitght());
            order_buyer.setTax_percent(orderEntity_buyer.getTax_percent());
            order_buyer.setTax_amount(orderEntity_buyer.getTax_amount());
            order_buyer.setDeviverdate(orderEntity_buyer.getDeviverdate());
            if (orderEntity_buyer.getDeliverto() != null) {
                order_buyer.setAddress_buyer(addressEntityDataMapper_buyer.transform(orderEntity_buyer.getDeliverto()));
            }
            order_buyer.setIos(orderEntity_buyer.getIos());
            order_buyer.setCountry_name(orderEntity_buyer.getCountry_name());
            order_buyer.setCurrency(orderEntity_buyer.getCurrency());
            order_buyer.setDescription(orderEntity_buyer.getDescription());
            order_buyer.setStatus(orderEntity_buyer.getStatus());
            order_buyer.setCreated_at(orderEntity_buyer.getCreated_at());
            order_buyer.setShip_from_country(orderEntity_buyer.getShip_from_country());
            order_buyer.setShip_to_country(orderEntity_buyer.getShip_to_country());
            if (orderEntity_buyer.getProducts() != null && !orderEntity_buyer.getProducts().isEmpty()) {
                order_buyer.setProducts(productEntityDataMapper_buyer.transform(orderEntity_buyer.getProducts()));
            }
            if (orderEntity_buyer.getOffers() != null && !orderEntity_buyer.getOffers().isEmpty()) {
                order_buyer.setOffers(offerEntityDataMapper_buyer.transform(orderEntity_buyer.getOffers()));
            }
            order_buyer.setCountry(countryEntityDataMapper.transform(orderEntity_buyer.getCountry()));

        }
        return order_buyer;
    }
    /**
     * Transform a List of {@link OrderEnity_Buyer} into a Collection of {@link Order_Buyer}.
     *
     * @param orderEntityCollection Object Collection to be transformed.
     * @return {@link Order_Buyer} if valid {@link OrderEnity_Buyer} otherwise null.
     */
    public List<Order_Buyer> transform(Collection<OrderEnity_Buyer> orderEntityCollection) {
        final List<Order_Buyer> orderList = new ArrayList<>();
        for (OrderEnity_Buyer orderEntity_buyer : orderEntityCollection) {
            final Order_Buyer order_buyer = transform(orderEntity_buyer);
            if (order_buyer != null) {
                orderList.add(order_buyer);
            }
        }
        return orderList;
    }




    public Order_Buyer transform(OrderEntityResponse_Buyer orderEntityResponse_buyer) throws Exception {
        Order_Buyer order_buyer = null;
        if (orderEntityResponse_buyer != null) {
            if (orderEntityResponse_buyer.status() == false) {
                throw new Exception(orderEntityResponse_buyer.message());
            }
            order_buyer = transform(orderEntityResponse_buyer.data());
        }
        return order_buyer;
    }

    /**
     * Transform a List of {@link OrderEntityResponse_Buyer} into a Collection of {@link Order_Buyer}.
     *
     * @param orderListEntityResponse Object Collection to be transformed.
     * @return {@link Order_Buyer} if valid {@link OrderEntityResponse_Buyer} otherwise null.
     */
    public List<Order_Buyer> transform(OrderListEntityResponse_Buyer orderListEntityResponse) throws Exception {
        List<Order_Buyer> orderList = null;
        if (orderListEntityResponse != null) {
            if (orderListEntityResponse.status() == false) {
                throw new Exception(orderListEntityResponse.message());
            }
            orderList = transform(orderListEntityResponse.data());
        }
        return orderList;
    }

    public OrderEnity_Buyer transform(Order_Buyer order_buyer) {
        OrderEnity_Buyer orderEnity_buyer = null;
        if (order_buyer != null) {
            orderEnity_buyer = new OrderEnity_Buyer();
            orderEnity_buyer.setId(order_buyer.getId());
            orderEnity_buyer.setUserid(order_buyer.getUserid());
            orderEnity_buyer.setProviderid(order_buyer.getProviderid());
            orderEnity_buyer.setAmount(order_buyer.getAmount());
            orderEnity_buyer.setQuantity(order_buyer.getQuantity());
            orderEnity_buyer.setWeitght(order_buyer.getWeitght());
            orderEnity_buyer.setTax_percent(order_buyer.getTax_percent());
            orderEnity_buyer.setTax_amount(order_buyer.getTax_amount());
            orderEnity_buyer.setDeviverdate(order_buyer.getDeviverdate());
            orderEnity_buyer.setDeliverto(addressEntityDataMapper_buyer.transform(order_buyer.getAddress_buyer()));
            orderEnity_buyer.setIos(order_buyer.getIos());
            orderEnity_buyer.setCountry_name(order_buyer.getCountry_name());
            orderEnity_buyer.setCurrency(order_buyer.getCurrency());
            orderEnity_buyer.setDescription(order_buyer.getDescription());
            orderEnity_buyer.setStatus(order_buyer.getStatus());
            orderEnity_buyer.setCreated_at(order_buyer.getCreated_at());
            orderEnity_buyer.setShip_from_country(order_buyer.getShip_from_country());
            orderEnity_buyer.setShip_to_country(order_buyer.getShip_to_country());
            orderEnity_buyer.setProducts(productEntityDataMapper_buyer.transformToEntity(order_buyer.getProducts()));
            orderEnity_buyer.setOffers(offerEntityDataMapper_buyer.transformEntity(order_buyer.getOffers()));
            orderEnity_buyer.setCountry(countryEntityDataMapper.transform(order_buyer.getCountry()));

        }
        return orderEnity_buyer;
    }
}


