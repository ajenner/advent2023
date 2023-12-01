package util;

import day1.Day1;

import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        runDay1();
    }

    private static void runDay1() {
        System.out.println("Day1: ");
        Reader reader = new Reader("src/data/day1.txt");
        ArrayList<String> day1Inputs = reader.readAsStrings();
        System.out.println("Question 1: " + Day1.part1(day1Inputs));
        System.out.println("Question 2: " + Day1.part2(day1Inputs));
    }
}
