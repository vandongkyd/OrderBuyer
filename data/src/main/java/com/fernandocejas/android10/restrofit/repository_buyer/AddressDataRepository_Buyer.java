package com.fernandocejas.android10.restrofit.repository_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Address_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.AddressRepository_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer.AddressEntityDataMapper_Buyer;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper_Buyer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/14/18.
 */
@Singleton
public class AddressDataRepository_Buyer implements AddressRepository_Buyer {

    private final RetrofitHelper_Buyer retrofitHelper;

    private final AddressEntityDataMapper_Buyer addressEntityDataMapper_buyer;

    @Inject
    AddressDataRepository_Buyer(
            RetrofitHelper_Buyer retrofitHelper,
            AddressEntityDataMapper_Buyer addressEntityDataMapper_buyer) {
        this.retrofitHelper = retrofitHelper;
        this.addressEntityDataMapper_buyer = addressEntityDataMapper_buyer;
    }

    @Override
    public Observable<List<Address_Buyer>> addresses(String token) {
        return retrofitHelper
                .getRestApiService()
                .addresses_buyer("Bearer " + token)
                .map(this.addressEntityDataMapper_buyer::transform);
    }

    @Override
    public Observable<Address_Buyer> addAddresses(String token, String address, String city, String postcode, String country, String country_code) {
        return retrofitHelper
                .getRestApiService()
                .new_address_buyer("Bearer " + token,
                        address,
                        city,
                        postcode,
                        country,
                        country_code)
                .map(this.addressEntityDataMapper_buyer::transform);
    }
}
