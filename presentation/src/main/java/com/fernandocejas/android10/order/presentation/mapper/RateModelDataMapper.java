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

import com.fernandocejas.android10.order.domain.Rate;
import com.fernandocejas.android10.order.presentation.model.RateModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import javax.inject.Inject;

@PerActivity
public class RateModelDataMapper {

    @Inject
    public RateModelDataMapper() {
    }


    public RateModel transform(Rate rate) {
        if (rate == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final RateModel rateModel = new RateModel();
        rateModel.setStart(rate.getStart());
        rateModel.setCount(rate.getCount());
        return rateModel;
    }

    public Rate transform(RateModel rateModel) {
        if (rateModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Rate rate = new Rate();
        rate.setStart(rateModel.getStart());
        rate.setCount(rateModel.getCount());
        return rate;
    }

}

