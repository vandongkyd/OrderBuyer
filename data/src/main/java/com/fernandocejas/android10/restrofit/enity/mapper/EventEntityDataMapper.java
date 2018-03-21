/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.restrofit.enity.mapper;

import com.fernandocejas.android10.order.domain.Event;
import com.fernandocejas.android10.restrofit.enity.EventEntity;
import com.fernandocejas.android10.restrofit.enity.EventListEntityResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 *
 */
@Singleton
public class EventEntityDataMapper {

    @Inject
    EventEntityDataMapper() {
    }

    public Event transform(EventEntity eventEntity) {
        Event event = null;
        if (eventEntity != null) {
            event = new Event();
            event.setId(eventEntity.id());
            event.setImage(eventEntity.image());
            event.setLink(eventEntity.link());
            event.setDescription(eventEntity.description());
            event.setActive(eventEntity.active());
            event.setCreated_at(eventEntity.created_at());
            event.setUpdated_at(eventEntity.updated_at());
        }
        return event;
    }

    public List<Event> transform(Collection<EventEntity> eventEntityCollection) {
        final List<Event> eventList = new ArrayList<>();
        for (EventEntity eventEntity : eventEntityCollection) {
            final Event event = transform(eventEntity);
            if (event != null) {
                eventList.add(event);
            }
        }
        return eventList;
    }

    public List<Event> transform(EventListEntityResponse eventListEntityResponse) throws Exception {
        List<Event> eventList = null;
        if (eventListEntityResponse != null) {
            if (eventListEntityResponse.status() == false) {
                throw new Exception(eventListEntityResponse.message());
            }
            eventList = transform(eventListEntityResponse.data());
        }
        return eventList;
    }
}
