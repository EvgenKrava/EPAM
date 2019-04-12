package ua.nure.kravchenko.practice1;

public class Part3 {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (i != args.length - 1) {
                System.out.print(args[i] + " ");
            } else {
                System.out.print(args[i]);
            }
        }
        System.out.println();
    }
}
