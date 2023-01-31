package chucknorris;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Input string: ");
        Scanner sc = new Scanner(System.in);
        char[] res = sc.nextLine().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c: res) {
            String bin = Integer.toBinaryString(c);
            while (bin.length() < 7) {
                bin = '0' + bin;
            }
            sb.append(bin);
        }
        System.out.println();
        System.out.println("The result: ");
        int i = 0;
        char currentChar;
        while (i < sb.length()) {
            if (sb.charAt(i) == '0') {
                System.out.print("00 ");
                currentChar = '0';
            }
            else {
                System.out.print("0 ");
                currentChar = '1';
            }
            while (sb.charAt(i) == currentChar) {
                System.out.print("0");
                i++;
                if (i >= sb.length()) break;
            }
            if (i < sb.length()) System.out.print(" ");
        }
        sc.close();
    }
}