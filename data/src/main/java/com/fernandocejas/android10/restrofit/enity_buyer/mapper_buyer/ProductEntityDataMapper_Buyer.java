package com.fernandocejas.android10.restrofit.enity_buyer.mapper_buyer;


import com.fernandocejas.android10.order.domain.Model_buyer.Product_Buyer;
import com.fernandocejas.android10.restrofit.enity.mapper.CountryEntityDataMapper;
import com.fernandocejas.android10.restrofit.enity_buyer.ProductEntityResponse_Buyer;
import com.fernandocejas.android10.restrofit.enity_buyer.ProductEntity_Buyer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by vandongluong on 3/15/18.
 */
@Singleton
public class ProductEntityDataMapper_Buyer {

    private final ImageEntityDataMapper_Buyer imageEntityDataMapper_buyer;
    private final CountryEntityDataMapper countryEntityDataMapper;
    @Inject
    ProductEntityDataMapper_Buyer(ImageEntityDataMapper_Buyer imageEntityDataMapper_buyer,
                                  CountryEntityDataMapper countryEntityDataMapper){

        this.imageEntityDataMapper_buyer = imageEntityDataMapper_buyer;
        this.countryEntityDataMapper = countryEntityDataMapper;
    }

    /**
     * Transform a {@link ProductEntity_Buyer} into an {@link Product_Buyer}.
     *
     * @param productEntity_buyer Object to be transformed.
     * @return {@link Product_Buyer} if valid {@link ProductEntity_Buyer} otherwise null.
     */
    public Product_Buyer transform(ProductEntity_Buyer productEntity_buyer) {
        Product_Buyer product_buyer = null;
        if (productEntity_buyer != null) {
            product_buyer = new Product_Buyer();
            product_buyer.setId(productEntity_buyer.getId());
            product_buyer.setOrderid(productEntity_buyer.getOrderid());
            product_buyer.setLink(productEntity_buyer.getLink());
            product_buyer.setName(productEntity_buyer.getName());
            product_buyer.setCode(productEntity_buyer.getCode());
            product_buyer.setDescription(productEntity_buyer.getDescription());
            product_buyer.setPrice(productEntity_buyer.getPrice());
            product_buyer.setQuantity(productEntity_buyer.getQuantity());
            product_buyer.setWeight(productEntity_buyer.getWeight());
            product_buyer.setImages(imageEntityDataMapper_buyer.transform(productEntity_buyer.getImages()));
            product_buyer.setCountry(countryEntityDataMapper.transform(productEntity_buyer.getCountry()));
        }
        return product_buyer;
    }

    /**
     * Transform a List of {@link ProductEntity_Buyer} into a Collection of {@link Product_Buyer}.
     *
     * @param productEntityCollection Object Collection to be transformed.
     * @return {@link Product_Buyer} if valid {@link ProductEntity_Buyer} otherwise null.
     */
    public List<Product_Buyer> transform(Collection<ProductEntity_Buyer> productEntityCollection) {
        final List<Product_Buyer> productList = new ArrayList<>();
        for (ProductEntity_Buyer productEntity : productEntityCollection) {
            final Product_Buyer product = transform(productEntity);
            if (product != null) {
                productList.add(product);
            }
        }
        return productList;
    }

    public Product_Buyer transform(ProductEntityResponse_Buyer productEntityResponse_buyer) throws Exception {
        Product_Buyer product = null;
        if (productEntityResponse_buyer != null) {
            if (productEntityResponse_buyer.status() == false) {
                throw new Exception(productEntityResponse_buyer.message());
            }
            product = transform(productEntityResponse_buyer.data());
        }
        return product;
    }

    public ProductEntity_Buyer transform(Product_Buyer product_buyer) {
        ProductEntity_Buyer productEntity_buyer = null;
        if (product_buyer != null) {
            productEntity_buyer = new ProductEntity_Buyer();
            productEntity_buyer.setId(product_buyer.getId());
            productEntity_buyer.setOrderid(product_buyer.getOrderid());
            productEntity_buyer.setLink(product_buyer.getLink());
            productEntity_buyer.setName(product_buyer.getName());
            productEntity_buyer.setCode(product_buyer.getCode());
            productEntity_buyer.setDescription(product_buyer.getDescription());
            productEntity_buyer.setPrice(product_buyer.getPrice());
            productEntity_buyer.setQuantity(product_buyer.getQuantity());
            productEntity_buyer.setWeight(product_buyer.getWeight());
            productEntity_buyer.setImages(imageEntityDataMapper_buyer.transformEntity(product_buyer.getImages()));
            productEntity_buyer.setCountry(countryEntityDataMapper.transform(product_buyer.getCountry()));

        }
        return productEntity_buyer;
    }

    public List<ProductEntity_Buyer> transformToEntity(Collection<Product_Buyer> productCollection) {
        final List<ProductEntity_Buyer> productEntityList = new ArrayList<>();
        if (productCollection != null && !productCollection.isEmpty()) {
            for (Product_Buyer product : productCollection) {
                final ProductEntity_Buyer productEntity = transform(product);
                if (productEntity != null) {
                    productEntityList.add(productEntity);
                }
            }
        }
        return productEntityList;
    }
}
