package com.example.demo.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.SystemPropertyUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Slf4j
@Configuration
public class NettyUdpServer implements ApplicationListener<ApplicationStartedEvent> {

    @Value("${netty.udp.port}")
    private int port;

    @Resource
    private UdpServerHandler udpServerHandler;

    private EventLoopGroup group = null;

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {

        try {
            Bootstrap b = new Bootstrap();
            String osName= SystemPropertyUtil.get("os.name").toLowerCase();
            if("linux".equals(osName)) {
                group = new EpollEventLoopGroup();
                b.group(group)
                        .channel(EpollDatagramChannel.class);
            }else {
                group = new NioEventLoopGroup();
                b.group(group)
                        .channel(NioDatagramChannel.class);
            }
            //广播
            b.option(ChannelOption.SO_BROADCAST, true)
                    //接收缓存区  10M
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024 * 10 )
                    //发送缓存区  10M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024 * 10 )
                    .handler(udpServerHandler);

            ChannelFuture channelFuture = b.bind(port).sync();
            if (channelFuture.isSuccess()) {
                log.info("UDP服务启动完毕,port={}", port);
            }

        } catch (InterruptedException e) {
            log.info("UDP服务启动失败", e);
        }

    }

    /**
     * 销毁资源
     */
    @PreDestroy
    public void destroy() {
        if(group!=null) {
            group.shutdownGracefully();
        }
        log.info("UDP服务关闭成功");
    }
}
