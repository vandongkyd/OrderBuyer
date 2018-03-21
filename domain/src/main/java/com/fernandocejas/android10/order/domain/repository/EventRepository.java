package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Country;
import com.fernandocejas.android10.order.domain.Event;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 *
 */

public interface EventRepository {

    Observable<List<Event>> events(String token);
}
