package ua.nure.kravchenko.practice1;

public class Part5 {
    public static void main(String[] args) {
        int sum = 0;
        char[] chars = args[0].toCharArray();
        for (int i = 0; i < args[0].length(); i++) {
            sum += Integer.parseInt(String.valueOf(chars[i]));
        }
        System.out.println(sum);
    }
}
