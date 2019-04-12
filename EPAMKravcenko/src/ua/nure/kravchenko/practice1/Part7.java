package ua.nure.kravchenko.practice1;

public class Part7 {

    private static int char2int(char c) {
        return (int) c - 64;
    }

    private static char int2char(int a) {
        if (a==0) return 'Z';
        return (char) (a + 64);
    }

    public static int str2int(String number) {
        int result = 0;
        for (int i = 0; i < number.length(); i++) {
            int tmp = (char2int(number.charAt(i))) * (int) Math.pow(26, number.length() - i - 1);
            result += tmp;
        }
        return result;
    }

    public static String int2str(int number) {

        String result = "";

        int ostatok;

        while(number>26){
            ostatok = number%26;
            if (ostatok==0)
                number-=26;
            result += int2char(ostatok);
            number/=26;
        }
        result += int2char(number);
        return reversString(result);
    }

    private static String reversString(String str){
        String result = "";
        for (int i = str.length()-1; i >= 0; i--) {
            result += str.charAt(i);
        }
        return result;
    }

    public static String rightColumn(String number) {
        return int2str(str2int(number)+1);
    }

    public static void main(String[] args) {
        System.out.println("A\t=>\t"+str2int("A") + "\t=>\t"+int2str(1));
        System.out.println("B\t=>\t"+str2int("B")+ "\t=>\t"+int2str(2)) ;
        System.out.println("Z\t=>\t"+str2int("Z")+ "\t=>\t"+int2str(26));
        System.out.println("AA\t=>\t"+str2int("AA")+ "\t=>\t"+int2str(27));
        System.out.println("AZ\t=>\t"+str2int("AZ")+ "\t=>\t"+int2str(52));
        System.out.println("BA\t=>\t"+str2int("BA")+ "\t=>\t"+int2str(53));
        System.out.println("ZZ\t=>\t"+str2int("ZZ")+ "\t=>\t"+int2str(702));
        System.out.println("AAA\t=>\t"+str2int("AAA")+ "\t=>\t"+int2str(703));
        System.out.print("A => "+rightColumn("A"));
        System.out.print(" | B => "+rightColumn("B")) ;
        System.out.print(" | Z => "+rightColumn("Z"));
        System.out.print(" | AA => "+rightColumn("AA"));
        System.out.print(" | AZ => "+rightColumn("AZ"));
        System.out.print(" | BA => "+rightColumn("BA"));
        System.out.print(" | ZZ => "+rightColumn("ZZ"));
        System.out.println(" | AAA => "+rightColumn("AAA"));
    }

}
