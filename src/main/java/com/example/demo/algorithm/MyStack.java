package com.example.demo.algorithm;

public class MyStack {

    private Node top = null;

    private class Node {
        public Object data;
        public Node next = null;

        public Node(Object data) {
            this.data = data;
        }
    }

    public Object pop() {
        if (top != null) {
            Object item = top.data;
            top = top.next;
            return item;
        }
        return null;
    }

    public void push(Object data) {
        Node t = new Node(data);
        t.next = top;
        top = t;
    }

    public Object peek() {
        return top == null ? null: top.data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node n = top;
        while (n != null) {
            sb.append(n.data);
            sb.append(",");
            n = n.next;
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack);
        stack.pop();
        System.out.println(stack);
    }
}
