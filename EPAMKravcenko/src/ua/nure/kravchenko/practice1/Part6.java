package ua.nure.kravchenko.practice1;

public class Part6 {
    public static void main(String[] args) {

        int[] simples = new int[Integer.valueOf(args[0])];

        for (int i = 2, k = 0; k < Integer.valueOf(args[0]); i++) {
            int count = 0;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) count++;
            }
            if (count == 0) {
                simples[k] = i;
                k++;
            }
        }

        for (int i = 0; i < simples.length; i++) {
            System.out.print(simples[i] + " ");
        }
        System.out.println();
    }
}
