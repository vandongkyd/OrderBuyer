package com.fernandocejas.android10.order.presentation.mapper;

import com.fernandocejas.android10.order.domain.Contact;
import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.order.presentation.model.ContactModel;
import com.fernandocejas.android10.order.presentation.model.TickerModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by vandongluong on 3/9/18.
 */
@PerActivity
public class ContactModelDataMapper {
    @Inject
    public ContactModelDataMapper() {
    }
    public ContactModel transform(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final ContactModel contactModel = new ContactModel();
        contactModel.setId(contact.getId());
        contactModel.setName(contact.getName());
        contactModel.setEmail(contact.getEmail());
        contactModel.setAddress(contact.getAddress());
        contactModel.setGender(contact.getGender());
        contactModel.setMobile(contact.getMobile());
        contactModel.setHome(contact.getHome());
        contactModel.setOffice(contact.getOffice());
        return contactModel;
    }
    public Collection<ContactModel> transform(Collection<Contact> contacts) {
        Collection<ContactModel> contactModels;

        if (contacts != null && !contacts.isEmpty()) {
            contactModels = new ArrayList<>();
            for (Contact contact : contacts) {
                contactModels.add(transform(contact));
            }
        } else {
            contactModels = Collections.emptyList();
        }

        return contactModels;
    }
    public Contact transform(ContactModel contactModel) {
        if (contactModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final Contact contact = new Contact();
        contact.setId(contactModel.getId());
        contact.setName(contactModel.getName());
        contact.setEmail(contactModel.getEmail());
        contact.setAddress(contactModel.getAddress());
        contact.setGender(contactModel.getGender());
        contact.setMobile(contactModel.getMobile());
        contact.setHome(contactModel.getHome());
        contact.setOffice(contactModel.getOffice());
        return contact;
    }
    public Collection<Contact> transformToDomain(Collection<ContactModel> contactModels) {
        Collection<Contact> contacts;

        if (contactModels != null && !contactModels.isEmpty()) {
            contacts = new ArrayList<>();
            for (ContactModel contactModel : contactModels) {
                contacts.add(transform(contactModel));
            }
        } else {
            contacts = Collections.emptyList();
        }

        return contacts;
    }
}
