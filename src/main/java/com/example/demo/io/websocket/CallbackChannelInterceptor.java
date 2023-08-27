package com.example.demo.io.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class CallbackChannelInterceptor implements ChannelInterceptor  {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return message;
    }

}
