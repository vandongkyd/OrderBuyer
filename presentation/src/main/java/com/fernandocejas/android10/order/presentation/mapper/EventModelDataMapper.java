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
package com.fernandocejas.android10.order.presentation.mapper;

import com.fernandocejas.android10.order.domain.Event;
import com.fernandocejas.android10.order.presentation.model.EventModel;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class EventModelDataMapper {

    @Inject
    public EventModelDataMapper() {
    }

    public EventModel transform(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final EventModel eventModel = new EventModel();
        eventModel.setId(event.getId());
        eventModel.setImage(event.getImage());
        eventModel.setLink(event.getLink());
        eventModel.setDescription(event.getDescription());
        eventModel.setActive(event.getActive());
        eventModel.setCreated_at(event.getCreated_at());
        eventModel.setUpdated_at(event.getUpdated_at());
        return eventModel;
    }

    public Collection<EventModel> transform(Collection<Event> eventCollection) {
        Collection<EventModel> eventModelCollection;

        if (eventCollection != null && !eventCollection.isEmpty()) {
            eventModelCollection = new ArrayList<>();
            for (Event event : eventCollection) {
                eventModelCollection.add(transform(event));
            }
        } else {
            eventModelCollection = Collections.emptyList();
        }

        return eventModelCollection;
    }
}

