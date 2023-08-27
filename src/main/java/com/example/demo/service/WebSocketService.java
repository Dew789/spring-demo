package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//@Service
@Slf4j
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final String channel = "";

    private static final long wsTime = 1;

    private static final List<String> fileList = new ArrayList<>(
            Arrays.asList("/1.pcm", "/2.pcm", "/3.pcm", "/4.pcm", "/5.pcm"));

    private static final List<String> devCodeList = new ArrayList<>(
            Arrays.asList("a09090900001:1", "a09090900002:1", "a09090900003:1", "a09090900004:1", "a09090900005:1"));

    private final static ExecutorService pcmThreadPool = Executors.newFixedThreadPool(5);


    private void sendAudio(String fileName, String devCode) {
        MessageHead head = new MessageHead();
        head.setSampleRate(48000);
        head.setBits(32);
        head.setDeviceMacId(devCode);
        head.setChNO(channel);
        int wsByteNum = (int) (Math.floor(head.getSampleRate() * head.getBits() * wsTime / 8 / 2048) * 2048);
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "r");
            byte[] data = new byte[2048];

            while (true) {
                int readBytes = file.read(data);
                if (readBytes == -1) {
                    log.info("重置文件{}", fileName);
                    file.seek(0);
                }
                ByteBuffer wsByteBuffer = putByteToWsByteBuffer(head, channel, data, wsByteNum);;

                if (wsByteBuffer.limit() == wsByteBuffer.position()) {
                    try {
                        // 推送实时音频
                        simpMessagingTemplate.convertAndSend("/topic/client/tcp/data" + "/" + devCode, wsByteBuffer.array());

//                        log.info("接收到该设备" + wsTime + "秒音频,并发送至websocket,设备编号{}", devCode);
                        WsBufferCache.remove(head.getDeviceMacId() + "_" + channel);
                        Thread.sleep(wsTime * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //删除缓存
                        wsByteBuffer.clear();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void sendAudioLoop() {
        for (int i = 0; i < 5; i++) {
            String fileName = fileList.get(i);
            String devCode = devCodeList.get(i);
            log.info("文件{}开始发送到{}", fileName, devCode);

            pcmThreadPool.submit(() -> {
                this.sendAudio(fileName, devCode);
            });
        }

    }


    private ByteBuffer putByteToWsByteBuffer(MessageHead head, String channel, byte[] bytes, int wsSize) {
        ByteBuffer byteBuffer = WsBufferCache.get(head.getDeviceMacId() + "_" + channel);
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(wsSize);
            byteBuffer.put(bytes);
            WsBufferCache.put(head.getDeviceMacId() + "_" + channel, byteBuffer);
        } else {
            if (byteBuffer.limit() > byteBuffer.position()) {
                byteBuffer.put(bytes);
            }
        }
        return byteBuffer;
    }


}
