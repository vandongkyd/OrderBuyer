package com.fernandocejas.android10.order.domain.repository_buyer;

import com.fernandocejas.android10.order.domain.Country;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/14/18.
 */

public interface CountryRepository_Buyer {
    Observable<List<Country>> countries();
}
