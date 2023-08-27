package com.example.demo.spring;

import com.netflix.hystrix.*;

public class HelloWorldHystrixCommand extends HystrixCommand<String> {
    private final String name;

    public HelloWorldHystrixCommand(String name) {

        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("PrimaryCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("PrimaryCommand"))
                .andCommandPropertiesDefaults(
                        // we default to a 600ms timeout for primary
                        HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(1000)));
        this.name = name;
    }

    // 如果继承的是HystrixObservableCommand，要重写Observable construct()
    @Override
    protected String run() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {}

        return "Hello " + name;
    }

    @Override
    protected String getFallback() {
        return "fallback: " + name;
    }


    public static void main(String[] args) {
        try {
            System.out.println("!");
            return;
        } catch (Exception e) {
            System.out.println("3");

        }
    }
}
