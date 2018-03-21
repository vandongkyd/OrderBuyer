package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Country;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 *
 */

public interface CountryRepository {

    Observable<List<Country>> countries();
}
