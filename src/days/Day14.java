package days;

import templates.DayTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class Day14 extends DayTemplate {

    ArrayList<char[]> mirrorMap;
    HashMap<String, Long> cachedStates;

    private void rollNorth(int x, int y) {
        if (y < 0) {
            return;
        }
        if (mirrorMap.get(y)[x] == 'O' || mirrorMap.get(y)[x] == '#') {
            return;
        }
        mirrorMap.get(y)[x] = 'O';
        mirrorMap.get(y + 1)[x] = '.';
        rollNorth(x, y - 1);
    }

    private void tiltNorth() {
        for (int x = 0; x < mirrorMap.get(0).length; x++) {
            for (int y = 1; y < mirrorMap.size(); y++) {
                if (mirrorMap.get(y)[x] == 'O') {
                    rollNorth(x, y - 1);
                }
            }
        }
    }

    private void rollWest(int x, int y) {
        if (x < 0) {
            return;
        }
        if (mirrorMap.get(y)[x] == 'O' || mirrorMap.get(y)[x] == '#') {
            return;
        }
        mirrorMap.get(y)[x] = 'O';
        mirrorMap.get(y)[x + 1] = '.';
        rollWest(x - 1, y);
    }


    private void tiltWest() {
        for (int x = 0; x < mirrorMap.get(0).length; x++) {
            for (int y = 0; y < mirrorMap.size(); y++) {
                if (mirrorMap.get(y)[x] == 'O') {
                    rollWest(x - 1, y);
                }
            }
        }
    }

    private void rollSouth(int x, int y) {
        if (y == mirrorMap.size()) {
            return;
        }
        if (mirrorMap.get(y)[x] == 'O' || mirrorMap.get(y)[x] == '#') {
            return;
        }
        mirrorMap.get(y)[x] = 'O';
        mirrorMap.get(y - 1)[x] = '.';
        rollSouth(x, y + 1);
    }

    private void tiltSouth() {
        for (int x = 0; x < mirrorMap.get(0).length; x++) {
            for (int y = mirrorMap.size() - 1; y >= 0; y--) {
                if (mirrorMap.get(y)[x] == 'O') {
                    rollSouth(x, y + 1);
                }
            }
        }
    }

    private void rollEast(int x, int y) {
        if (x == mirrorMap.get(y).length) {
            return;
        }
        if (mirrorMap.get(y)[x] == 'O' || mirrorMap.get(y)[x] == '#') {
            return;
        }
        mirrorMap.get(y)[x] = 'O';
        mirrorMap.get(y)[x - 1] = '.';
        rollEast(x + 1, y);
    }

    private void tiltEast() {
        for (int x = mirrorMap.get(0).length - 1; x >= 0; x--) {
            for (int y = 0; y < mirrorMap.size(); y++) {
                if (mirrorMap.get(y)[x] == 'O') {
                    rollEast(x + 1, y);
                }
            }
        }
    }

    private void buildMirrorMap(ArrayList<String> inputs) {
        mirrorMap = new ArrayList<>();
        for (String line : inputs) {
            mirrorMap.add(line.toCharArray());
        }
    }

    private Integer calculateLoad() {
        int total = 0;
        for (int y = 0; y < mirrorMap.size(); y++) {
            for (int x = 0; x < mirrorMap.get(0).length; x++) {
                if (mirrorMap.get(y)[x] == 'O') {
                    total += mirrorMap.size() - y;
                }
            }
        }
        return total;
    }

    private String stringifyMap() {
        StringBuilder sb = new StringBuilder();
        for (char[] chars : mirrorMap) {
            sb.append(chars);
        }
        return sb.toString();
    }

    private Integer runCycles() {
        cachedStates = new HashMap<>();
        for (long i = 0; i < 1000000000; i++) {
            tiltNorth();
            tiltWest();
            tiltSouth();
            tiltEast();
            String mapState = stringifyMap();
            if (cachedStates.containsKey(mapState)) {
                long delta = i - cachedStates.get(mapState);
                i += delta * ((1000000000 - i) / delta);
            }
            cachedStates.put(mapState, i);
        }
        return calculateLoad();
    }


    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        buildMirrorMap(inputs);
        if (part1) {
            tiltNorth();
        }
        return (part1) ? calculateLoad().toString() : runCycles().toString();
    }
}
