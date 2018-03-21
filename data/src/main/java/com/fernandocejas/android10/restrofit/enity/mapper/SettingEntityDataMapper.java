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

import com.fernandocejas.android10.order.domain.Setting;
import com.fernandocejas.android10.restrofit.enity.SettingEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingEntityDataMapper {

    @Inject
    SettingEntityDataMapper() {
    }


    public Setting transform(SettingEntity settingEntity) {
        Setting setting = null;
        if (settingEntity != null) {
            setting = new Setting();
            setting.setId(settingEntity.id());
            setting.setCode(settingEntity.code());
            setting.setDescription(settingEntity.description());
            setting.setValues(settingEntity.values());
        }
        return setting;
    }

    public List<Setting> transform(Collection<SettingEntity> settingEntityCollection) {
        final List<Setting> settingList = new ArrayList<>();
        for (SettingEntity settingEntity : settingEntityCollection) {
            final Setting setting = transform(settingEntity);
            if (setting != null) {
                settingList.add(setting);
            }
        }
        return settingList;
    }
}
