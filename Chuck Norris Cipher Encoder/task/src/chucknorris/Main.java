package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input string: ");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        char[] res = s.toCharArray();
        System.out.println("The result: ");
        for (Character c :
                res) {
            String a = Integer.toBinaryString(c);
            char[] analyze = a.toCharArray();

        }
    }
}