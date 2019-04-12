package ua.nure.kravchenko.practice1;

public class Part2 {
    public static void main(String[] args) {

        double result = 0;

        for (int i = 0; i < args.length; i++) {
            result += Double.valueOf(args[i]);
        }

        System.out.println("Sum = " + result);
    }
}
