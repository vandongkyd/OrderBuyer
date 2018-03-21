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
package com.fernandocejas.android10.restrofit.enity.mapper;

import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.restrofit.enity.CountryEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 *
 */
@Singleton
public class CountryEntityDataMapper {

    @Inject
    CountryEntityDataMapper() {
    }

    public Country transform(CountryEntity countryEntity) {
        Country country = null;
        if (countryEntity != null) {
            country = new Country();
            country.setId(countryEntity.getId());
            country.setName(countryEntity.getName());
            country.setCode(countryEntity.getCode());
            country.setDial_code(countryEntity.getDial_code());
            country.setCurrency_name(countryEntity.getCurrency_name());
            country.setCurrency_symbol(countryEntity.getCurrency_symbol());
            country.setCurrency_code(countryEntity.getCurrency_code());
            country.setIos(countryEntity.getIos());
            country.setIso(countryEntity.getIso());
        }
        return country;
    }


    public List<Country> transform(Collection<CountryEntity> countryEntityCollection) {
        final List<Country> countryList = new ArrayList<>();
        for (CountryEntity countryEntity : countryEntityCollection) {
            final Country country = transform(countryEntity);
            if (country != null) {
                countryList.add(country);
            }
        }
        return countryList;
    }

    public CountryEntity transform(Country country) {
        CountryEntity countryEntity = null;
        if (country != null) {
            countryEntity = new CountryEntity();
            countryEntity.setId(country.getId());
            countryEntity.setName(country.getName());
            countryEntity.setCode(country.getCode());
            countryEntity.setDial_code(country.getDial_code());
            countryEntity.setCurrency_name(country.getCurrency_name());
            countryEntity.setCurrency_symbol(country.getCurrency_symbol());
            countryEntity.setCurrency_code(country.getCurrency_code());
            countryEntity.setIos(country.getIos());
            countryEntity.setIso(country.getIso());
        }
        return countryEntity;
    }
}
