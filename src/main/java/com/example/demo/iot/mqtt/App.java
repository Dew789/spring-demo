package com.example.demo.iot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class App {

//    public static String subTopic = "$SYS/brokers/+/clients/+/connected";
    public static String subTopic = "v1/devices/telemetry/audio/#";
    public static String pubTopic = "v1/devices/telemetry/audio/1/hz00000001/dsadsad";
    public static String content = "Hello World";
    public static int qos = 2;
    public static String broker = "tcp://10.4.110.5:1883";
    public static String clientId = "test106";  // 决定是否接收到消息
    public static MemoryPersistence persistence = new MemoryPersistence();

    public static  MqttClient client;

    public static class OnMessageCallback implements MqttCallback {
        public void connectionLost(Throwable cause) {
            // 连接丢失后，一般在这里面进行重连
            try {

//                client.reconnect();

            } catch (Exception e) {}
            finally {
                System.out.println("连接断开，可以做重连");
            }
        }

        public void messageArrived(String topic, MqttMessage message) throws Exception {

//            System.out.println(client.getPendingDeliveryTokens());

            // subscribe后得到的消息会执行到这里面
            System.out.println("接收消息主题:" + topic);
            System.out.println("接收消息Qos:" + message.getQos());
            System.out.println("接收消息内容:" + new String(message.getPayload()));
        }

        public void deliveryComplete(IMqttDeliveryToken token) {
            System.out.println("deliveryComplete---------" + token.isComplete());
        }
    }

    public static void main(String[] args) {

        try {
            client = new MqttClient(broker, clientId, persistence);
            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName("iflytek");
//            connOpts.setPassword("hziflytek@202066".toCharArray());
//            connOpts.setAutomaticReconnect(true);
            // 保留会话
            connOpts.setCleanSession(false);

            // 设置回调
            client.setCallback(new OnMessageCallback());

            // 建立连接
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);

            System.out.println("Connected");
            System.out.println("Publishing message: " + content);

            // 订阅
            int[] qos = {2, 2, 2};
            String[] topics = {"v1/devices/telemetry/switch/test106",
                    "v1/devices/telemetry/samp/test106",
                    "v1/devices/execute/command"};

            client.subscribe(
                    topics,
                    qos);

            // 消息发布所需参数
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(2);
            client.publish(pubTopic, message);
            System.out.println("Message published");

            while (true) {}

//            client.disconnect();
//            System.out.println("Disconnected");
//            client.close();
//            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

        }
    }

}
