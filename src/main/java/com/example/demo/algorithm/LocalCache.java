package com.example.demo.algorithm;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 构建一个本地缓存，缓存对象是用户信息，缓存对象存活时间超过指定expireTime时间，则缓存对象失效
 * 1.缓存失效
 *  - 主动失效  需要异步线程去主动更新
 *  - 被动失效  每次查询判断时间
 * 2.缓存击穿的解决方案
 *  - 使用双锁校验
 * 3.从缓存拿出来的对象不应该被修改
 *  - 使用使用final修饰
 * 4.应该使用算法限制缓存的大小
 *  - LRU
 * 5.过期时间不一定要存在用户对象内
 */
public class LocalCache {

    private final ConcurrentHashMap<String, User> cache = new ConcurrentHashMap<>();

//    private final ExecutorService expireWorker = ExecutorService;

    /**
     * 写缓存
     *
     * @param user       缓存对象
     * @param expireTime 过期时间，单位：毫秒
     */
    public void put(String userId, User user, long expireTime) {
        user.setExpire(System.currentTimeMillis() + expireTime);
        cache.put(userId, user);
    }

    /**
     * 读缓存
     *
     * @param userId 用户Id
     * @return 获取的用户对象
     */
    public User get(String userId) {
        User user = cache.get(userId);
        if (user == null) {
            return null;
        }

        if (System.currentTimeMillis() > user.expire) {
            cache.remove(userId);
            return null;
        }

        return user;
    }

    public static class User {
        private String id;
        private String name;
        private Integer age;
        private long expire;

        public User(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }

        public long getExpire() {
            return this.expire;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return this.id;
        }
    }

    public static class UserReadService {
        /**
         * 默认系统提供此方法
         * 无需填充内容
         *
         * @param userId 用户ID
         * @return
         */
        private User readUserFromDB(String userId) {
            System.out.println("Get from db");
            if ("001".equals(userId)) {
                return new User("001");
            }
            return null;
        }

        /**
         * 请使用上述本地缓存及readUserFromDB方法实现
         *
         * @param userId 用户ID
         * @return
         */
        public User getUser(String userId) {
            LocalCache localCache = new LocalCache();

            User user = localCache.get(userId);
            if (user == null) {
                synchronized (LocalCache.class) {
                    user = localCache.get(userId);
                    if (user != null) {
                        return user;
                    }
                    user = readUserFromDB(userId);
                    localCache.put(userId, user, 1000);
                }
            }

            return user;
        }
    }

    public static void main(String[] args) throws Exception {

        UserReadService userReadService = new UserReadService();

        System.out.print(userReadService.getUser("001"));
        System.out.print(userReadService.getUser("001"));

        Thread.sleep(3000);

        System.out.print(userReadService.getUser("001"));
    }
}
