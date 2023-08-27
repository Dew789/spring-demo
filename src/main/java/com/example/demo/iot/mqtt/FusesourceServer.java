package com.example.demo.iot.mqtt;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;

public class FusesourceServer {
    public static void blockingIO() throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost("tcp://10.4.111.19:1883");
        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();
        Topic[] topics = new Topic[]{new Topic("foo", QoS.AT_LEAST_ONCE)};
        connection.subscribe(topics);

        while(true) {
            Message message = connection.receive();
            System.out.println(message);
            byte[] payload = message.getPayload();
            message.ack();
        }
    }

    public static void callback() throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setUserName("admin");
        mqtt.setPassword("public");
        mqtt.setHost("tcp://10.4.111.19:1883");
        final CallbackConnection connection = mqtt.callbackConnection();
        connection.listener(new Listener() {
            public void onDisconnected() {
            }

            public void onConnected() {
            }

            public void onPublish(UTF8Buffer topic, Buffer payload, Runnable ack) {
                System.out.println(topic);
                ack.run();
            }

            public void onFailure(Throwable value) {
                connection.disconnect((Callback)null);
            }
        });
        connection.connect(new Callback<Void>() {
            public void onFailure(Throwable value) {
                System.out.println("onFailure");
            }

            public void onSuccess(Void v) {
                Topic[] topics = new Topic[]{new Topic("$SYS/brokers/emqx@127.0.0.1/clients/+/connected", QoS.AT_LEAST_ONCE)};
                connection.subscribe(topics, new Callback<byte[]>() {
                    public void onSuccess(byte[] qoses) {
                        System.out.println("Subscrube to a topic onSuccess");
                    }

                    public void onFailure(Throwable value) {
                        connection.disconnect((Callback)null);
                    }
                });
            }
        });
    }

    public static void main(String[] args) throws Exception {
        callback();
        while (true){}
    }
}
