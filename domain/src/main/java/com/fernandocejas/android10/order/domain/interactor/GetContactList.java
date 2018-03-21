package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Contact;

import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.order.domain.repository.ContactRepository;
import com.fernandocejas.android10.order.domain.repository.TickerRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vandongluong on 3/9/18.
 */

public class GetContactList extends UseCase<List<Contact>,GetContactList.Params> {
    private final ContactRepository contactRepository;

    @Inject
    public GetContactList(ContactRepository contactRepository,ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor,postExecutionThread);
        this.contactRepository = contactRepository;
    }

    @Override
    protected Observable<List<Contact>> buildUseCaseObservable(GetContactList.Params params) {
        Preconditions.checkNotNull(params);
        return this.contactRepository.contact(params.access_token);
    }
    public static final class Params {

        private final String access_token;

        private Params(String access_token) {
            this.access_token = access_token;
        }

        public static GetContactList.Params forContact(String access_token) {
            return new GetContactList.Params(access_token);
        }
    }
}
