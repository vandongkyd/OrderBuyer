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

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.Event;
import com.fernandocejas.android10.restrofit.enity.AddressEntity;
import com.fernandocejas.android10.restrofit.enity.AddressEntityResponse;
import com.fernandocejas.android10.restrofit.enity.AddressListEntityResponse;
import com.fernandocejas.android10.restrofit.enity.EventListEntityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link AddressEntity} (in the data layer) to {@link Address} in the
 * domain layer.
 */
@Singleton
public class AddressEntityDataMapper {

    @Inject
    AddressEntityDataMapper() {
    }

    /**
     * Transform a {@link AddressEntity} into an {@link Address}.
     *
     * @param addressEntity Object to be transformed.
     * @return {@link Address} if valid {@link AddressEntity} otherwise null.
     */
    public Address transform(AddressEntity addressEntity) {
        Address address = null;
        if (addressEntity != null) {
            address = new Address();
            address.setId(addressEntity.getId());
            address.setAddress(addressEntity.getAddress());
            address.setCity(addressEntity.getCity());
            address.setState(addressEntity.getState());
            address.setPostcode(addressEntity.getPostcode());
            address.setCode(addressEntity.getCode());
            address.setCountry(addressEntity.getCountry());
            address.setLat(addressEntity.getLat());
            address.setLng(addressEntity.getLng());
        }
        return address;
    }

    /**
     * Transform a List of {@link AddressEntity} into a Collection of {@link Address}.
     *
     * @param addressEntityCollection Object Collection to be transformed.
     * @return {@link Address} if valid {@link AddressEntity} otherwise null.
     */
    public List<Address> transform(Collection<AddressEntity> addressEntityCollection) {
        final List<Address> addressList = new ArrayList<>();
        for (AddressEntity addressEntity : addressEntityCollection) {
            final Address address = transform(addressEntity);
            if (address != null) {
                addressList.add(address);
            }
        }
        return addressList;
    }

    public AddressEntity transform(Address address) {
        AddressEntity addressEntity = null;
        if (address != null) {
            addressEntity = new AddressEntity();
            addressEntity.setId(address.getId());
            addressEntity.setAddress(address.getAddress());
            addressEntity.setCity(address.getCity());
            addressEntity.setState(address.getState());
            addressEntity.setPostcode(address.getPostcode());
            addressEntity.setCode(address.getCode());
            addressEntity.setCountry(address.getCountry());
            addressEntity.setLat(address.getLat());
            addressEntity.setLng(address.getLng());
        }
        return addressEntity;
    }

    public List<AddressEntity> transformToEntity(Collection<Address> addressCollection) {
        final List<AddressEntity> addressEntityList = new ArrayList<>();
        for (Address address : addressCollection) {
            final AddressEntity addressEntity = transform(address);
            if (addressEntity != null) {
                addressEntityList.add(addressEntity);
            }
        }
        return addressEntityList;
    }

    public List<Address> transform(AddressListEntityResponse addressListEntityResponse) throws Exception {
        List<Address> addressList = null;
        if (addressListEntityResponse != null) {
            if (addressListEntityResponse.status() == false) {
                throw new Exception(addressListEntityResponse.message());
            }
            addressList = transform(addressListEntityResponse.data());
        }
        return addressList;
    }

    public Address transform(AddressEntityResponse addressEntityResponse) throws Exception {
        Address address = null;
        if (addressEntityResponse != null) {
            if (addressEntityResponse.status() == false) {
                throw new Exception(addressEntityResponse.message());
            }
            address = transform(addressEntityResponse.data());
        }
        return address;
    }
}
