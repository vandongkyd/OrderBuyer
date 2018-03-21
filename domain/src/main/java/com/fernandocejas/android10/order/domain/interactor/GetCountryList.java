package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.order.domain.repository.CountryRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 */

public class GetCountryList extends UseCase<List<Country>, GetOrderList.Params> {

    private final CountryRepository countryRepository;

    @Inject
    GetCountryList(CountryRepository countryRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.countryRepository = countryRepository;
    }

    @Override
    protected Observable<List<Country>> buildUseCaseObservable(GetOrderList.Params params) {
        return this.countryRepository.countries();
    }
}

