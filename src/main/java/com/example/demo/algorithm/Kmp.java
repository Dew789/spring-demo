package com.example.demo.algorithm;

import java.util.Arrays;

public class Kmp {

    public static int[] getNext(String P) {
        int[] next = new int[P.length()];
        next[0] = -1;
        int j;

        for (int i = 1; i < P.length(); i++) {
            j = next[i-1];

            while (j >= 0 && P.charAt(i) != P.charAt(j) ) {
                j = next[j];
            }

            if (j >= 0 && P.charAt(i) == P.charAt(j) ) {
                next[i] = j + 1;
            } else {
                next[i] = 0;
            }
        }

        return next;
    }

    public static Integer find(String T, String P) {
        int[] next = getNext(P);
        System.out.println(Arrays.toString(next));
        int j = 0;

        for (int i = 0; i < T.length(); i++) {
            while (j != P.length()) {
                if (T.charAt(i+j) == P.charAt(j)) {
                    j++;
                } else {
                    j = j > 1 ? next[j-1] : 0;
                    break;
                }
            }

            if (j == P.length()) {
                return i;
            }
        }

        return null;
    }


    public static void main(String[] args) {
        String P = "abacab";
        String T = "abacaabacabacabaabb";

        T = "bbc abcdab abcdabcdabde";
        P = "abcdabd";
        

    }
}
