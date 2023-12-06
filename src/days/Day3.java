package days;

import templates.DayTemplate;

import java.util.ArrayList;

public class Day3 extends DayTemplate {

    public static Integer part1(ArrayList<String> inputs) {
        ArrayList<char[]> charsMap = buildCharArray(inputs);
        int runningTotal = 0;
        for (int i = 0; i < charsMap.size(); i++) {
            char [] row = charsMap.get(i);
            for (int j = 0; j < row.length; j++) {
                if (!Character.isDigit(row[j]) && !(row[j] == '.')) {
                    runningTotal += findAdjacentNumbers(charsMap, i, j);
                }
            }
        }
        return runningTotal;
    }

    public static Integer part2(ArrayList<String> inputs) {
        ArrayList<char[]> charsMap = buildCharArray(inputs);
        int runningTotal = 0;
        for (int i = 0; i < charsMap.size(); i++) {
            char [] row = charsMap.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == '*') {
                    runningTotal += findGearRatios(charsMap, i, j);
                }
            }
        }
        return runningTotal;
    }

    private static int findAdjacentNumbers(ArrayList<char[]> charsMap, int i, int j) {
        int runningTotal = 0;
        if (testIfNumber(charsMap, i - 1, j - 1)) {
            String number = buildFullNumber(charsMap, i - 1, j - 1);
            runningTotal += Integer.parseInt(number);
        }
        if (testIfNumber(charsMap, i - 1, j)) {
            String number = buildFullNumber(charsMap, i - 1, j);
            runningTotal += Integer.parseInt(number);
        }
        if (testIfNumber(charsMap, i - 1, j + 1)) {
            String number = buildFullNumber(charsMap, i - 1, j + 1);
            runningTotal += Integer.parseInt(number);
        }
        if (testIfNumber(charsMap, i, j - 1)) {
            String number = buildFullNumber(charsMap, i, j - 1);
            runningTotal += Integer.parseInt(number);
        }
        if (testIfNumber(charsMap, i, j + 1)) {
            String number = buildFullNumber(charsMap, i, j + 1);
            runningTotal += Integer.parseInt(number);
        }
        if (testIfNumber(charsMap, i + 1, j - 1)) {
            String number = buildFullNumber(charsMap, i + 1, j - 1);
            runningTotal += Integer.parseInt(number);
        }
        if (testIfNumber(charsMap, i + 1, j)) {
            String number = buildFullNumber(charsMap, i + 1, j);
            runningTotal += Integer.parseInt(number);
        }
        if (testIfNumber(charsMap, i + 1, j + 1)) {
            String number = buildFullNumber(charsMap, i + 1, j + 1);
            runningTotal += Integer.parseInt(number);
        }
        return runningTotal;
    }

    private static int findGearRatios(ArrayList<char[]> charsMap, int i, int j) {
        int runningTotal = 1;
        int adjacentNumbers = 0;
        if (testIfNumber(charsMap, i - 1, j - 1)) {
            String number = buildFullNumber(charsMap, i - 1, j - 1);
            runningTotal *= Integer.parseInt(number);
            adjacentNumbers++;
        }
        if (testIfNumber(charsMap, i - 1, j)) {
            String number = buildFullNumber(charsMap, i - 1, j);
            runningTotal *= Integer.parseInt(number);
            adjacentNumbers++;
        }
        if (testIfNumber(charsMap, i - 1, j + 1)) {
            String number = buildFullNumber(charsMap, i - 1, j + 1);
            runningTotal *= Integer.parseInt(number);
            adjacentNumbers++;
        }
        if (testIfNumber(charsMap, i, j - 1)) {
            String number = buildFullNumber(charsMap, i, j - 1);
            runningTotal *= Integer.parseInt(number);
            adjacentNumbers++;
        }
        if (testIfNumber(charsMap, i, j + 1)) {
            String number = buildFullNumber(charsMap, i, j + 1);
            runningTotal *= Integer.parseInt(number);
            adjacentNumbers++;
        }
        if (testIfNumber(charsMap, i + 1, j - 1)) {
            String number = buildFullNumber(charsMap, i + 1, j - 1);
            runningTotal *= Integer.parseInt(number);
            adjacentNumbers++;
        }
        if (testIfNumber(charsMap, i + 1, j)) {
            String number = buildFullNumber(charsMap, i + 1, j);
            runningTotal *= Integer.parseInt(number);
            adjacentNumbers++;
        }
        if (testIfNumber(charsMap, i + 1, j + 1)) {
            String number = buildFullNumber(charsMap, i + 1, j + 1);
            runningTotal *= Integer.parseInt(number);
            adjacentNumbers++;
        }
        return (adjacentNumbers > 1)? runningTotal : 0;
    }

    private static String buildFullNumber(ArrayList<char[]> charsMap, int i, int j) {
        return leftDigits(charsMap, i, j) + rightDigits(charsMap, i, j);
    }

    private static String leftDigits(ArrayList<char[]> charsMap, int i, int j) {
        String output = "";
        try {
            while (Character.isDigit(charsMap.get(i)[j])) {
                output = charsMap.get(i)[j] + output;
                charsMap.get(i)[j] = '.';
                j--;
            }
        } catch (Exception ex) {}
        return output;
    }

    private static String rightDigits(ArrayList<char[]> charsMap, int i, int j) {
        String output = "";
        try {
            while (Character.isDigit(charsMap.get(i)[++j])) {
                output += charsMap.get(i)[j];
                charsMap.get(i)[j] = '.';
            }
        } catch (Exception ex) {}
        return output;
    }

    private static boolean testIfNumber (ArrayList<char[]> charsMap, int i, int j) {
        try {
            char candidate = charsMap.get(i)[j];
            return Character.isDigit(candidate);
        } catch (Exception e) {
            return false;
        }
    }

    private static ArrayList<char[]> buildCharArray(ArrayList<String> inputs) {
        var result = new ArrayList<char[]>();
        for (int i = 0; i < inputs.size(); i++) {
            result.add(i, inputs.get(i).toCharArray());
        }
        return result;
    }

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        return (part1)? part1(inputs) : part2(inputs);
    }
}
