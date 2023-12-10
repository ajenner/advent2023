package days;

import templates.DayTemplate;
import java.util.ArrayList;

public class Day10 extends DayTemplate {

    ArrayList<ArrayList<Pipe>> pipeMap;
    int startX;
    int startY;

    private void buildPipeMap(ArrayList<String> inputs) {
        pipeMap = new ArrayList<>();
        for (int y = 0; y < inputs.size(); y++) {
            pipeMap.add(y, new ArrayList<>());
            String[] splitLine = inputs.get(y).split("");
            for (int x = 0; x < splitLine.length; x++) {
                if (splitLine[x].equals("S")) {
                    startX = x;
                    startY = y;
                }
                pipeMap.get(y).add(new Pipe(splitLine[x], x, y));
            }
        }
    }

    private Integer traverseMap() {
        int currentX = startX;
        int currentY = startY;
        int steps = 0;
        while (true) {
            steps ++;
            //try walk north
            if (currentY > 0 && pipeMap.get(currentY).get(currentX).canGoNorth() && pipeMap.get(currentY - 1).get(currentX).canTraverse(0)) {
                currentY -= 1;
                continue;
            }
            //try walk east
            else if (currentX < pipeMap.get(currentY).size() - 1 && pipeMap.get(currentY).get(currentX).canGoEast() && pipeMap.get(currentY).get(currentX + 1).canTraverse(1)) {
                currentX += 1;
                continue;
            }
            //try walk south
            else if (currentY < pipeMap.size() - 1 && pipeMap.get(currentY).get(currentX).canGoSouth() && pipeMap.get(currentY + 1).get(currentX).canTraverse(2)) {
                currentY += 1;
                continue;
            }
            //try walk west
            else if (currentX > 0 && pipeMap.get(currentY).get(currentX).canGoWest() && pipeMap.get(currentY).get(currentX - 1).canTraverse(3)) {
                currentX -= 1;
                continue;
            }
            return steps / 2;
        }
    }

    private Integer countEnclosed() {
        boolean isEnclosed;
        int count = 0;
        for (int y = 0; y < pipeMap.size(); y++) {
            isEnclosed = false;
            for (int x = 0; x < pipeMap.get(y).size(); x++) {
                if (pipeMap.get(y).get(x).visited) {
                    PipeType type = pipeMap.get(y).get(x).type;
                    if (type == PipeType.VERTICAL || type == PipeType.NORTH_EAST || type == PipeType.NORTH_WEST || type == PipeType.ANIMAL) {
                        isEnclosed = !isEnclosed;
                    }
                } else {
                    if (isEnclosed) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        buildPipeMap(inputs);
        Integer loopLength = traverseMap();
        Integer enclosedTiles = countEnclosed();
        return (part1)? loopLength.toString() : enclosedTiles.toString();
    }

    private enum PipeType {
        VERTICAL,
        HORIZONTAL,
        NORTH_EAST,
        NORTH_WEST,
        SOUTH_WEST,
        SOUTH_EAST,
        GROUND,
        ANIMAL
    }

    private class Pipe {
        PipeType type;
        int x;
        int y;
        boolean visited;

        public Pipe(String type, int x, int y) {
            this.x = x;
            this.y = y;
            this.type = setPipeType(type);
            this.visited = this.type == PipeType.ANIMAL;
        }

        private PipeType setPipeType(String type) {
            switch (type) {
                case "|":
                    return PipeType.VERTICAL;
                case "-":
                    return PipeType.HORIZONTAL;
                case "L":
                    return PipeType.NORTH_EAST;
                case "J":
                    return PipeType.NORTH_WEST;
                case "7":
                    return PipeType.SOUTH_WEST;
                case "F":
                    return PipeType.SOUTH_EAST;
                case ".":
                    return PipeType.GROUND;
                case "S":
                    return PipeType.ANIMAL;
            }
            return null;
        }

        public boolean canTraverse(int direction) {
            if (this.visited) {
                return false;
            }
            //Walking North
            if (direction == 0) {
                if (this.canGoSouth()) {
                    this.visited = true;
                    return true;
                }
            }
            //Walking East
            if (direction == 1) {
                if (this.canGoWest()) {
                    this.visited = true;
                    return true;
                }
            }
            //Walking South
            if (direction == 2) {
                if (this.canGoNorth()) {
                    this.visited = true;
                    return true;
                }
            }
            //Walking West
            if (direction == 3) {
                if (this.canGoEast()) {
                    this.visited = true;
                    return true;
                }
            }
            return false;
        }

        public boolean canGoNorth() {
            return (type == PipeType.VERTICAL || type == PipeType.NORTH_EAST || type == PipeType.NORTH_WEST || type == PipeType.ANIMAL);
        }

        public boolean canGoEast() {
            return (type == PipeType.HORIZONTAL || type == PipeType.NORTH_EAST || type == PipeType.SOUTH_EAST || type == PipeType.ANIMAL);
        }

        public boolean canGoSouth() {
            return (type == PipeType.VERTICAL || type == PipeType.SOUTH_WEST || type == PipeType.SOUTH_EAST || type == PipeType.ANIMAL);
        }

        public boolean canGoWest() {
            return (type == PipeType.HORIZONTAL || type == PipeType.NORTH_WEST || type == PipeType.SOUTH_WEST || type == PipeType.ANIMAL);
        }
    }
}
