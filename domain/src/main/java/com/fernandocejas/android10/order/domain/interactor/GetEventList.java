package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Event;
import com.fernandocejas.android10.order.domain.repository.EventRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 */

public class GetEventList extends UseCase<List<Event>, GetEventList.Params> {

    private final EventRepository eventRepository;

    @Inject
    GetEventList(EventRepository eventRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.eventRepository = eventRepository;
    }

    @Override
    protected Observable<List<Event>> buildUseCaseObservable(GetEventList.Params params) {
        Preconditions.checkNotNull(params);
        return this.eventRepository.events(params.token);
    }

    public static final class Params {

        private final String token;

        private Params(String token) {
            this.token = token;
        }

        public static GetEventList.Params forEvent(String token) {
            return new GetEventList.Params(token);
        }
    }
}

