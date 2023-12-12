package days;

import common.Tuple;
import templates.DayTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class Day12 extends DayTemplate {

    ArrayList<Integer> groupSizes = new ArrayList<>();
//    ArrayList<String> potentialLines = new ArrayList<>();
//    Long possibilitiesCount = 0l;
    HashMap<Tuple<Integer, Integer, Integer>, Long> blockCache;

    private Long dynamicPossibilities(boolean part1, String input) {
        String[] splitInput = input.split(" ");
        String springs = splitInput[0];
        String groupCounts = splitInput[1];
        if (!part1) {
            springs = springs + "?" + springs + "?" + springs + "?" + springs + "?" + springs;
            groupCounts = groupCounts + "," + groupCounts + "," + groupCounts + "," + groupCounts + "," + groupCounts;
        }
        String[] numbers = groupCounts.split(",");
        groupSizes = new ArrayList<>();
        for (String i : numbers) {
            groupSizes.add(Integer.parseInt(i));
        }
        blockCache = new HashMap<>();
        return countPossibilitiesDynamically(springs, 0, 0, 0);
    }

    private Long countPossibilitiesDynamically(String input, Integer i, Integer j, Integer groupSize) {
        Tuple<Integer, Integer, Integer> key = new Tuple<>(i, j, groupSize);
        if (blockCache.containsKey(key)) {
            return blockCache.get(key);
        }
        if (i == input.length()) {
            return (j == groupSizes.size() && groupSize == 0) || (j == groupSizes.size() - 1 && groupSizes.get(j) == groupSize) ? 1L : 0L;
        }
        long total = 0;
        char spring = input.charAt(i);
        if ((spring == '.' || spring == '?') && groupSize == 0) {
            total += countPossibilitiesDynamically(input, i + 1, j, 0);
        }
        if ((spring == '.' || spring == '?') && groupSize > 0 && j < groupSizes.size() && groupSizes.get(j) == groupSize) {
            total += countPossibilitiesDynamically(input, i + 1, j + 1, 0);
        }
        if (spring == '#' || spring == '?') {
            total += countPossibilitiesDynamically(input, i + 1, j, groupSize + 1);
        }
        blockCache.put(key, total);
        return total;
    }

//    private Long possibleConfigurations (String input, boolean part1) {
//        String[] numbers = input.split(" ")[1].split(",");
//        potentialLines = new ArrayList<>();
//        groupCounts = new ArrayList<>();
//        for (String i : numbers) {
//            groupCounts.add(Integer.parseInt(i));
//        }
//
//        possibilitiesCount = 0L;
//        generatePossibleLine(input.split(" ")[0].toCharArray(), 0);
//        return possibilitiesCount;
//    }
//
//    public boolean checkLineValidity (String line) {
//        String[] questionBlocks = line.split(" ")[0].split("\\.");
//        int index = 0;
//        for (String block : questionBlocks) {
//            if (index == groupCounts.size()) {
//                return false;
//            }
//            if (block.equals("")) {
//                continue;
//            }
//            else if (block.length() == groupCounts.get(index)) {
//                index++;
//            }
//            else {
//                return false;
//            }
//        }
//        return index == groupCounts.size();
//    }
//
//    public void generatePossibleLine(char[] str, int pos) {
//        if (pos >= str.length) {
//            if (checkLineValidity(new String(str))) {
//                possibilitiesCount++;
//            }
//            return;
//        }
//        if (str[pos] == '?') {
//            str[pos] = '.';
//            generatePossibleLine(str, pos+1);
//            str[pos] = '#';
//            generatePossibleLine(str, pos+1);
//            str[pos] = '?';
//        }
//        else {
//            generatePossibleLine(str, pos+1);
//        }
//    }

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        Long total = 0L;
        for (String input : inputs) {
            total += dynamicPossibilities(part1, input);
        }
        return total.toString();
    }
}
