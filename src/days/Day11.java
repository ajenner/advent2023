package days;

import templates.DayTemplate;
import java.util.ArrayList;
import java.util.HashSet;

public class Day11 extends DayTemplate {

    ArrayList<Galaxy> galaxies;
    HashSet<Integer> fullRows;
    HashSet<Integer> fullColumns;

    private void findGalaxies(ArrayList<String> inputs, boolean part1) {
        galaxies = new ArrayList<>();
        fullRows = new HashSet<>();
        fullColumns = new HashSet<>();
        for (int y = 0; y < inputs.size(); y++) {
            String[] points = inputs.get(y).split("");
            for (int x = 0; x < points.length; x++) {
                if (points[x].equals("#")) {
                    galaxies.add(new Galaxy(x, y));
                    fullRows.add(y);
                    fullColumns.add(x);
                }
            }
        }
        expandGalaxies(inputs, part1);
    }

    private void expandGalaxies(ArrayList<String> inputs, boolean part1) {
        long numRowsAdded = 0;
        long numColumnsAdded = 0;
        long expansionFactor = (part1)? 1 : 999999;
        for (int y = 0; y < inputs.size(); y++) {
            if (!fullRows.contains(y)) {
                for (Galaxy g : galaxies) {
                    if (g.y - numRowsAdded > y) {
                        g.y += expansionFactor;
                    }
                }
                numRowsAdded += expansionFactor;
            }
        }
        for (int x = 0; x < inputs.get(0).length(); x++) {
            if (!fullColumns.contains(x)) {
                for (Galaxy g : galaxies) {
                    if (g.x - numColumnsAdded > x) {
                        g.x += expansionFactor;
                    }
                }
                numColumnsAdded += expansionFactor;
            }
        }
    }

    private Long calculateTotalManhattanDistance() {
        long total = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                total += Math.abs(galaxies.get(i).x - galaxies.get(j).x) + Math.abs(galaxies.get(i).y - galaxies.get(j).y);
            }
        }
        return total;
    }

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        findGalaxies(inputs, part1);
        Long totalDistance = calculateTotalManhattanDistance();
        return totalDistance.toString();
    }

    private class Galaxy {
        long x;
        long y;

        public Galaxy (long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
}
