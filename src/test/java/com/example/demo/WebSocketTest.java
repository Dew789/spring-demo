package com.example.demo;

import org.junit.Test;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.util.Scanner;


public class WebSocketTest {


    @Test
    public void testStompReceive() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxBinaryMessageBufferSize(20*1024*1024);
        WebSocketClient client = new StandardWebSocketClient(container);

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setInboundMessageSizeLimit(Integer.MAX_VALUE);
//        stompClient.setMessageConverter(new ByteArrayMessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        String url = "ws://192.168.1.109:8082/ws";
        stompClient.connect(url, sessionHandler);

        new Scanner(System.in).nextLine(); // Don't close immediately.
    }

}
