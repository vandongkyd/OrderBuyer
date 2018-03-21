package com.fernandocejas.android10.restrofit.enity.mapper;

import com.fernandocejas.android10.order.domain.Contact;
import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.restrofit.enity.ContactEnity;
import com.fernandocejas.android10.restrofit.enity.ContactEnityResponse;
import com.fernandocejas.android10.restrofit.enity.TickerEnity;
import com.fernandocejas.android10.restrofit.enity.TickerEnityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by vandongluong on 3/9/18.
 */

@Singleton
public class ContactEntityDataMapper {
    @Inject
    ContactEntityDataMapper() {
    }
    public Contact transform(ContactEnity contactEnity) {
        Contact contact = null;
        if (contactEnity != null) {
            contact = new Contact();
            contact.setId(contactEnity.getId());
            contact.setName(contactEnity.getName());
            contact.setEmail(contactEnity.getEmail());
            contact.setAddress(contactEnity.getAddress());
            contact.setGender(contactEnity.getGender());
            contact.setMobile(contactEnity.getMobile());
            contact.setHome(contactEnity.getHome());
            contact.setOffice(contactEnity.getOffice());


        }
        return contact;
    }
    public ContactEnity transform(Contact contact) {
        ContactEnity contactEnity = null;
        if (contact != null) {
            contactEnity = new ContactEnity();
            contactEnity.setId(contact.getId());
            contactEnity.setName(contact.getName());
            contactEnity.setEmail(contact.getEmail());
            contactEnity.setAddress(contact.getAddress());
            contactEnity.setGender(contact.getGender());
            contactEnity.setMobile(contact.getMobile());
            contactEnity.setHome(contact.getHome());
            contactEnity.setOffice(contact.getOffice());
        }
        return contactEnity;
    }
    public List<Contact> transform(Collection<ContactEnity> contactEnities) {
        final List<Contact> contacts = new ArrayList<>();
        for (ContactEnity contactEnity : contactEnities) {
            final Contact contact = transform(contactEnity);
            if (contact != null) {
                contacts.add(contact);
            }
        }
        return contacts;
    }
    public List<ContactEnity> transformToEntity(Collection<Contact> contacts) {
        final List<ContactEnity> contactEnities = new ArrayList<>();
        for (Contact contact : contacts) {
            final ContactEnity contactEnity = transform(contact);
            if (contactEnity != null) {
                contactEnities.add(contactEnity);
            }
        }
        return contactEnities;
    }
//    public List<Ticker> transform(TickerListEnityResponse tickerListEnityResponse) throws Exception {
//        List<Ticker> tickerList = null;
//        if (tickerListEnityResponse != null) {
////            if (tickerListEnityResponse.status() == false) {
////                throw new Exception(tickerListEnityResponse.message());
////            }
//           // tickerList = transform(tickerListEnityResponse.);
//        }
//        return tickerList;
//    }

    public Contact transform(ContactEnityResponse contactEnityResponse) throws Exception {
        Contact contact = null;
        if (contactEnityResponse != null) {
            if (contactEnityResponse.status() == false) {
                throw new Exception(contactEnityResponse.message());
            }
            contact = transform(contactEnityResponse.data());
        }
        return contact;
    }
}
