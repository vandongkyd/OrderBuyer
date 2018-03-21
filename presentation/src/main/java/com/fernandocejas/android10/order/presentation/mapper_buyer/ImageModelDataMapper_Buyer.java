package com.fernandocejas.android10.order.presentation.mapper_buyer;


import com.fernandocejas.android10.order.domain.Image;
import com.fernandocejas.android10.order.domain.Model_buyer.Image_Buyer;
import com.fernandocejas.android10.order.presentation.Model_buyer.ImageModel_Buyer;
import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by vandongluong on 3/19/18.
 */
@PerActivity
public class ImageModelDataMapper_Buyer {
    @Inject
    public ImageModelDataMapper_Buyer() {
    }

    /**
     * Transform a {@link Image_Buyer} into an {@link ImageModel_Buyer}.
     *
     * @param image_buyer Object to be transformed.
     * @return {@link ImageModel_Buyer}.
     */
    public ImageModel_Buyer transform(Image_Buyer image_buyer) {
        if (image_buyer == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final ImageModel_Buyer imageModel = new ImageModel_Buyer();
        imageModel.setImage(image_buyer.getImage());
        return imageModel;
    }

    /**
     * Transform a Collection of {@link Image_Buyer} into a Collection of {@link ImageModel_Buyer}.
     *
     * @param imagesCollectionbuyer Objects to be transformed.
     * @return List of {@link ImageModel_Buyer}.
     */
    public Collection<ImageModel_Buyer> transform(Collection<Image_Buyer> imagesCollectionbuyer) {
        Collection<ImageModel_Buyer> imageModelsCollection;

        if (imagesCollectionbuyer != null && !imagesCollectionbuyer.isEmpty()) {
            imageModelsCollection = new ArrayList<>();
            for (Image_Buyer image : imagesCollectionbuyer) {
                imageModelsCollection.add(transform(image));
            }
        } else {
            imageModelsCollection = Collections.emptyList();
        }

        return imageModelsCollection;
    }
    public Image_Buyer transform(ImageModel_Buyer imageModelBuyer) {
        if (imageModelBuyer == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Image_Buyer image = new Image_Buyer();
        image.setImage(imageModelBuyer.getImage());
        return image;
    }

    public Collection<Image_Buyer> transformToDomain(Collection<ImageModel_Buyer> imageModelsbuyer) {
        Collection<Image_Buyer> images_buyer;

        if (imageModelsbuyer != null && !imageModelsbuyer.isEmpty()) {
            images_buyer = new ArrayList<>();
            for (ImageModel_Buyer imageModel : imageModelsbuyer) {
                images_buyer.add(transform(imageModel));
            }
        } else {
            images_buyer = Collections.emptyList();
        }

        return images_buyer;
    }
}
