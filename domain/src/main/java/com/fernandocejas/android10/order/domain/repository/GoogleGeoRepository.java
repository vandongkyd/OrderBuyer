package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.GoogleGeo;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 *
 */

public interface GoogleGeoRepository {

    Observable<List<GoogleGeo>> geocode(String lat, String lng);
}
