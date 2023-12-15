package days;

import templates.DayTemplate;
import java.util.ArrayList;

public class Day15 extends DayTemplate {
    
    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        Integer result = 0;
        HashMap map = new HashMap();
        if (part1) {
            for (String str : inputs.get(0).split(",")) {
                result += hash(str);
            }
        } else {
            for (String str : inputs.get(0).split(",")) {
                if (str.contains("=")) {
                    String label = str.split("=")[0];
                    Integer focalLength = Integer.parseInt(str.split("=")[1]);
                    map.put(label, focalLength);
                } else {
                    String label = str.split("-")[0];
                    map.remove(label);
                }
            }
            result = map.focusingPower();
        }

        return result.toString();
    }

    public Integer hash (String str) {
        int total = 0;
        for (char c : str.toCharArray()) {
            total += c;
            total *= 17;
            total %= 256;
        }
        return total;
    }

    private class HashMap {
        Box<String, Integer>[] boxes;

        public HashMap () {
            boxes = new Box[256];
        }

        public void put(String label, Integer focalLength) {
            Integer hash = hash(label);
            Box<String, Integer> box = boxes[hash];
            if (box == null) {
                boxes[hash] = new Box<>(label, focalLength);
                return;
            }
            while (box != null) {
                if (box.key.equals(label)) {
                    box.value = focalLength;
                    return;
                }
                if (box.next == null) {
                    box.next = new Box<>(label, focalLength);
                }
                box = box.next;
            }
        }

        public void remove(String label) {
            Integer hash = hash(label);
            Box<String, Integer> node = boxes[hash];
            Box<String, Integer> prev = null;
            while (node != null) {
                if (node.key.equals(label)) {
                    if (prev == null) {
                        boxes[hash] = node.next;
                    } else {
                        prev.next = node.next;
                    }
                    return;
                }
                prev = node;
                node = node.next;
            }
        }

        public Integer focusingPower() {
            int total = 0;
            for (int i = 0; i < boxes.length; i++) {
                int boxTotal = i + 1;
                Box<String, Integer> currentBox = boxes[i];
                if (currentBox == null) {
                    continue;
                }
                int index = 1;
                while (currentBox != null) {
                    total += boxTotal * index * currentBox.value;
                    index++;
                    currentBox = currentBox.next;
                }
            }
            return total;
        }
    }

    private class Box <K, V> {
        K key;
        V value;
        Box<K, V> next;
        public Box (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
