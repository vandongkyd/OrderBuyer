package com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Address_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.AddressEntityResponse_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.AddressEntity_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.AddressListEntityResponse_Buyer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by vandongluong on 3/14/18.
 */
@Singleton
public class AddressEntityDataMapper_Buyer {
    @Inject
    AddressEntityDataMapper_Buyer() {
    }
    /**
     * Transform a {@link AddressEntity_Buyer} into an {@link Address_Buyer}.
     *
     * @param addressEntity Object to be transformed.
     * @return {@link Address_Buyer} if valid {@link AddressEntity_Buyer} otherwise null.
     */
    public Address_Buyer transform(AddressEntity_Buyer addressEntity) {
        Address_Buyer address_buyer = null;
        if (addressEntity != null) {
            address_buyer = new Address_Buyer();
            address_buyer.setId(addressEntity.getId());
            address_buyer.setAddress(addressEntity.getAddress());
            address_buyer.setCity(addressEntity.getCity());
            address_buyer.setState(addressEntity.getState());
            address_buyer.setPostcode(addressEntity.getPostcode());
            address_buyer.setCode(addressEntity.getCode());
            address_buyer.setCountry(addressEntity.getCountry());

        }
        return address_buyer;
    }

    /**
     * Transform a List of {@link AddressEntity_Buyer} into a Collection of {@link Address_Buyer}.
     *
     * @param addressEntityCollection_buyer Object Collection to be transformed.
     * @return {@link Address_Buyer} if valid {@link AddressEntity_Buyer} otherwise null.
     */
    public List<Address_Buyer> transform(Collection<AddressEntity_Buyer> addressEntityCollection_buyer) {
        final List<Address_Buyer> addressList_buyer = new ArrayList<>();
        for (AddressEntity_Buyer addressEntity : addressEntityCollection_buyer) {
            final Address_Buyer address = transform(addressEntity);
            if (address != null) {
                addressList_buyer.add(address);
            }
        }
        return addressList_buyer;
    }


    public AddressEntity_Buyer transform(Address_Buyer address) {
        AddressEntity_Buyer addressEntity_buyer = null;
        if (address != null) {
            addressEntity_buyer = new AddressEntity_Buyer();
            addressEntity_buyer.setId(address.getId());
            addressEntity_buyer.setAddress(address.getAddress());
            addressEntity_buyer.setCity(address.getCity());
            addressEntity_buyer.setState(address.getState());
            addressEntity_buyer.setPostcode(address.getPostcode());
            addressEntity_buyer.setCode(address.getCode());
            addressEntity_buyer.setCountry(address.getCountry());
        }
        return addressEntity_buyer;
    }

    public List<AddressEntity_Buyer> transformToEntity(Collection<Address_Buyer> addressCollection_buyer) {
        final List<AddressEntity_Buyer> addressEntityList_buyer = new ArrayList<>();
        for (Address_Buyer address : addressCollection_buyer) {
            final AddressEntity_Buyer addressEntity_buyer = transform(address);
            if (addressEntity_buyer != null) {
                addressEntityList_buyer.add(addressEntity_buyer);
            }
        }
        return addressEntityList_buyer;
    }

    public List<Address_Buyer> transform(AddressListEntityResponse_Buyer addressListEntityResponse_buyer) throws Exception {
        List<Address_Buyer> addressList_buyer = null;
        if (addressListEntityResponse_buyer != null) {
            if (addressListEntityResponse_buyer.status() == false) {
                throw new Exception(addressListEntityResponse_buyer.message());
            }
            addressList_buyer = transform(addressListEntityResponse_buyer.data());
        }
        return addressList_buyer;
    }

    public Address_Buyer transform(AddressEntityResponse_Buyer addressEntityResponse_buyer) throws Exception {
        Address_Buyer address_buyer = null;
        if (addressEntityResponse_buyer != null) {
            if (addressEntityResponse_buyer.status() == false) {
                throw new Exception(addressEntityResponse_buyer.message());
            }
            address_buyer = transform(addressEntityResponse_buyer.data());
        }
        return address_buyer;
    }
}
