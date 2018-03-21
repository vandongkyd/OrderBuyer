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

import com.fernandocejas.android10.order.domain.Message;
import com.fernandocejas.android10.restrofit.enity.MessageEntity;
import com.fernandocejas.android10.restrofit.enity.MessageEntityResponse;

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
public class MessageEntityDataMapper {

    @Inject
    MessageEntityDataMapper() {
    }

    public Message transform(MessageEntity messageEntity) {
        Message message = null;
        if (messageEntity != null) {

            message = new Message();
            message.setDatetime(messageEntity.getDatetime());
            message.setMessage(messageEntity.getMessage());
            message.setOrder(messageEntity.getOrder());
            message.setReceiver(messageEntity.getReceiver());
            message.setSender(messageEntity.getSender());
        }
        return message;
    }

    public List<Message> transform(Collection<MessageEntity> messageEntityCollection) {
        final List<Message> messageList = new ArrayList<>();
        for (MessageEntity messageEntity : messageEntityCollection) {
            final Message message = transform(messageEntity);
            if (message != null) {
                messageList.add(message);
            }
        }
        return messageList;
    }

    public Message transform(MessageEntityResponse messageEntityResponse) throws Exception {
        Message message = null;
        if (messageEntityResponse != null) {
            if (messageEntityResponse.status() == false) {
                throw new Exception(messageEntityResponse.message());
            }
            message = transform(messageEntityResponse.data());
        }
        return message;
    }

}
