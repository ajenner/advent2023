package util;

import day1.Day1;
import day2.Day2;
import day3.Day3;

import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        //runDay1();
        //runDay2();
        runDay3();
    }

    private static void runDay1() {
        System.out.println("Day1: ");
        Reader reader = new Reader("src/data/day1.txt");
        ArrayList<String> day1Inputs = reader.readAsStrings();
        System.out.println("Question 1: " + Day1.part1(day1Inputs));
        System.out.println("Question 2: " + Day1.part2(day1Inputs));
    }

    private static void runDay2() {
        System.out.println("Day2: ");
        Reader reader = new Reader("src/data/day2.txt");
        ArrayList<String> day2Inputs = reader.readAsStrings();
        System.out.println("Question 1: " + Day2.part1(day2Inputs));
        System.out.println("Question 2: " + Day2.part2(day2Inputs));
    }

    private static void runDay3() {
        System.out.println("Day3: ");
        Reader reader = new Reader("src/data/day3.txt");
        ArrayList<String> day3Inputs = reader.readAsStrings();
        System.out.println("Question 1: " + Day3.part1(day3Inputs));
        System.out.println("Question 2: " + Day3.part2(day3Inputs));
    }
}
