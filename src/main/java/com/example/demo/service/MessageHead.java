package com.example.demo.service;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageHead {

    private int headData = 0xFCFCFCFC;//协议开始标志

    private int length;//包的长度

    private int dataLength;//音频数据长度

    private String token;

    private int crc;
    //采样率
    private int sampleRate;
    //位深
    private int bits;
    //MAC 地址，唯一 ID
    private String deviceMacId;
    //通道号
    private String chNO;
    /**
     * 包号
     */
    private long packetNum;
}
