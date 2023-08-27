package com.example.demo.algorithm;

import java.util.HashSet;
import java.util.Stack;

public class Node {
    public Node next = null;
    public int data;

    public Node(int d) {
        this.data = d;
    }

    /**
     * 添加节点到单向链表尾部
     * @param d
     */
    public Node appendToTail(int d) {
        Node end = new Node(d);
        Node n = this;
        while (n.next != null) {
            n = n.next;
        }

        n.next = end;

        return end;
    }

    /**
     * 添加节点
     * @param d
     */
    public static Node remove(Node head, int d) {
        Node n = head;

        if (n == null) {
            return null;
        }
        if (n.data == d) {
            return n.next;
        }

        while (n.next != null) {
            if (n.next.data == d) {
                n.next = n.next.next;
                return head;
            }
            n = n.next;
        }
        return head;
    }

    /**
     * 逆序链表
     * @return
     */
    public static Node reverse(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node prev = null;
        Node curr = head;
        Node third = head.next;

        while (true) {
            curr.next = prev;
            prev = curr;
            curr = third;
            if (curr == null) {
                break;
            }
            third = curr.next;
        }
        return prev;
    }

    /**
     * 找出单向链表中倒数第k个节点
     * @return
     */
    public static Node nthToLast(Node head, int k) {
        if (k <= 0) {
            return null;
        }

        Node n = head;
        Node kNode = head;
        int index = 0;

        while (n != null) {
            n = n.next;
            index++;
            if (index > k) {
                kNode = kNode.next;
            }

        }

        return kNode;
    }

    public static boolean deleteNode(Node node) {
        if (node == null | node.next == null) {
            return false;
        }
        Node n = node.next;
        node.data = n.data;
        node.next = n.next;
        return true;
    }

    public static void removeDups(Node head) {
        HashSet table = new HashSet<>();
        Node n = head;
        Node prev = head;


        while (n != null) {
            if (table.contains(n.data)) {
                prev.next = n.next;
            } else {
                table.add(n.data);
                prev = n;
            }
            n = n.next;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(data);
    }

    public void printToTail() {
        Node n = this;
        if (n != null) {
            System.out.print(n);
            n = n.next;
        }

        while (n != null) {
            System.out.print("->" + n);
            n = n.next;
        }
        System.out.println("");
    }

    public static Node partition(Node head, int x) {
        Node n = head;
        Node beforeStarter = null;
        Node afterStarter  = null;

        while (n != null) {
            Node next = n.next;
            if (n.data < x) {
                n.next = beforeStarter;
                beforeStarter = n;
            } else {
                n.next = afterStarter;
                afterStarter = n;
            }
            n = next;
        }

        if (beforeStarter == null) {
            return beforeStarter;
        }

        Node newHead = beforeStarter;
        while (beforeStarter.next != null) {
            beforeStarter = beforeStarter.next;
        }
        beforeStarter.next = afterStarter;

        return newHead;
    }


    public static Node findBeginning(Node head) {
        Node slower = head;
        Node faster = head;

        while (faster != null && faster.next != null) {
            slower = slower.next;
            faster = faster.next.next;
            if (slower.data == faster.data) {
                break;
            }
        }

        if (faster == null || faster.next == null) {
            return null;
        }

        Node begin = head;
        while (slower.data != begin.data) {
            slower = slower.next;
            begin = begin.next;
        }

        return begin;

    }

    public static boolean isPalindrome(Node head) {
        assert head != null;

        Node slower = head;
        Node faster = head;
        Stack<Integer> stack = new Stack();

        while (faster!= null && faster.next!= null) {
            stack.push(slower.data);

            slower = slower.next;
            faster = faster.next.next;
        }

        // 链表的长度为奇数
        if (faster != null) {
            slower = slower.next;
        }

        while (slower != null) {

            int n = stack.pop();
            if (n != slower.data) {
                return false;
            }
            slower = slower.next;

        }

        return true;

    }

}