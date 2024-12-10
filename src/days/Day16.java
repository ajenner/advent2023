package days;

import templates.DayTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class Day16 extends DayTemplate {

    private ArrayList<ArrayList<Tile>> map;

    private void buildGrid (ArrayList<String> inputs) {
        map = new ArrayList<>();
        for (int y = 0; y < inputs.size(); y++) {
            map.add(y, new ArrayList<>());
            for (int x = 0; x < inputs.get(y).length(); x++) {
                map.get(y).add(x, new Tile(x, y, inputs.get(y).charAt(x)));
            }
        }
    }

    private void beamTravel (int x, int y, Direction d, HashMap<Tile, ArrayList<Direction>> visited) {
        if (y < 0 || y >= map.size() || x < 0 || x >= map.get(y).size()) {
            return;
        }
        Tile currentTile = map.get(y).get(x);
        if (visited.get(currentTile) != null) {
            if (visited.get(currentTile).contains(d)) {
                return;
            }
        } else {
            visited.put(currentTile, new ArrayList<>());
        }
        visited.get(currentTile).add(d);
        currentTile.energized++;
        d = currentTile.convertDirection(d);
        switch (d) {
            case NORTH_AND_SOUTH:
                beamTravel(x, y - 1, Direction.NORTH, visited);
                beamTravel(x, y + 1, Direction.SOUTH, visited);
                break;
            case EAST_AND_WEST:
                beamTravel(x - 1, y, Direction.WEST, visited);
                beamTravel(x + 1, y, Direction.EAST, visited);
                break;
            case NORTH:
                beamTravel(x, y - 1, d, visited);
                break;
            case EAST:
                beamTravel(x + 1, y, d, visited);
                break;
            case SOUTH:
                beamTravel(x, y + 1, d, visited);
                break;
            case WEST:
                beamTravel(x - 1, y, d, visited);
                break;
        }
    }

    private Integer countEnergy() {
        int total = 0;
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                total += (map.get(y).get(x).energized > 0) ? 1 : 0;
                map.get(y).get(x).energized = 0;
            }
        }
        return total;
    }

    private Integer quantumBeamCounter() {
        int maxEnergy = 0;
        int energy;
        for (int y = 0; y < map.size(); y++) {
            beamTravel(0, y, Direction.EAST, new HashMap<>());
            energy = countEnergy();
            if (energy > maxEnergy) {
                maxEnergy = energy;
            }
            beamTravel(map.get(y).size() - 1, y, Direction.WEST, new HashMap<>());
            energy = countEnergy();
            if (energy > maxEnergy) {
                maxEnergy = energy;
            }
        }
        for (int x = 0; x < map.get(0).size(); x++) {
            beamTravel(x, 0, Direction.SOUTH, new HashMap<>());
            energy = countEnergy();
            if (energy > maxEnergy) {
                maxEnergy = energy;
            }
            beamTravel(x, map.get(0).size() - 1, Direction.NORTH, new HashMap<>());
            energy = countEnergy();
            if (energy > maxEnergy) {
                maxEnergy = energy;
            }
        }
        return maxEnergy;
    }

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        buildGrid(inputs);

        if (part1) {
            beamTravel(0, 0, Direction.EAST, new HashMap<>());
            return countEnergy().toString();
        }
        return quantumBeamCounter().toString();
    }

    private enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
        NORTH_AND_SOUTH,
        EAST_AND_WEST,
    }

    private class Tile {
        int x;
        int y;
        char value;
        int energized;

        public Tile(int x, int y, char value) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.energized = 0;
        }

        public Direction convertDirection (Direction d) {
            if (value == '.') {
                return d;
            }
            if (value == '|') {
                if (d == Direction.NORTH || d == Direction.SOUTH) {
                    return d;
                }
                return Direction.NORTH_AND_SOUTH;
            }
            if (value == '-') {
                if ((d == Direction.EAST || d == Direction.WEST)) {
                    return d;
                }
                return Direction.EAST_AND_WEST;
            }
            if (value == '/') {
                if (d == Direction.NORTH) {
                    return Direction.EAST;
                }
                if (d == Direction.EAST) {
                    return Direction.NORTH;
                }
                if (d == Direction.SOUTH) {
                    return Direction.WEST;
                }
                if (d == Direction.WEST) {
                    return Direction.SOUTH;

                }
            }
            if (value == '\\') {
                if (d == Direction.NORTH) {
                    return Direction.WEST;
                }
                if (d == Direction.EAST) {
                    return Direction.SOUTH;
                }
                if (d == Direction.SOUTH) {
                    return Direction.EAST;
                }
                if (d == Direction.WEST) {
                    return Direction.NORTH;
                }
            }
            return null;
        }
    }

    @Override
    public boolean exclude() {
        return true;
    }
}
