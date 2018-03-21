package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Contact;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/9/18.
 */

public interface ContactRepository {
    Observable<List<Contact>> contact(String token);
}
