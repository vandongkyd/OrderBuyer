package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.SettingByCountry;

import io.reactivex.Observable;

/**
 *
 *
 */

public interface SettingRepository {

    Observable<SettingByCountry> setting(String token, String country);
}
