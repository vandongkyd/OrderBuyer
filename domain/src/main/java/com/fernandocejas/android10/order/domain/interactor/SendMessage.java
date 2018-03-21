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
package com.fernandocejas.android10.order.domain.interactor;

import com.fernandocejas.android10.order.domain.Message;
import com.fernandocejas.android10.order.domain.repository.MessageRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * register user.
 */
public class SendMessage extends UseCase<Message, SendMessage.Params> {

    private final MessageRepository messageRepository;

    @Inject
    SendMessage(MessageRepository messageRepository, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.messageRepository = messageRepository;
    }

    @Override
    protected Observable<Message> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.messageRepository.message_send(
                params.token,
                params.order,
                params.receiver,
                params.message,
                params.type);
    }

    public static final class Params {

        final String token;
        final String order;
        final String receiver;
        final String message;
        final String type;

        public Params(String token, String order, String receiver, String message, String type) {
            this.token = token;
            this.order = order;
            this.receiver = receiver;
            this.message = message;
            this.type = type;
        }

        public static SendMessage.Params forSendMessage(final String token,
                                                        final String order,
                                                        final String receiver,
                                                        final String message,
                                                        final String type) {
            return new SendMessage.Params(token, order, receiver, message, type);
        }
    }
}
