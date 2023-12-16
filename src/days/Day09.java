package days;

import templates.DayTemplate;
import java.util.ArrayList;
import java.util.Collections;

public class Day09 extends DayTemplate {

    public ArrayList<Integer> parseNumbers (boolean part1, String line) {
        ArrayList<Integer> nums = new ArrayList<>();
        for (String num : line.trim().split("\\s+")) {
            nums.add(Integer.parseInt(num));
        }
        if (!part1) {
            Collections.reverse(nums);
        }
        return nums;
    }

    public Integer enumerate(boolean part1, String line) {
        int total = 0;
        ArrayList<Integer> nums = parseNumbers(part1, line);
        int j = nums.size();
        while (true) {
            int zeros = 0;
            j--;
            total += nums.get(j);
            for (int i = 1; i <= j; i++) {
                int differential = nums.get(i) - nums.get(i - 1);
                if (differential == 0) {
                    zeros++;
                }
                nums.set(i - 1, differential);
            }
            if (zeros == j) {
                return total;
            }
        }
    }

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        Integer total = 0;
        for (String line : inputs) {
            total += enumerate(part1, line);
        }
        return total.toString();
    }
}
