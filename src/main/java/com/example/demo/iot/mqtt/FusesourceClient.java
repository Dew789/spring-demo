package com.example.demo.iot.mqtt;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

public class FusesourceClient {

    public static void main(String[] args) throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost("tcp://10.4.111.19:1883");
        mqtt.setWillTopic("");

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        connection.publish("terminal", "Hello".getBytes(), QoS.AT_MOST_ONCE, true);

        connection.disconnect();
    }
}
