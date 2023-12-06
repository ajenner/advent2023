package days;

import templates.DayTemplate;

import java.util.ArrayList;
import java.util.Map;

public class Day1 extends DayTemplate {

    private static Map<String, String> numbers = Map.of("one", "o1e",
            "two", "t2o",
            "three", "t3e",
            "four", "f4r",
            "five", "f5e",
            "six", "s6x",
            "seven", "s7n",
            "eight", "e8t",
            "nine", "n9n");

    public static Integer part1(ArrayList<String> inputs) {
        int result = 0;
        for (String input : inputs) {
            result += Integer.parseInt(parseInput(input));
        }
        return result;
    }

    private static String parseInput(String input) {
        input = input.replaceAll("[^0-9.]", "");
        char[] chars = input.toCharArray();
        if (chars.length == 1) {
            return chars[0] + "" + chars[0];
        }
        return chars[0] + "" + chars[chars.length - 1];
    }

    public static Integer part2(ArrayList<String> inputs) {
        int result = 0;
        for (String input : inputs) {
            result += Integer.parseInt(parseInput(replaceWithNumerics(input)));
        }
        return result;
    }

    private static String replaceWithNumerics(String input) {
        for (String key : numbers.keySet()) {
            input = input.replaceAll(key, numbers.get(key));
        }
        return input;
    }

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        return (part1)? part1(inputs) : part2(inputs);
    }
}
