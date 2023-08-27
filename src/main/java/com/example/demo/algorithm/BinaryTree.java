package com.example.demo.algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<E> {
    public Node root = null;

    private class Node {
        public E cargo = null;
        public Node left = null;
        public Node right = null;

        public Node(E cargo) {
            this.cargo = cargo;
        }

        public String toString() {
            return cargo.toString();
        }
    }

    public boolean insertLevel(E target) {
        Node node = new Node(target);
        if (this.root == null) {
            this.root = node;
            return true;
        }
        Queue<Node> q = new LinkedList<>();
        q.offer(this.root);
        Node current;
        while (q.peek() != null) {
            current = q.poll();
            if (current.left == null) {
                current.left = node;
                break;
            } else if (current.right == null) {
                current.right = node;
                break;
            } else {
                q.offer(current.left);
                q.offer(current.right);
            }
        }
        return true;
    }

    public void level() {
        Queue<Node> q = new LinkedList<>();
        q.offer(this.root);
        Node current = null;

        while (q.peek() != null) {
            current = q.poll();
            System.out.print(current);
            if (current.left != null) {
                q.offer(current.left);
            }
            if (current.right != null) {
                q.offer(current.right);
            }
        }
    }

    public void preOrder(Node current) {
        if (current == null) {
            return;
        }
        System.out.print(current);
        preOrder(current.left);
        preOrder(current.right);
    }

    public void preOrder() {
        Stack<Node> s = new Stack<>();
        Node node = this.root;
        while(!s.empty() || node != null) {
            while (node != null) {
                System.out.print(node);
                s.push(node);
                node = node.left;
            }
            node = s.pop();
            node = node.right;
        }
    }

    public void inOrder(Node current) {
        if (current == null) {
            return;
        }
        inOrder(current.left);
        System.out.print(current);
        inOrder(current.right);
    }

    public void inOrder() {
        Stack<Node> s = new Stack<>();
        Node node = this.root;

        while (!s.empty() || node != null) {
            while (node != null) {
                s.push(node);
                node = node.left;
            }
            node = s.pop();
            System.out.print(node);
            node = node.right;
        }

    }

    public void postOrder(Node current) {
        if (current ==  null) {
            return;
        }
        postOrder(current.left);
        postOrder(current.right);
        System.out.print(current);
    }

    public void postOrder() {
        Stack<Node> s = new Stack<>();
        Node node = null;
        Node pre = null;
        s.push(this.root);

        while (!s.empty()) {
            node = s.peek();
            if ((node.right == null && node.left == null) ||
                 pre != null && (node.right == pre || node.left == pre)) {
                System.out.print(node);
                pre = node;
                s.pop();
            } else {
                if (node.right != null) {
                    s.push(node.right);
                }
                if (node.left != null) {
                    s.push(node.left);
                }
            }
        }

    }

    /**
     *  Level Order build th tree
     *               0
     *             /   \
     *            1     2
     *           / \   / \
     *          3   4  5  6
     * @param args
     */
    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>();
        for (int i=0; i<7; i++) {
            tree.insertLevel(i);
        }

        tree.postOrder();
    }
}