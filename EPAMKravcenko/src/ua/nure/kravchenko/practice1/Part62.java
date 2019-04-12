package ua.nure.kravchenko.practice1;

public class Part62 {

    public static boolean isPrime(int value){
        for (int i = 2; i < value; i++) {
            if (value%i==0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        int[] simples = new int[Integer.valueOf(args[0])];

        for (int i = 2, j = 0 ;j < Integer.valueOf(args[0]); i++) {
            if(isPrime(i)){
                simples[j]=i;
                j++;
            }
        }

        for (int i = 0; i < simples.length; i++) {
            System.out.print(simples[i]+" ");
        }
        System.out.println();
    }
}
