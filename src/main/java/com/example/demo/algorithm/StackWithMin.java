package com.example.demo.algorithm;

import java.util.Stack;

public class StackWithMin extends Stack<Integer> {
    private Stack<Integer> minStack = new Stack<>();

    @Override
    public Integer pop() {
        Integer item = super.pop();
        if (item == minStack.peek()) {
            minStack.pop();
        }
        return item;
    }

    @Override
    public Integer push(Integer item) {
        if (item < min()) {
            minStack.push(item);
        }
        return super.push(item);
    }

    public Integer min() {
        if (minStack.isEmpty() ) {
            return Integer.MAX_VALUE;
        }
        return minStack.peek();
    }

    public static void main(String[] args) {
        StackWithMin sm = new StackWithMin();
        sm.push(5);
        sm.push(1);
        sm.push(4);
        System.out.println(sm.min());
        sm.pop();
        sm.pop();
        System.out.println(sm.min());
    }

}
