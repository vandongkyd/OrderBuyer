package com.fernandocejas.android10.restrofit.repository;

import com.fernandocejas.android10.order.domain.Contact;
import com.fernandocejas.android10.order.domain.repository.ContactRepository;
import com.fernandocejas.android10.restrofit.enity.mapper.ContactEntityDataMapper;
import com.fernandocejas.android10.restrofit.net.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/9/18.
 */
@Singleton
public class ContactDataRepository implements ContactRepository {
    private final RetrofitHelper retrofitHelper;
    private final ContactEntityDataMapper contactEntityDataMapper;
    @Inject
    public ContactDataRepository(RetrofitHelper retrofitHelper,ContactEntityDataMapper contactEntityDataMapper) {
        this.retrofitHelper = retrofitHelper;
        this.contactEntityDataMapper = contactEntityDataMapper;
    }


    @Override
    public Observable<List<Contact>> contact(String token) {
        return retrofitHelper
                .getRestApiService()
                .hellocontact("Bearer " + token)
                .map(this.contactEntityDataMapper:: transform);
    }


}
