package com.evgeek.skeletonapplication.data.events;

/**
 * Created by evgeek on 10/11/17.
 */

public class MessageEvent extends BaseEvent {

    public final String message;

    public MessageEvent(String message) {
        this.message = message;
    }
}
