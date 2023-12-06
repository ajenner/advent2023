package days;

import templates.DayTemplate;

import java.util.ArrayList;

public class Day2 extends DayTemplate {

    private static final int MAX_RED = 12;
    private static final int MAX_GREEN = 13;
    private static final int MAX_BLUE = 14;


    public static Integer part1(ArrayList<String> inputs) {
        int validCounter = 0;
        for (String input : inputs) {
            validCounter += testGameValidity(input);
        }
        return validCounter;
    }

    public static Integer part2(ArrayList<String> inputs) {
        int powerCounter = 0;
        for (String input : inputs) {
            powerCounter += getGamePower(input);
        }
        return powerCounter;
    }

    public static int testGameValidity(String input) {
        String[] sets = input.split(";");
        String[] gameSet = sets[0].split(":");
        sets[0] = gameSet[1];
        int gameNumber = Integer.parseInt(gameSet[0].replaceAll("[\\D]", ""));
        for (String set : sets) {
            for (String ball : set.split(",")) {
                int value = Integer.parseInt(ball.replaceAll("[\\D]", ""));
                if (ball.contains("red") && value > MAX_RED) {
                    return 0;
                }
                if (ball.contains("green") && value > MAX_GREEN) {
                    return 0;
                }
                if (ball.contains("blue") && value > MAX_BLUE) {
                    return 0;
                }
            }
        }
        return gameNumber;
    }

    public static int getGamePower(String input) {
        String[] sets = input.split(";");
        String[] gameSet = sets[0].split(":");
        sets[0] = gameSet[1];
        int minRed = 1, minGreen = 1, minBlue = 1;
        for (String set : sets) {
            for (String ball : set.split(",")) {
                int value = Integer.parseInt(ball.replaceAll("[\\D]", ""));
                if (ball.contains("red") && value > minRed) {
                    minRed = value;
                }
                if (ball.contains("green") && value > minGreen) {
                    minGreen = value;
                }
                if (ball.contains("blue") && value > minBlue) {
                    minBlue = value;
                }
            }
        }
        return minRed * minGreen * minBlue;
    }

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        return (part1)? part1(inputs) : part2(inputs);
    }
}
