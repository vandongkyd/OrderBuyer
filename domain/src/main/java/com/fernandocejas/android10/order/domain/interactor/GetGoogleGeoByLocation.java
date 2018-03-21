package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.GoogleGeo;
import com.fernandocejas.android10.order.domain.repository.GoogleGeoRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by jerry on Jan-18-18.
 */

public class GetGoogleGeoByLocation extends UseCase<List<GoogleGeo>, GetGoogleGeoByLocation.Params> {

    private final GoogleGeoRepository googleGeoRepository;

    @Inject
    GetGoogleGeoByLocation(GoogleGeoRepository googleGeoRepository, ThreadExecutor threadExecutor,
                           PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.googleGeoRepository = googleGeoRepository;
    }

    @Override
    protected Observable<List<GoogleGeo>> buildUseCaseObservable(GetGoogleGeoByLocation.Params params) {
        Preconditions.checkNotNull(params);
        return this.googleGeoRepository.geocode(params.lat, params.lng);
    }

    public static final class Params {

        private final String lat;
        private final String lng;

        private Params(String lat, String lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public static GetGoogleGeoByLocation.Params forGeoCode(String lat, String lng) {
            return new GetGoogleGeoByLocation.Params(lat, lng);
        }
    }
}
