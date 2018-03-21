package com.fernandocejas.android10.order.domain.repository_buyer;


import com.fernandocejas.android10.order.domain.Model_buyer.Address_Buyer;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/14/18.
 */

public interface AddressRepository_Buyer {
    Observable<List<Address_Buyer>> addresses(String token);

    Observable<Address_Buyer> addAddresses(String token,
                                     String address,
                                     String city,
                                     String postcode,
                                     String country,
                                     String country_code);
}
