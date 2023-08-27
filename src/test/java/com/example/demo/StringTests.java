package com.example.demo;

import org.junit.Test;

import javax.validation.constraints.NotNull;

public class StringTests {

    /**
     * 在不使用额外数据结构的情况下，确定一个字符串的所有字符是否全部都不同
     */
    @Test
    public void testDifferent() {
    }

    private void test(@NotNull String a) {
        System.out.println(a);
    }
}
