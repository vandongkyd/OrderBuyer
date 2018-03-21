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

import com.fernandocejas.android10.order.domain.SettingByCountry;
import com.fernandocejas.android10.restrofit.enity.SettingByCountryEntity;
import com.fernandocejas.android10.restrofit.enity.SettingByCountryEntityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingByCountryEntityDataMapper {

    private final SettingEntityDataMapper settingEntityDataMapper;
    private final AddressEntityDataMapper addressEntityDataMapper;

    @Inject
    SettingByCountryEntityDataMapper(SettingEntityDataMapper settingEntityDataMapper,
                                     AddressEntityDataMapper addressEntityDataMapper) {
        this.settingEntityDataMapper = settingEntityDataMapper;
        this.addressEntityDataMapper = addressEntityDataMapper;
    }


    public SettingByCountry transform(SettingByCountryEntity settingByCountryEntity) {
        SettingByCountry settingByCountry = null;
        if (settingByCountryEntity != null) {
            settingByCountry = new SettingByCountry();
            settingByCountry.setId(settingByCountryEntity.id());
            settingByCountry.setName(settingByCountryEntity.name());
            settingByCountry.setCode(settingByCountryEntity.code());
            settingByCountry.setPhone_code(settingByCountryEntity.phone_code());
            settingByCountry.setCurrency(settingByCountryEntity.currency());
            settingByCountry.setSetting(settingEntityDataMapper.transform(settingByCountryEntity.setting()));
            settingByCountry.setAddresses(addressEntityDataMapper.transform(settingByCountryEntity.addresses()));
        }
        return settingByCountry;
    }

    public List<SettingByCountry> transform(Collection<SettingByCountryEntity> settingByCountryEntityCollection) {
        final List<SettingByCountry> settingByCountryList = new ArrayList<>();
        for (SettingByCountryEntity settingByCountryEntity : settingByCountryEntityCollection) {
            final SettingByCountry settingByCountry = transform(settingByCountryEntity);
            if (settingByCountry != null) {
                settingByCountryList.add(settingByCountry);
            }
        }
        return settingByCountryList;
    }

    public SettingByCountry transform(SettingByCountryEntityResponse settingByCountryEntityResponse) throws Exception {
        SettingByCountry settingByCountry = null;
        if (settingByCountryEntityResponse != null) {
            if (settingByCountryEntityResponse.status() == false) {
                throw new Exception(settingByCountryEntityResponse.message());
            }
            settingByCountry = transform(settingByCountryEntityResponse.data());
        }
        return settingByCountry;
    }
}
