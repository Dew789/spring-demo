package com.example.demo.dao;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "iais.fileservie")
//@ConditionalOnBean(User.class)
public class BeanTest implements InitializingBean, DisposableBean, ApplicationListener<ContextRefreshedEvent> {

    private final OssFileServiceProps ossFileServiceProps = new OssFileServiceProps();

    private final LocalFileSystemProps localFileSystemProps = new LocalFileSystemProps();

    private String season = "summer";

    public List<String> strategy = new ArrayList<>();

    public BeanTest() {
        System.out.println("!!!init" + strategy);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("!!!afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("!!!destory");
    }

    @PostConstruct
    public void post() {
        System.out.println("!!!PostConstruct");
    }

    @PreDestroy
    public void destory() {
        System.out.println("!!!PreDestroy");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        System.out.println("!!!onApplicationEvent");
    }

    @Data
    public static class OssFileServiceProps {
        /**
         * 存储池bucket
         */
        private String bucketNamePrefix;

        /**
         * key
         */
        private String accessKey;

        /**
         * secretKey
         */
        private String secretKey;

        /**
         * 连接超时时间，单位ms
         */
        private Integer timeout;

        /**
         * 服务器域名
         */
        private String hostName;

        /**
         * 跨域配置
         */
        private List<String> allowedOrigins = new ArrayList<>();
    }


    @Data
    public static class LocalFileSystemProps {
        /**
         * 音频存储目录
         */
        private String storagePath = "/data/base/static/iais-service-platform";
    }


}
