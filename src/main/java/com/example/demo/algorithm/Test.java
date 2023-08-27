package com.example.demo.algorithm;


public class Test {

    public int divide(int dividend, int divisor) {
        if (dividend == 0) {
            return 0;
        }
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 处理符号问题
        Boolean sign = false;
        if (dividend < 0 && divisor > 0
                || divisor < 0 && dividend > 0) {
            sign = true;
        }
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        int step = 0;
        int sum = 0;
        int result = 0;
        while (true) {
            int temp = sum + (divisor << step);
            if (temp > dividend || temp < sum) {
                if (step == 0) {
                    break;
                }
                step--;
                continue;
            }

            result = result + (1<<step);
            sum = temp;
            step++;
        }

        if (sign) {
            result = -result;
        }

        return result;
    }





    public static void main(String[] args) {
        Test t = new Test();
        int result = t.divide(-2147483648,
                1);
//        long result = (long) Integer.MAX_VALUE << (long)1;
//        int resulti = Integer.MAX_VALUE << 1;
        System.out.println(Math.abs(-2147483648));
//        System.out.println(resulti);
    }


}
