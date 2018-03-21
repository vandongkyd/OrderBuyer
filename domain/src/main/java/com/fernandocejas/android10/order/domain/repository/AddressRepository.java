package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Address;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 *
 */

public interface AddressRepository {

    Observable<List<Address>> addresses(String token);

    Observable<Address> addAddresses(String token,
                                     String address,
                                     String city,
                                     String postcode,
                                     String country,
                                     String country_code,
                                     String lat,
                                     String lng);

}
