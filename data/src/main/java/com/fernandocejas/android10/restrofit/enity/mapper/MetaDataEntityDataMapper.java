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

import com.fernandocejas.android10.order.domain.MetaData;
import com.fernandocejas.android10.restrofit.enity.MetaDataEntity;
import com.fernandocejas.android10.restrofit.enity.MetaDataEntityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MetaDataEntityDataMapper {

    @Inject
    MetaDataEntityDataMapper() {
    }

    public MetaData transform(MetaDataEntity MetaDataEntity) {
        MetaData MetaData = null;
        if (MetaDataEntity != null) {
            MetaData = new MetaData();
            MetaData.setKey(MetaDataEntity.getKey());
            MetaData.setValue(MetaDataEntity.getValue());
        }
        return MetaData;
    }

    public List<MetaData> transform(Collection<MetaDataEntity> MetaDataEntities) {
        final List<MetaData> MetaDataList = new ArrayList<>();
        if (MetaDataEntities != null && !MetaDataEntities.isEmpty()) {
            for (MetaDataEntity MetaDataEntity : MetaDataEntities) {
                final MetaData MetaData = transform(MetaDataEntity);
                if (MetaData != null) {
                    MetaDataList.add(MetaData);
                }
            }
        }
        return MetaDataList;
    }

    public MetaData transform(MetaDataEntityResponse metaDataEntityResponse) throws Exception {
        MetaData metaData = null;
        if (metaDataEntityResponse != null) {
            if (metaDataEntityResponse.status() == false) {
                throw new Exception(metaDataEntityResponse.message());
            }
            MetaDataEntity metaDataEntity = metaDataEntityResponse.data();
            metaData = transform(metaDataEntity);
        }
        return metaData;
    }

}
