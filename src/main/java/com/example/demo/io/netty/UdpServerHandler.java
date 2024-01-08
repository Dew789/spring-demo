package com.example.demo.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@ChannelHandler.Sharable
@Component
@Slf4j
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Autowired
    @Qualifier("businessGroup")
    private EventExecutorGroup businessGroup;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String content = packet.content().toString(StandardCharsets.UTF_8);

        log.info("UDP服务端接收到消息：{}", content);


        ByteBuf buf = Unpooled.copiedBuffer("UDP已经接收到消息：".getBytes(StandardCharsets.UTF_8));

        businessGroup.execute(() -> {
            try {
                ctx.writeAndFlush(new DatagramPacket(buf, packet.sender()));
            } catch (Throwable e) {
                log.info("UDP数据接收处理出错{}", e);
            }
        });
    }

}

