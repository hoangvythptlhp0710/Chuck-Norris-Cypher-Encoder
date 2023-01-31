package chucknorris;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final String NORRIS_ENCODED_SEQUENCE_REGEX = "(0{1,2} 0*)";
    private static final String SEVEN_BIT_CHUNK_REGEX_PATTERN = "(?<=\\G.{7})";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input encoded string: ");
        String in = sc.nextLine();
        Pattern pattern = Pattern.compile(NORRIS_ENCODED_SEQUENCE_REGEX);
        Matcher matcher = pattern.matcher(in);
        System.out.println("\nThe result: ");
        Arrays.stream(matcher.results()
                .map(MatchResult::group)
                .map(s -> s.split(" "))
                .map(s -> s[0].equals("0") ? "1".repeat(s[1].length()) : "0".repeat(s[1].length()))
                .collect(Collectors.joining())
                .split(SEVEN_BIT_CHUNK_REGEX_PATTERN))
                .map(s -> (char) Integer.parseInt(s, 2))
                .forEach(System.out::print);
    }
}