package com.fernandocejas.android10.order.domain.interactor_buyer;


import com.fernandocejas.android10.order.domain.Model_buyer.Address_Buyer;
import com.fernandocejas.android10.order.domain.repository_buyer.AddressRepository_Buyer;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/14/18.
 */

public class GetAddressList_Buyer extends UseCase<List<Address_Buyer>, GetAddressList_Buyer.Params> {

    private final AddressRepository_Buyer addressRepository_buyer;

    @Inject
    GetAddressList_Buyer(AddressRepository_Buyer addressRepository_buyer, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.addressRepository_buyer = addressRepository_buyer;
    }

    @Override
    protected Observable<List<Address_Buyer>> buildUseCaseObservable(GetAddressList_Buyer.Params params) {
        Preconditions.checkNotNull(params);
        return this.addressRepository_buyer.addresses(params.access_token);
    }

    public static final class Params {

        private final String access_token;

        private Params(String access_token) {
            this.access_token = access_token;
        }

        public static GetAddressList_Buyer.Params forAddress(String access_token) {
            return new GetAddressList_Buyer.Params(access_token);
        }
    }
}
