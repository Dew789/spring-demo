package com.example.demo;

import com.example.demo.algorithm.Node;
import org.junit.Assert;
import org.junit.Test;
import java.util.Random;

public class NodeTests {

    private Node createLinkedList() {
        Node head = new Node(0);
        for (int i=1; i<10; i++) {
            head.appendToTail(i);
        }

        return head;
    }

    /**
     * build Linked list "0->1->2...->9"
     */
    @Test
    public void testCreate() {
        Node head = createLinkedList();

        head.printToTail();
    }

    /**
     * 测试删除节点
     */
    @Test
    public void testRemove() {
        Node head = createLinkedList();

        Node.remove(head, 3);
        head.printToTail();
    }


    /**
     * 反转链表
     */
    @Test
    public void testReverse() {
        Node head = createLinkedList();

        head = Node.reverse(head);
        head.printToTail();
    }

    /**
     * 找出倒数第k个节点
     */
    @Test
    public void testNthToLast() {
        Node head = createLinkedList();
        head.printToTail();

        Node kNode = Node.nthToLast(head, 3);
        System.out.print(kNode);
    }

    /**
     * 移除未排序链表中的重复节点
     */
    @Test
    public void testRemoveDups() {
        Node head = createLinkedList();
        for (int i=1; i<5; i++) {
            head.appendToTail(i);
        }
        head.printToTail();
        Node.removeDups(head);

        head.printToTail();
    }


    /**
     * 以给值x为基准，将链表分为两部分所有小于x的节点排在大于或者等于x节点之前
     */
    @Test
    public void testPartition() {
         Node head = new Node(0);

         Random r = new Random();
         for (int i=1; i<10; i++) {
             head.appendToTail(r.nextInt(10));
         }
         head = Node.partition(head, 4);
         head.printToTail();
    }



    /**
     * 给定一个有环单链表，实现一个算法返回环路的开头节点
     */
    @Test
    public void testFindBeginning() {
        Node head = new Node(0);
        Node begin = null;
        Node tail = null;
        for (int i=1; i<10; i++) {
            tail = head.appendToTail(i);
            if (i == 4) {
                begin = tail;
            }
        }
        tail.next = begin;

        Node result = Node.findBeginning(head);

        Assert.assertTrue(result.data == 4);

    }

    /**
     * 判断单向链表是否为回文
     */
    @Test
    public void isPalindrome() {
        Node head = new Node(0);
        for (int i=1; i<5; i++) {
            head.appendToTail(i);
        }
        for (int i=3; i>=0; i--) {
            head.appendToTail(i);
        }

        head.printToTail();

        Assert.assertTrue(Node.isPalindrome(head));

    }




}
