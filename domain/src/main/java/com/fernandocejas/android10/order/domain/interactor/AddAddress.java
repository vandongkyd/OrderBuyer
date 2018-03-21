package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.repository.AddressRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 */

public class AddAddress extends UseCase<Address, AddAddress.Params> {

    private final AddressRepository addressRepository;

    @Inject
    AddAddress(AddressRepository addressRepository, ThreadExecutor threadExecutor,
               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.addressRepository = addressRepository;
    }

    @Override
    protected Observable<Address> buildUseCaseObservable(AddAddress.Params params) {
        Preconditions.checkNotNull(params);
        return this.addressRepository.addAddresses(params.access_token,
                params.address,
                params.city,
                params.postcode,
                params.country,
                params.country_code,
                params.lat,
                params.lng);
    }

    public static final class Params {

        private final String access_token;
        private final String address;
        private final String city;
        private final String postcode;
        private final String country;
        private final String country_code;
        private final String lat;
        private final String lng;

        private Params(String access_token,
                       String address,
                       String city,
                       String postcode,
                       String country,
                       String country_code,
                       String lat,
                       String lng) {
            this.access_token = access_token;
            this.address = address;
            this.city = city;
            this.postcode = postcode;
            this.country = country;
            this.country_code = country_code;
            this.lat = lat;
            this.lng = lng;
        }

        public static AddAddress.Params forAddAddress(String access_token,
                                                   String address,
                                                   String city,
                                                   String postcode,
                                                   String country,
                                                   String country_code,
                                                   String lat,
                                                   String lng) {
            return new AddAddress.Params(access_token,
                    address,
                    city,
                    postcode,
                    country,
                    country_code,
                    lat,
                    lng);
        }
    }
}

