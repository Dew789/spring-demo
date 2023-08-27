package com.example.demo.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tower {
    private Stack<Integer> disks;
    private Integer index;

    public Tower(Integer index) {
        this.index = index;
        this.disks = new Stack<>();
    }

    public int index() {
        return index;
    }

    public void addDisk(Integer d) {
        if (!disks.isEmpty() && disks.peek() <= d) {
            System.out.print("Error placing disk " + d);
        } else {
            disks.push(d);
        }
    }

    public void moveTopTo(Tower target) {
        Integer d = disks.pop();
        target.addDisk(d);

        System.out.print("Mode disk Top form " + this.index() + " to " + target.index());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer i : disks) {
            sb.append(i);
            sb.append(",");
        }

        if (sb.length() > 2) {
            sb.deleteCharAt(sb.length() - 1);
        } else {
            return "Empty Disks";
        }

        return sb.toString();
    }

    public void moveDisks(Integer c, Tower target, Tower buffer) {
        if (c <= 0) {
            return;
        }

        moveDisks(c-1, buffer, target);
        moveTopTo(target);

        buffer.moveDisks(c-1, target, this);

    }


    public static void main(String[] args) {
        List<Tower> towers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            towers.add(new Tower(i));
        }

        // add disks
        for (int i = 1; i <= 5; i++) {
            towers.get(0).addDisk(i);
        }

        System.out.println(towers);

        towers.get(0).moveDisks(5, towers.get(2), towers.get(1));

        System.out.println(towers);

    }

}
