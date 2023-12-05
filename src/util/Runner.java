package util;

import day1.Day1;
import day2.Day2;
import day3.Day3;
import day4.Day4;
import day5.Day5;

import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        //runDay1();
        //runDay2();
        //runDay3();
        //runDay4();
        runDay5();
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

    private static void runDay4() {
        System.out.println("Day4: ");
        Reader reader = new Reader("src/data/day4.txt");
        ArrayList<String> day4Inputs = reader.readAsStrings();
        System.out.println("Question 1: " + Day4.part1(day4Inputs));
        System.out.println("Question 2: " + new Day4().part2(day4Inputs));
    }

    private static void runDay5() {
        System.out.println("Day5: ");
        Reader reader = new Reader("src/data/day5.txt");
        ArrayList<String> day5Inputs = reader.readAsStrings();
        Day5 day5 = new Day5(day5Inputs);
        System.out.println("Question 1: " + day5.part1());
        System.out.println("Question 2: " + day5.part2());
    }
}
