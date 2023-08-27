package com.example.demo.io.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

/**
 * @author chaoye4
 * @date 2022/8/18
 */
@Component
@Slf4j
public class WebSocketEventListener {

    @EventListener
    public void handleSubscribe(final SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("有新客户端订阅了,seesionId = [{}], des= [{}] ", getWebsocketSessionId(headerAccessor),headers.getDestination());

    }

    @EventListener
    public void handleUnsubscribe(final SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("有新客户端取消订阅了,seesionId = [{}]", getWebsocketSessionId(headerAccessor));

    }

    @EventListener
    public void onSessionDisConnected(final SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("有新客户端断开连接了,seesionId = [{}]", getWebsocketSessionId(headerAccessor));

    }


    private String getWebsocketSessionId(StompHeaderAccessor headerAccessor) {
        return headerAccessor.getHeader(SimpMessageHeaderAccessor.SESSION_ID_HEADER).toString();
    }


}