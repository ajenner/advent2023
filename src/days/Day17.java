package days;

import common.Point;
import templates.DayTemplate;

import java.util.*;

public class Day17 extends DayTemplate {

    public int dijkstra(ArrayList<String> inputs, boolean part1) {
        var queue = new PriorityQueue<Entry>();
        var visited = new HashSet<CityBlock>();
        int endX = inputs.getFirst().length() - 1;
        int endY = inputs.size() - 1;

        CityBlock rightStart = new CityBlock(new Point(1, 0), 1, Point.Direction.RIGHT);
        CityBlock downStart = new CityBlock(new Point(0, 1), 1, Point.Direction.DOWN);
        queue.add(new Entry(rightStart, Character.getNumericValue(inputs.get(rightStart.p().y()).charAt(rightStart.p().x()))));
        queue.add(new Entry(downStart, Character.getNumericValue(inputs.get(downStart.p().y()).charAt(downStart.p().x()))));

        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (visited.contains(current.node)) {
                continue;
            }
            visited.add(current.node);
            if (current.node.p().x() == endX && current.node.p().y() == endY
                    && (part1 || current.node.currentSteps() >= 4)) {
                return current.heatLoss;
            }
            queue.addAll(part1 ? current.getNeighbours(inputs) : current.getPart2Neighbours(inputs));
        }
        return 0;
    }

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        return String.valueOf(dijkstra(inputs, part1));
    }
}

record CityBlock(Point p, int currentSteps, Point.Direction direction) {}

class Entry implements Comparable<Entry> {

    public CityBlock node;
    public int heatLoss;

    public Entry(CityBlock node, int heatLoss) {
        this.node = node;
        this.heatLoss = heatLoss;
    }

    public List<Entry> getNeighbours(ArrayList<String> inputs) {
        List<Entry> neighbours = new ArrayList<>();
        var left = getNextEntry(inputs, node.p().rotateDirectionLeft90Degrees(node.direction()));
        if (left != null) {
            neighbours.add(left);
        }

        var right = getNextEntry(inputs, node.p().rotateDirectionRight90Degrees(node.direction()));
        if (right != null) {
            neighbours.add(right);
        }

        if (node.currentSteps() < 3) {
            var straight = getNextEntry(inputs, node.direction());
            if (straight != null) {
                neighbours.add(straight);
            }
        }

        return neighbours;
    }

    public List<Entry> getPart2Neighbours(ArrayList<String> inputs) {
        List<Entry> neighbours = new ArrayList<>();
        if (node.currentSteps() >= 4) {
            var left = getNextEntry(inputs, node.p().rotateDirectionLeft90Degrees(node.direction()));
            if (left != null) {
                neighbours.add(left);
            }

            var right = getNextEntry(inputs, node.p().rotateDirectionRight90Degrees(node.direction()));
            if (right != null) {
                neighbours.add(right);
            }
        }

        if (node.currentSteps() < 10) {
            var straight = getNextEntry(inputs, node.direction());
            if (straight != null) {
                neighbours.add(straight);
            }
        }

        return neighbours;
    }

    private Entry getNextEntry(ArrayList<String> inputs, Point.Direction nextDirection) {
            var nextCityBlock = new CityBlock(node.p().moveDirection(nextDirection), node.direction() == nextDirection ? node.currentSteps() + 1 : 1, nextDirection);
            if (nextCityBlock.p().inBounds(inputs.getFirst().length(), inputs.size())) {
                return new Entry(nextCityBlock, this.heatLoss + Character.getNumericValue(inputs.get(nextCityBlock.p().y()).charAt(nextCityBlock.p().x())));
            }
            return null;
    }

    @Override
    public int compareTo(Entry o) {
        if (this.heatLoss != o.heatLoss) {
            return Integer.compare(this.heatLoss, o.heatLoss);
        } else if (this.node.direction() == o.node.direction() && this.node.currentSteps() != o.node.currentSteps()) {
            return Integer.compare(this.node.currentSteps(), o.node.currentSteps());
        } else if (this.node.p().y() != o.node.p().y()) {
            return Integer.compare(this.node.p().y(), o.node.p().y());
        } else {
            return Integer.compare(this.node.p().x(), o.node.p().x());
        }
    }
}