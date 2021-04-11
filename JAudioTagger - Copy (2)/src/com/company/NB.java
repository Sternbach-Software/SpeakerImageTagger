package com.company;

import java.util.Arrays;

public class NB {
    public static void main(String[] args) {
        double[] ss={1.1,2.2,3.3,4.4};
        System.out.println(Arrays.toString(reverse(ss)));
    }
    public static double[] reverse(double[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            // swap the values at the left and right indices
            double temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            // move the left and right index pointers in toward the center
            left++;
            right--;
        }
        return data;
    }
}
