package com.fernandocejas.android10.order.presentation.mapper_buyer;

import com.fernandocejas.android10.order.domain.Model_buyer.Product_Buyer;
import com.fernandocejas.android10.order.presentation.Model_buyer.ProductModel_Buyer;
import com.fernandocejas.android10.order.presentation.mapper.CountryModelDataMapper;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import javax.inject.Inject;

/**
 * Created by vandongluong on 3/19/18.
 */
@PerActivity
public class ProductModelDataMapper_Buyer {
    private final ImageModelDataMapper_Buyer imageModelDataMapper_buyer;
    private final CountryModelDataMapper countryModelDataMapper;

    @Inject
    public ProductModelDataMapper_Buyer(ImageModelDataMapper_Buyer imageModelDataMapper_buyer,
                                        CountryModelDataMapper countryModelDataMapper) {
        this.imageModelDataMapper_buyer = imageModelDataMapper_buyer;
        this.countryModelDataMapper = countryModelDataMapper;
    }
    /**
     * Transform a {@link Product_Buyer} into an {@link ProductModel_Buyer}.
     *
     * @param product Object to be transformed.
     * @return {@link ProductModel_Buyer}.
     */
    public ProductModel_Buyer transform(Product_Buyer product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final ProductModel_Buyer productModel = new ProductModel_Buyer();
        productModel.setId(product.getId());
        productModel.setOrderid(product.getOrderid());
        productModel.setLink(product.getLink());
        productModel.setName(product.getName());
        productModel.setCode(product.getCode());
        productModel.setDescription(product.getDescription());
        productModel.setPrice(product.getPrice());
        productModel.setQuantity(product.getQuantity());
        productModel.setWeight(product.getWeight());
        productModel.setImages(imageModelDataMapper_buyer.transform(product.getImages()));
        if (product.getCountry() != null) {
            productModel.setCountry(countryModelDataMapper.transform(product.getCountry()));
        }

        return productModel;
    }
}
