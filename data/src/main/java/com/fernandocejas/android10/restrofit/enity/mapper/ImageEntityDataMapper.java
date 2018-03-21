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

import com.fernandocejas.android10.order.domain.Image;
import com.fernandocejas.android10.restrofit.enity.ImageEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link ImageEntity} (in the data layer) to {@link Image} in the
 * domain layer.
 */
@Singleton
public class ImageEntityDataMapper {

    @Inject
    ImageEntityDataMapper() {
    }

    /**
     * Transform a {@link ImageEntity} into an {@link Image}.
     *
     * @param imageEntity Object to be transformed.
     * @return {@link Image} if valid {@link ImageEntity} otherwise null.
     */
    public Image transform(ImageEntity imageEntity) {
        Image image = null;
        if (imageEntity != null) {
            image = new Image();
            image.setImage(imageEntity.getImage());
        }
        return image;
    }

    /**
     * Transform a List of {@link ImageEntity} into a Collection of {@link Image}.
     *
     * @param imageEntityCollection Object Collection to be transformed.
     * @return {@link Image} if valid {@link ImageEntity} otherwise null.
     */
    public List<Image> transform(Collection<ImageEntity> imageEntityCollection) {
        final List<Image> imageList = new ArrayList<>();
        for (ImageEntity imageEntity : imageEntityCollection) {
            final Image image = transform(imageEntity);
            if (image != null) {
                imageList.add(image);
            }
        }
        return imageList;
    }

    public ImageEntity transform(Image image) {
        ImageEntity imageEntity = null;
        if (image != null) {
            imageEntity = new ImageEntity();
            imageEntity.setImage(image.getImage());
        }
        return imageEntity;
    }

    public List<ImageEntity> transformEntity(Collection<Image> imageCollection) {
        final List<ImageEntity> imageEntityList = new ArrayList<>();
        for (Image image : imageCollection) {
            final ImageEntity imageEntity = transform(image);
            if (imageEntity != null) {
                imageEntityList.add(imageEntity);
            }
        }
        return imageEntityList;
    }
}
