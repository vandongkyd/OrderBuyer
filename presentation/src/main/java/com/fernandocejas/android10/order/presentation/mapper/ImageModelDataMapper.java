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

import com.fernandocejas.android10.order.domain.Image;
import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Image} (in the domain layer) to {@link ImageModel} in the
 * presentation layer.
 */
@PerActivity
public class ImageModelDataMapper {

    @Inject
    public ImageModelDataMapper() {
    }

    /**
     * Transform a {@link Image} into an {@link ImageModel}.
     *
     * @param image Object to be transformed.
     * @return {@link ImageModel}.
     */
    public ImageModel transform(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final ImageModel imageModel = new ImageModel();
        imageModel.setImage(image.getImage());
        return imageModel;
    }

    /**
     * Transform a Collection of {@link Image} into a Collection of {@link ImageModel}.
     *
     * @param imagesCollection Objects to be transformed.
     * @return List of {@link ImageModel}.
     */
    public Collection<ImageModel> transform(Collection<Image> imagesCollection) {
        Collection<ImageModel> imageModelsCollection;

        if (imagesCollection != null && !imagesCollection.isEmpty()) {
            imageModelsCollection = new ArrayList<>();
            for (Image image : imagesCollection) {
                imageModelsCollection.add(transform(image));
            }
        } else {
            imageModelsCollection = Collections.emptyList();
        }

        return imageModelsCollection;
    }

    public Image transform(ImageModel imageModel) {
        if (imageModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Image image = new Image();
        image.setImage(imageModel.getImage());
        return image;
    }

    public Collection<Image> transformToDomain(Collection<ImageModel> imageModels) {
        Collection<Image> images;

        if (imageModels != null && !imageModels.isEmpty()) {
            images = new ArrayList<>();
            for (ImageModel imageModel : imageModels) {
                images.add(transform(imageModel));
            }
        } else {
            images = Collections.emptyList();
        }

        return images;
    }
}

