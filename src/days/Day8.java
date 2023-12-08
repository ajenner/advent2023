package days;

import templates.DayTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 extends DayTemplate {

    Tree tree;

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        String[] instructions = inputs.get(0).split("");
        buildTree(inputs);
        Long steps = 0L;
        if (part1) {
            steps = traverse(instructions);
        } else {
            steps = ghostWalk(instructions);
        }
        return steps;
    }

    private Long traverse(String[] instructions) {
        String goal = "ZZZ";
        String currentNodeName = "AAA";
        Integer steps = 0;
        while (!currentNodeName.equals(goal)) {
            for (String instruction : instructions) {
                if (!currentNodeName.equals(goal)) {
                    steps++;
                    if (instruction.equals("L")) {
                        currentNodeName = tree.nodes.get(currentNodeName).left.value;
                    } else {
                        currentNodeName = tree.nodes.get(currentNodeName).right.value;
                    }
                }
            }
        }
        return (long) steps;
    }

    private Long ghostWalk(String[] instructions) {
        boolean reachedGoal = false;
        Integer steps = 0;
        ArrayList<Node> currentNodes = tree.getAllANodes();
        long[] cycles = new long[currentNodes.size()];
        while (!reachedGoal) {
            for (String instruction : instructions) {
                if (!reachedGoal) {
                    steps++;
                    if (instruction.equals("L")) {
                        for (int i = 0; i < currentNodes.size(); i++) {
                            Node currentNode = currentNodes.get(i);
                            if (!currentNode.value.endsWith("Z")) {
                                currentNodes.set(i, currentNodes.get(i).left);
                                if (currentNode.left.value.endsWith("Z")){
                                        cycles[i] = steps;
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < currentNodes.size(); i++) {
                            Node currentNode = currentNodes.get(i);
                            if (!currentNode.value.endsWith("Z")) {
                                currentNodes.set(i, currentNodes.get(i).right);
                                if (currentNode.right.value.endsWith("Z")){
                                    cycles[i] = steps;
                                }
                            }
                        }
                    }
                    reachedGoal = checkGoalCondition(cycles);
                }
            }
        }
        return lcm(Arrays.stream(cycles).boxed().collect(Collectors.toList()));
    }

    private long lcm(List<Long> numbers) {
        return numbers.stream().reduce(1L, (x, y) -> (x * y) / gcd(x, y));
    }

    private long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    private boolean checkGoalCondition(long[] cycles) {
        for (long i : cycles) {
            if (i == 0L) {
                return false;
            }
        }
        return true;
    }

    private void buildTree(ArrayList<String> inputs) {
        tree = new Tree();
        for (int i = 2; i < inputs.size(); i++) {
            String[] line = inputs.get(i).split(" ");
            String name = line[0];
            String left = line[2].replaceAll("[^A-Za-z0-9]", "");
            String right = line[3].replaceAll("[^A-Za-z0-9]", "");
            tree.addNode(name, left, right);
        }
    }

    private class Tree {
        HashMap<String, Node> nodes;
        String root;

        public Tree () {
            nodes = new HashMap<>();
        }

        public void addNode(String nodeName, String left, String right) {
            if (nodes.isEmpty()) {
                root = nodeName;
            }
            if (nodes.get(nodeName) == null) {
                Node newNode = new Node(nodeName);
                nodes.put(nodeName, newNode);
            }
            Node leftNode = nodes.get(left);
            if (leftNode == null) {
                leftNode = new Node(left);
                nodes.put(left, leftNode);
            }
            nodes.get(nodeName).left = leftNode;

            Node rightNode = nodes.get(right);
            if (rightNode == null) {
                rightNode = new Node(right);
                nodes.put(right, rightNode);
            }
            nodes.get(nodeName).right = rightNode;
        }

        private ArrayList<Node> getAllANodes() {
            ArrayList<Node> aNodes = new ArrayList<>();
            for (Node node : nodes.values()) {
                if (node.value.endsWith("A")){
                    aNodes.add(node);
                }
            }
            return aNodes;
        }
    }

    private class Node {
        String value;
        Node left;
        Node right;

        public Node (String value) {
            this.value = value;
        }

        public Node (String value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}
