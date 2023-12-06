package day6;

import java.util.ArrayList;

public class Day6 {

    ArrayList<String> inputs;

    public Day6(ArrayList<String> inputs) {
        this.inputs = inputs;
    }

    private ArrayList<Race> buildRaceList() {
        ArrayList<Race> races = new ArrayList<>();
        String[] times = inputs.get(0).trim().split("\\s+");
        String[] distances = inputs.get(1).trim().split("\\s+");
        for (int i = 1; i < times.length; i++) {
            races.add(new Race(Long.parseLong(times[i]), Long.parseLong(distances[i])));
        }
        return races;
    }

    private Race buildLongRace() {
        String time = inputs.get(0).replaceAll("[\\D]", "");
        String distance = inputs.get(1).replaceAll("[\\D]", "");
        return new Race(Long.parseLong(time), Long.parseLong(distance));
    }

    public Integer part1(){
        ArrayList<Race> races = buildRaceList();
        int product = 1;
        for (Race race : races) {
            product *= race.computeLeeway();
        }
        return product;
    }

    public Long part2() {
        return buildLongRace().computeLeeway();
    }

    private class Race {
        Long time;
        Long distance;

        public Race (Long time, Long distance) {
            this.time = time;
            this.distance = distance;
        }

        public long computeLeeway() {
            int successful = 0;
            for (long i = 0; i < this.time; i++) {
                if (computeDistance(i) > distance) {
                    successful++;
                }
            }
            return successful;
        }

        private long computeDistance(long i) {
            return i * (this.time - i);
        }
    }
}
