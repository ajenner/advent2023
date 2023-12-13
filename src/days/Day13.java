package days;

import templates.DayTemplate;

import java.util.ArrayList;

public class Day13 extends DayTemplate {

    ArrayList<ArrayList<String>> patterns;

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        buildPatterns(inputs);
        return calculateSymmetryValue(part1).toString();
    }

    private void buildPatterns(ArrayList<String> inputs) {
        patterns = new ArrayList<>();
        ArrayList<String> pattern = new ArrayList<>();
        for (String line : inputs) {
            if (line.equals("")) {
                patterns.add(pattern);
                pattern = new ArrayList<>();
                continue;
            }
            pattern.add(line);
        }
        patterns.add(pattern);
    }

    private Integer calculateSymmetryValue(boolean part1) {
        int total = 0;
        for (ArrayList<String> pattern : patterns) {
            int hSlice = 0;
            for (int i = 0; i < pattern.size() - 1; i++) {
                int diff = computeReflectedDifference(i, i + 1, pattern, 0);
                if ((diff == 0 && part1) || (diff == 1 && !part1) ) {
                    hSlice = i+1;
                    break;
                }
            }
            ArrayList<String> verticalPattern = rotate(pattern);
            int vSlice = 0;
            for (int i = 0; i < verticalPattern.size() - 1; i++) {
                int diff = computeReflectedDifference(i, i + 1, verticalPattern, 0);
                if (diff == 0 && part1 || (diff == 1 && !part1)) {
                    vSlice = i+1;
                    break;
                }
            }
            total += vSlice + (hSlice * 100);
        }
        return total;
    }

    private Integer computeReflectedDifference(int i, int j, ArrayList<String> pattern, int d) {
        int diff = d;
        if (diff > 1) {
            return diff;
        }
        diff += computeDifference(pattern.get(i), pattern.get(j));
        if (i == 0 || j == pattern.size() - 1) {
            return diff;
        }
        return computeReflectedDifference(i - 1, j + 1, pattern, diff);
    }

    private Integer computeDifference(String a, String b) {
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diff++;
            }
        }
        return diff;
    }

    private ArrayList<String> rotate (ArrayList<String> pattern) {
        ArrayList<String> result = new ArrayList<>();
        for (int j = 0; j < pattern.get(0).length(); j++) {
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < pattern.size(); i++) {
                temp.append(pattern.get(i).split("")[j]);
            }
            result.add(temp.toString());
        }
        return result;
    }
}
