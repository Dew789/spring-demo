package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Runner implements ApplicationRunner {

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void run(ApplicationArguments args){
        webSocketService.sendAudioLoop();
        log.info("音频发送服务启动！");
    }

}
