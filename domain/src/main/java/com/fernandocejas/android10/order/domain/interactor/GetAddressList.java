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
package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.repository.AddressRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 */
public class GetAddressList extends UseCase<List<Address>, GetAddressList.Params> {

    private final AddressRepository addressRepository;

    @Inject
    GetAddressList(AddressRepository addressRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.addressRepository = addressRepository;
    }

    @Override
    protected Observable<List<Address>> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.addressRepository.addresses(params.access_token);
    }

    public static final class Params {

        private final String access_token;

        private Params(String access_token) {
            this.access_token = access_token;
        }

        public static GetAddressList.Params forAddress(String access_token) {
            return new GetAddressList.Params(access_token);
        }
    }
}
