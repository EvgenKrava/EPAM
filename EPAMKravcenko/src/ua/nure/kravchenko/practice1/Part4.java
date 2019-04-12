package ua.nure.kravchenko.practice1;

import java.util.prefs.NodeChangeEvent;

public class Part4 {

    public static int NOD(int a, int b) {
        for (int i = Math.min(a, b); i > 0; i--) {
            if ((a % i == 0) && (b % i == 0))
                return i;
        }
        return 1;
    }

    public static void main(String[] args) {
        System.out.println("НОД чисел " + args[0] + " и " + args[1] + ": " + NOD(Integer.valueOf(args[0]), Integer.valueOf(args[1])));
    }
}
