package com.fernandocejas.android10.order.domain.repository;

import com.fernandocejas.android10.order.domain.Message;

import io.reactivex.Observable;

/**
 *
 *
 */

public interface MessageRepository {

    Observable<Message> message_send(String token, String order, String receiver, String message, String type);
}
