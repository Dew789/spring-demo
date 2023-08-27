package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

@Slf4j
public class MyStompSessionHandler implements StompSessionHandler {

    @Override
    public void afterConnected(
            StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/audio/change/2", this);
        log.info("Connect success!!!");
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
//        Message msg = (Message) payload;
//        System.out.println("Received : " + msg.getText()+ " from : " + msg.getFrom());
        log.info("Get Message {}!!", new String((byte[]) payload));
    }

    @Override
    public void handleException(StompSession session, @Nullable StompCommand command,
                                StompHeaders headers, byte[] var4, Throwable t) {
        log.info("handleException!!, session {}, command {}, headers {} ",
                session, command, headers, t);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable t) {
        log.info("handleTransportError!!, session {} ", session, t);
    }

    @NotNull
    @Override
    public Type getPayloadType(StompHeaders headers) {
        System.out.println("get stomp headers!" + headers);

        return Object.class;
    }

}
