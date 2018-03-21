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

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Address} (in the domain layer) to {@link AddressModel} in the
 * presentation layer.
 */
@PerActivity
public class AddressModelDataMapper {

    @Inject
    public AddressModelDataMapper() {
    }

    /**
     * Transform a {@link Address} into an {@link AddressModel}.
     *
     * @param address Object to be transformed.
     * @return {@link AddressModel}.
     */
    public AddressModel transform(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final AddressModel addressModel = new AddressModel();
        addressModel.setId(address.getId());
        addressModel.setAddress(address.getAddress());
        addressModel.setCity(address.getCity());
        addressModel.setState(address.getState());
        addressModel.setPostcode(address.getPostcode());
        addressModel.setCode(address.getCode());
        addressModel.setCountry(address.getCountry());
        addressModel.setLat(address.getLat());
        addressModel.setLng(address.getLng());
        return addressModel;
    }

    /**
     * Transform a Collection of {@link Address} into a Collection of {@link AddressModel}.
     *
     * @param addressCollection Objects to be transformed.
     * @return List of {@link AddressModel}.
     */
    public Collection<AddressModel> transform(Collection<Address> addressCollection) {
        Collection<AddressModel> addressModelsCollection;

        if (addressCollection != null && !addressCollection.isEmpty()) {
            addressModelsCollection = new ArrayList<>();
            for (Address Address : addressCollection) {
                addressModelsCollection.add(transform(Address));
            }
        } else {
            addressModelsCollection = Collections.emptyList();
        }

        return addressModelsCollection;
    }

    public Address transform(AddressModel addressModel) {
        if (addressModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Address address = new Address();
        address.setId(addressModel.getId());
        address.setAddress(addressModel.getAddress());
        address.setCity(addressModel.getCity());
        address.setState(addressModel.getState());
        address.setPostcode(addressModel.getPostcode());
        address.setCode(addressModel.getCode());
        address.setCountry(addressModel.getCountry());
        address.setLat(addressModel.getLat());
        address.setLng(addressModel.getLng());
        return address;
    }
}

