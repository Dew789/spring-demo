package com.example.demo.algorithm;

import java.util.Stack;

public class StackSort {

    public static Stack<Integer> insertSort(Stack<Integer> s) {
        Stack<Integer> temp = new Stack<>();
        while (!s.isEmpty()) {
            Integer i = s.pop();

            while (!temp.isEmpty() && temp.peek() >= i) {
                s.push(temp.pop());
            }
            temp.push(i);
        }

        return temp;
    }

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        for (int i = 9; i >= 0; i--) {
            s.push(i);
        }

        System.out.println(s);
        s = StackSort.insertSort(s);
        System.out.println(s);
    }
}
