package com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer;



import com.fernandocejas.android10.order.domain.Model_buyer.Image_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.ImageEntity_Buyer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by vandongluong on 3/15/18.
 */
@Singleton
public class ImageEntityDataMapper_Buyer {
    @Inject
    ImageEntityDataMapper_Buyer() {

    }

    /**
     * Transform a {@link ImageEntity_Buyer} into an {@link Image_Buyer}.
     *
     * @param imageEntity Object to be transformed.
     * @return {@link Image_Buyer} if valid {@link ImageEntity_Buyer} otherwise null.
     */
    public Image_Buyer transform(ImageEntity_Buyer imageEntity) {
        Image_Buyer image_buyer = null;
        if (imageEntity != null) {
            image_buyer = new Image_Buyer();
            image_buyer.setImage(imageEntity.getImage());
        }
        return image_buyer;
    }

    /**
     * Transform a List of {@link ImageEntity_Buyer} into a Collection of {@link Image_Buyer}.
     *
     * @param imageEntityCollection Object Collection to be transformed.
     * @return {@link Image_Buyer} if valid {@link ImageEntity_Buyer} otherwise null.
     */
    public List<Image_Buyer> transform(Collection<ImageEntity_Buyer> imageEntityCollection) {
        final List<Image_Buyer> imageList = new ArrayList<>();
        for (ImageEntity_Buyer imageEntity_buyer : imageEntityCollection) {
            final Image_Buyer image = transform(imageEntity_buyer);
            if (image != null) {
                imageList.add(image);
            }
        }
        return imageList;
    }

    public ImageEntity_Buyer transform(Image_Buyer image_buyer) {
        ImageEntity_Buyer imageEntity_buyer = null;
        if (image_buyer != null) {
            imageEntity_buyer = new ImageEntity_Buyer();
            imageEntity_buyer.setImage(image_buyer.getImage());
        }
        return imageEntity_buyer;
    }

    public List<ImageEntity_Buyer> transformEntity(Collection<Image_Buyer> imageCollection) {
        final List<ImageEntity_Buyer> imageEntityList = new ArrayList<>();
        for (Image_Buyer image_buyer : imageCollection) {
            final ImageEntity_Buyer imageEntity_buyer = transform(image_buyer);
            if (imageEntity_buyer != null) {
                imageEntityList.add(imageEntity_buyer);
            }
        }
        return imageEntityList;
    }
}
