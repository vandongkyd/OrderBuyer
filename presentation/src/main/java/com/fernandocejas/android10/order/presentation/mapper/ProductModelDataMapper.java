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

import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Product} (in the domain layer) to {@link ProductModel} in the
 * presentation layer.
 */
@PerActivity
public class ProductModelDataMapper {

    private final ImageModelDataMapper imageModelDataMapper;
    private final CountryModelDataMapper countryModelDataMapper;

    @Inject
    public ProductModelDataMapper(ImageModelDataMapper imageModelDataMapper,
                                  CountryModelDataMapper countryModelDataMapper) {
        this.imageModelDataMapper = imageModelDataMapper;
        this.countryModelDataMapper = countryModelDataMapper;
    }

    /**
     * Transform a {@link Product} into an {@link ProductModel}.
     *
     * @param product Object to be transformed.
     * @return {@link ProductModel}.
     */
    public ProductModel transform(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setOrderid(product.getOrderid());
        productModel.setLink(product.getLink());
        productModel.setName(product.getName());
        productModel.setCode(product.getCode());
        productModel.setDescription(product.getDescription());
        productModel.setPrice(product.getPrice());
        productModel.setQuantity(product.getQuantity());
        productModel.setWeight(product.getWeight());
        productModel.setImages(imageModelDataMapper.transform(product.getImages()));
        productModel.setCurrency(product.getCurrency());
        if (product.getCountry() != null) {
            productModel.setCountry(countryModelDataMapper.transform(product.getCountry()));
        }
        productModel.setWeight_unit(product.getWeight_unit());
        return productModel;
    }

    /**
     * Transform a Collection of {@link Product} into a Collection of {@link ProductModel}.
     *
     * @param productsCollection Objects to be transformed.
     * @return List of {@link ProductModel}.
     */
    public Collection<ProductModel> transform(Collection<Product> productsCollection) {
        Collection<ProductModel> productModelsCollection;

        if (productsCollection != null && !productsCollection.isEmpty()) {
            productModelsCollection = new ArrayList<>();
            for (Product Product : productsCollection) {
                productModelsCollection.add(transform(Product));
            }
        } else {
            productModelsCollection = Collections.emptyList();
        }

        return productModelsCollection;
    }

    public Product transform(ProductModel productModel) {
        if (productModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Product product = new Product();
        product.setId(productModel.getId());
        product.setOrderid(productModel.getOrderid());
        product.setLink(productModel.getLink());
        product.setName(productModel.getName());
        product.setCode(productModel.getCode());
        product.setDescription(productModel.getDescription());
        product.setPrice(productModel.getPrice());
        product.setQuantity(productModel.getQuantity());
        product.setWeight(productModel.getWeight());
        product.setImages(imageModelDataMapper.transformToDomain(productModel.getImages()));
        product.setCurrency(productModel.getCurrency());
        if (productModel.getCountry() != null) {
            product.setCountry(countryModelDataMapper.transform(productModel.getCountry()));
        }
        product.setWeight_unit(productModel.getWeight_unit());
        return product;
    }
}

