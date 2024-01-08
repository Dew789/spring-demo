package com.example.demo.io.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

// https://blog.csdn.net/weixin_50272584/article/details/130638515
@Configuration
public class EventLoopGroupConfig {

    @Value("${netty.threads.boss}")
    private int bossNum;

    @Value("${netty.threads.worker}")
    private int workerNum;

    @Value("${netty.threads.business.num}")
    private int businessNum;

    @Value("${netty.threads.business.max-pending}")
    private int maxPending;


    /**
     * TCP连接处理
     * @return
     */
    @Bean(name = "bossGroup")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossNum);
    }

    /**
     * Socket数据读写
     * @return
     */
    @Bean(name = "workerGroup")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerNum);
    }

    /**
     * Handler业务处理
     * @return
     */
    @Bean(name = "businessGroup")
    public EventExecutorGroup businessGroup() {
        return new DefaultEventExecutorGroup(businessNum,new BusinessThreadFactory(),maxPending, RejectedExecutionHandlers.reject());
    }

    static class BusinessThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        BusinessThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "business-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()){
                t.setDaemon(false);
            }

            if (t.getPriority() != Thread.NORM_PRIORITY){
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}
