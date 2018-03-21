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

import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.restrofit.enity.ProductEntity;
import com.fernandocejas.android10.restrofit.enity.ProductEntityParseResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link ProductEntity} (in the data layer) to {@link Product} in the
 * domain layer.
 */
@Singleton
public class ProductEntityDataMapper {

    private final ImageEntityDataMapper imageEntityDataMapper;
    private final CountryEntityDataMapper countryEntityDataMapper;

    @Inject
    ProductEntityDataMapper(ImageEntityDataMapper imageEntityDataMapper,
                            CountryEntityDataMapper countryEntityDataMapper) {
        this.imageEntityDataMapper = imageEntityDataMapper;
        this.countryEntityDataMapper = countryEntityDataMapper;
    }

    /**
     * Transform a {@link ProductEntity} into an {@link Product}.
     *
     * @param productEntity Object to be transformed.
     * @return {@link Product} if valid {@link ProductEntity} otherwise null.
     */
    public Product transform(ProductEntity productEntity) {
        Product product = null;
        if (productEntity != null) {
            product = new Product();
            product.setId(productEntity.getId());
            product.setOrderid(productEntity.getOrderid());
            product.setLink(productEntity.getLink());
            product.setName(productEntity.getName());
            product.setCode(productEntity.getCode());
            product.setDescription(productEntity.getDescription());
            product.setPrice(productEntity.getPrice());
            product.setQuantity(productEntity.getQuantity());
            product.setWeight(productEntity.getWeight());
            product.setImages(imageEntityDataMapper.transform(productEntity.getImages()));
            product.setCurrency(productEntity.getCurrency());
            product.setCountry(countryEntityDataMapper.transform(productEntity.getCountry()));
            product.setWeight_unit(productEntity.getWeight_unit());
        }
        return product;
    }

    /**
     * Transform a List of {@link ProductEntity} into a Collection of {@link Product}.
     *
     * @param productEntityCollection Object Collection to be transformed.
     * @return {@link Product} if valid {@link ProductEntity} otherwise null.
     */
    public List<Product> transform(Collection<ProductEntity> productEntityCollection) {
        final List<Product> productList = new ArrayList<>();
        for (ProductEntity productEntity : productEntityCollection) {
            final Product product = transform(productEntity);
            if (product != null) {
                productList.add(product);
            }
        }
        return productList;
    }

    public Product transform(ProductEntityParseResponse productEntityParseResponse) throws Exception {
        Product product = null;
        if (productEntityParseResponse != null) {
            if (productEntityParseResponse.status() == false) {
                throw new Exception(productEntityParseResponse.message());
            }
            product = transform(productEntityParseResponse.data());
        }
        return product;
    }

    public ProductEntity transform(Product product) {
        ProductEntity productEntity = null;
        if (product != null) {
            productEntity = new ProductEntity();
            productEntity.setId(product.getId());
            productEntity.setOrderid(product.getOrderid());
            productEntity.setLink(product.getLink());
            productEntity.setName(product.getName());
            productEntity.setCode(product.getCode());
            productEntity.setDescription(product.getDescription());
            productEntity.setPrice(product.getPrice());
            productEntity.setQuantity(product.getQuantity());
            productEntity.setWeight(product.getWeight());
            productEntity.setImages(imageEntityDataMapper.transformEntity(product.getImages()));
            productEntity.setCurrency(product.getCurrency());
            productEntity.setCountry(countryEntityDataMapper.transform(product.getCountry()));
            productEntity.setWeight_unit(product.getWeight_unit());
        }
        return productEntity;
    }

    public List<ProductEntity> transformToEntity(Collection<Product> productCollection) {
        final List<ProductEntity> productEntityList = new ArrayList<>();
        if (productCollection != null && !productCollection.isEmpty()) {
            for (Product product : productCollection) {
                final ProductEntity productEntity = transform(product);
                if (productEntity != null) {
                    productEntityList.add(productEntity);
                }
            }
        }
        return productEntityList;
    }
}
