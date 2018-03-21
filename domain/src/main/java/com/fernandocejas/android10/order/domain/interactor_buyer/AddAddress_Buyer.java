package com.fernandocejas.android10.order.domain.interactor_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Address_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.AddressRepository_Buyer;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/14/18.
 */

public class AddAddress_Buyer extends UseCase<Address_Buyer, AddAddress_Buyer.Params> {

    private final AddressRepository_Buyer addressRepository_buyer;

    @Inject
    AddAddress_Buyer(AddressRepository_Buyer addressRepository_buyer, ThreadExecutor threadExecutor,
               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.addressRepository_buyer = addressRepository_buyer;
    }
    @Override
    protected Observable<Address_Buyer> buildUseCaseObservable(AddAddress_Buyer.Params params) {
        return this.addressRepository_buyer.addAddresses(params.access_token,
                params.address,
                params.city,
                params.postcode,
                params.country,
                params.country_code);
    }


    public static final class Params {

        private final String access_token;
        private final String address;
        private final String city;
        private final String postcode;
        private final String country;
        private final String country_code;

        private Params(String access_token,
                       String address,
                       String city,
                       String postcode,
                       String country,
                       String country_code) {
            this.access_token = access_token;
            this.address = address;
            this.city = city;
            this.postcode = postcode;
            this.country = country;
            this.country_code = country_code;
        }

        public static AddAddress_Buyer.Params forAddAddress(String access_token,
                                                      String address,
                                                      String city,
                                                      String postcode,
                                                      String country,
                                                      String country_code) {
            return new AddAddress_Buyer.Params(access_token,
                    address,
                    city,
                    postcode,
                    country,
                    country_code);
        }
    }

}
