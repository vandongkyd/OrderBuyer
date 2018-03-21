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

import com.fernandocejas.android10.order.domain.GoogleGeo;
import com.fernandocejas.android10.restrofit.enity.GAddressComponentsEntity;
import com.fernandocejas.android10.restrofit.enity.GLocationEntity;
import com.fernandocejas.android10.restrofit.enity.GoogleGeoEntity;
import com.fernandocejas.android10.restrofit.enity.GoogleGeoListEntityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GoogleGeoEntityDataMapper {

    private final String TYPE_COMPONENTS_CITY = "administrative_area_level_1";
    private final String TYPE_COMPONENTS_COUNTRY = "country";
    private final String TYPE_COMPONENTS_POSTCODE = "postal_code";

    @Inject
    GoogleGeoEntityDataMapper() {
    }

    public GoogleGeo transform(GoogleGeoEntity googleGeoEntity) {
        GoogleGeo googleGeo = null;
        if (googleGeoEntity != null) {
            googleGeo = new GoogleGeo();
            googleGeo.setFormatted_address(googleGeoEntity.getFormatted_address());
            Collection<GAddressComponentsEntity> componentsEntities = googleGeoEntity.getAddress_components();
            if (componentsEntities != null && !componentsEntities.isEmpty()) {
                for (GAddressComponentsEntity addressComponentsEntity : componentsEntities) {
                    if (addressComponentsEntity.getTypes().contains(TYPE_COMPONENTS_CITY)) {
                        googleGeo.setCity(addressComponentsEntity.getLong_name());
                    } else if (addressComponentsEntity.getTypes().contains(TYPE_COMPONENTS_POSTCODE)) {
                        googleGeo.setPostcode(addressComponentsEntity.getLong_name());
                    } else if (addressComponentsEntity.getTypes().contains(TYPE_COMPONENTS_COUNTRY)) {
                        googleGeo.setCountry(addressComponentsEntity.getLong_name());
                        googleGeo.setCountry_code(addressComponentsEntity.getShort_name());
                    }
                }
            }
            try {
                GLocationEntity locationEntity = googleGeoEntity.getGeometry().getLocation();
                googleGeo.setLat(locationEntity.getLat());
                googleGeo.setLng(locationEntity.getLng());
            }catch (Exception ex){}
        }
        return googleGeo;
    }

    public List<GoogleGeo> transform(Collection<GoogleGeoEntity> googleGeoEntities) {
        final List<GoogleGeo> googleGeos = new ArrayList<>();
        for (GoogleGeoEntity googleGeoEntity : googleGeoEntities) {
            final GoogleGeo googleGeo = transform(googleGeoEntity);
            if (googleGeo != null) {
                googleGeos.add(googleGeo);
            }
        }
        return googleGeos;
    }

    public List<GoogleGeo> transform(GoogleGeoListEntityResponse geoListEntityResponse) throws Exception {
        List<GoogleGeo> googleGeos = null;
        if (geoListEntityResponse != null) {
            if (geoListEntityResponse.results() != null && !geoListEntityResponse.results().isEmpty()) {
                googleGeos = transform(geoListEntityResponse.results());
            }
        }
        return googleGeos;
    }

}
