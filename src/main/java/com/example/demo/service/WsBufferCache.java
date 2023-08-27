package com.example.demo.service;

import java.nio.ByteBuffer;
import java.util.concurrent.*;

public class WsBufferCache {
    /**
     * 键值对集合
     */
    private final static ConcurrentHashMap<String, Entity> map = new ConcurrentHashMap<String, Entity>();
    /**
     * 定时器线程池，用于清除过期缓存
     */
    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    /**
     * 添加缓存
     *
     * @param key  键
     * @param data 值
     */
    public synchronized static void put(String key, ByteBuffer data) {
        WsBufferCache.put(key, data, 0);
    }

    /**
     * 添加缓存
     *
     * @param key    键
     * @param data   值
     * @param expire 过期时间，单位：毫秒， 0表示无限长
     */
    public synchronized static void put(String key, ByteBuffer data, long expire) {
        //清除原键值对
        WsBufferCache.remove(key);
        //设置过期时间
        if (expire > 0) {
            Future future = executor.schedule(new Runnable() {
                @Override
                public void run() {
                    //过期后清除该键值对
                    synchronized (WsBufferCache.class) {
                        map.remove(key);
                    }
                }
            }, expire, TimeUnit.MILLISECONDS);
            map.put(key, new Entity(data, future));
        } else {
            //不设置过期时间
            map.put(key, new Entity(data, null));
        }
    }

    /**
     * 读取缓存
     *
     * @param key 键
     * @return
     */
    public synchronized static ByteBuffer get(String key) {
        Entity entity = map.get(key);
        return entity == null ? null : entity.value;
    }

    /**
     * 读取缓存
     *
     * @param key 键
     * @return
     */
    public synchronized static Entity getEntity(String key) {
        Entity entity = map.get(key);
        return entity == null ? null : entity;
    }

    /**
     * 清除缓存
     *
     * @param key 键
     * @return
     */
    public synchronized static ByteBuffer remove(String key) {
        //清除原缓存数据
        Entity entity = map.remove(key);
        if (entity == null) {
            return null;
        }
        //清除原键值对定时器
        if (entity.future != null) {
            entity.future.cancel(true);
        }
        return entity.value;
    }

    /**
     * 查询当前缓存的键值对数量
     *
     * @return
     */
    public synchronized static int size() {
        return map.size();
    }

    /**
     * 缓存实体类
     */
    public static class Entity {
        /**
         * 键值对的value
         */
        public ByteBuffer value;
        /**
         * 定时器Future
         */
        public Future future;

        public Entity(ByteBuffer value, Future future) {
            this.value = value;
            this.future = future;
        }
    }
}


