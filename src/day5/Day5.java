package day5;

import java.util.ArrayList;
import java.util.HashSet;

public class Day5 {
    private ArrayList<MapNode> seedToSoilMap, soilToFertilizerMap, fertilizerToWaterMap, waterToLightMap, lightToTemperatureMap, temperatureToHumidityMap, humidityToLocationMap;
    private HashSet<Long> seeds;
    private Long part2;
    private final String SEED_TO_SOIL = "seed-to-soil";
    private final String SOIL_TO_FERTILIZER = "soil-to-fertilizer";
    private final String FERTILIZER_TO_WATER = "fertilizer-to-water";
    private final String WATER_TO_LIGHT = "water-to-light";
    private final String LIGHT_TO_TEMPERATURE = "light-to-temperature";
    private final String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity";
    private final String HUMIDITY_TO_LOCATION = "humidity-to-location";

    public Day5(ArrayList<String> inputs) {
        buildSeedsList(inputs.get(0));
        seedToSoilMap = buildXToYMapNodes(inputs, findFirstIndexMatchingString(inputs, SEED_TO_SOIL) + 1);
        soilToFertilizerMap = buildXToYMapNodes(inputs, findFirstIndexMatchingString(inputs, SOIL_TO_FERTILIZER) + 1);
        fertilizerToWaterMap = buildXToYMapNodes(inputs, findFirstIndexMatchingString(inputs, FERTILIZER_TO_WATER) + 1);
        waterToLightMap = buildXToYMapNodes(inputs, findFirstIndexMatchingString(inputs, WATER_TO_LIGHT) + 1);
        lightToTemperatureMap = buildXToYMapNodes(inputs, findFirstIndexMatchingString(inputs, LIGHT_TO_TEMPERATURE) + 1);
        temperatureToHumidityMap = buildXToYMapNodes(inputs, findFirstIndexMatchingString(inputs, TEMPERATURE_TO_HUMIDITY) + 1);
        humidityToLocationMap = buildXToYMapNodes(inputs, findFirstIndexMatchingString(inputs, HUMIDITY_TO_LOCATION) + 1);
        buildSeedsListWithRanges(inputs.get(0));
    }

    public Long part1() {
        Long currentLowest = Long.MAX_VALUE;
        for (Long seed : seeds) {
            Long seedValue = traverse(traverse(traverse(traverse(traverse(traverse(traverse(seed, seedToSoilMap), soilToFertilizerMap), fertilizerToWaterMap), waterToLightMap), lightToTemperatureMap), temperatureToHumidityMap), humidityToLocationMap);
            if (seedValue < currentLowest) {
                currentLowest = seedValue;
            }
        }
        return currentLowest;
    }

    public Long part2() {
        return part2;
    }

    private void buildSeedsList(String input) {
        seeds = new HashSet<>();
        for (String seed : input.split("\\s+")) {
            if (seed.matches("[0-9]+")) {
                seeds.add(Long.parseLong(seed));
            }
        }
    }

    private void buildSeedsListWithRanges(String input) {
        Long currentLowest = Long.MAX_VALUE;
        String[] digits = input.split("\\s+");
        for (int i = 1; i < digits.length; i+=2) {
            long startRange = Long.parseLong(digits[i]);
            long endRange = Long.parseLong(digits[i + 1]);
            for (long j = startRange; j < startRange + endRange; j++) {
                Long seedValue = traverse(traverse(traverse(traverse(traverse(traverse(traverse(j, seedToSoilMap), soilToFertilizerMap), fertilizerToWaterMap), waterToLightMap), lightToTemperatureMap), temperatureToHumidityMap), humidityToLocationMap);
                if (seedValue < currentLowest) {
                    currentLowest = seedValue;
                }
            }
        }
        part2 = currentLowest;
    }

    private Integer findFirstIndexMatchingString(ArrayList<String> inputs, String identifier) {
        return inputs.stream().filter(v -> v.contains(identifier)).map(v -> inputs.indexOf(v)).findFirst().orElse(-1);
    }

    private ArrayList<MapNode> buildXToYMapNodes(ArrayList<String> inputs, Integer startIndex) {
        var currentIndex = startIndex;
        var resultMap = new ArrayList<MapNode>();
        while (currentIndex < inputs.size() && inputs.get(currentIndex).matches(".*\\d.*")) {
            String[] digits = inputs.get(currentIndex).split("\\s+");
            Long source = Long.parseLong(digits[1]);
            Long destination = Long.parseLong(digits[0]);
            Long range = Long.parseLong(digits[2]);
            resultMap.add(new MapNode(source, destination, range));
            currentIndex++;
        }
        return resultMap;
    }

    private static Long traverse(Long start, ArrayList<MapNode> mapNodes) {
        long result = start;
        for (MapNode node : mapNodes) {
            result = node.getMappedValue(start);
            if (result != start) {
                return result;
            }
        }
        return start;
    }

    private class MapNode {
        Long source;
        Long offset;
        Long range;

        public MapNode(Long source, Long destination, Long range) {
            this.source = source;
            this.offset = destination - source;
            this.range = range;
        }

        public Long getMappedValue(Long input) {
            if (input < this.source) {
                return input;
            }
            if (input < this.source + this.range) {
                return Math.abs(input + offset);
            }
            return input;
        }
    }
}
