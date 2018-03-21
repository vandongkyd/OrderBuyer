package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.SettingByCountry;
import com.fernandocejas.android10.order.domain.repository.SettingRepository;
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

public class GetSettingByCountry extends UseCase<SettingByCountry, GetSettingByCountry.Params> {

    private final SettingRepository settingRepository;

    @Inject
    GetSettingByCountry(SettingRepository settingRepository, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.settingRepository = settingRepository;
    }

    @Override
    protected Observable<SettingByCountry> buildUseCaseObservable(GetSettingByCountry.Params params) {
        Preconditions.checkNotNull(params);
        return this.settingRepository.setting(params.access_token, params.country);
    }

    public static final class Params {

        private final String access_token;

        private final String country;

        private Params(String access_token, String country) {
            this.access_token = access_token;
            this.country = country;
        }

        public static GetSettingByCountry.Params forSetting(String access_token, String country) {
            return new GetSettingByCountry.Params(access_token, country);
        }
    }
}

