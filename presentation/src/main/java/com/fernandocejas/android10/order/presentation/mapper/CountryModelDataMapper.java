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

import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.order.presentation.model.CountryModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class CountryModelDataMapper {

    @Inject
    public CountryModelDataMapper() {
    }

    public CountryModel transform(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final CountryModel countryModel = new CountryModel();
        countryModel.setId(country.getId());
        countryModel.setName(country.getName());
        countryModel.setCode(country.getCode());
        countryModel.setDial_code(country.getDial_code());
        countryModel.setCurrency_name(country.getCurrency_name());
        countryModel.setCurrency_symbol(country.getCurrency_symbol());
        countryModel.setCurrency_code(country.getCurrency_code());
        countryModel.setIos(country.getIos());
        countryModel.setIso(country.getIso());
        return countryModel;
    }

    public Collection<CountryModel> transform(Collection<Country> countryCollection) {
        Collection<CountryModel> countryModelCollection;

        if (countryCollection != null && !countryCollection.isEmpty()) {
            countryModelCollection = new ArrayList<>();
            for (Country country : countryCollection) {
                countryModelCollection.add(transform(country));
            }
        } else {
            countryModelCollection = Collections.emptyList();
        }

        return countryModelCollection;
    }

    public Country transform(CountryModel countryModel) {
        if (countryModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Country country = new Country();
        country.setId(countryModel.getId());
        country.setName(countryModel.getName());
        country.setCode(countryModel.getCode());
        country.setDial_code(countryModel.getDial_code());
        country.setCurrency_name(countryModel.getCurrency_name());
        country.setCurrency_symbol(countryModel.getCurrency_symbol());
        country.setCurrency_code(countryModel.getCurrency_code());
        country.setIos(countryModel.getIos());
        country.setIso(countryModel.getIso());
        return country;
    }
}

