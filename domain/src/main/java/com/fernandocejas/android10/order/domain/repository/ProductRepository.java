package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Product;

import io.reactivex.Observable;

/**
 *
 *
 */

public interface ProductRepository {

    Observable<Product> parse(String token, String url);
}
