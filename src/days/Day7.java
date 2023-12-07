package days;

import templates.DayTemplate;

import java.util.*;

public class Day7 extends DayTemplate {

    private ArrayList<Hand> hands;

    @Override
    public Object solve(boolean part1, ArrayList<String> inputs) {
        buildHands(part1, inputs);
        long bid = 0L;
        for (int i = 0; i < hands.size(); i++) {
            bid += (long) hands.get(i).bid * (i + 1);
        }
        return bid;
    }

    private void buildHands(boolean part1, ArrayList<String> inputs) {
        hands = new ArrayList<>();
        for (String input : inputs) {
            String[] splits = input.split(" ");
            hands.add(new Hand(splits[0], Integer.parseInt(splits[1]), part1));
        }
        Collections.sort(hands);
    }

    private class Hand implements Comparable {
        int bid;
        int strength;
        int[] frequencies = new int[13];
        int[] cardRanks = new int[5];
        String[] ranks;

        public Hand(String cards, int bid, boolean part1) {
            ranks = (part1)? new String[] {"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"} :
                new String[] {"A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J"};
            this.bid = bid;
            setStrength(cards, !part1);
        }

        private void setStrength(String cards, boolean useJokers) {
            int numJokers = 0;
            String[] card = cards.split("");
            for (int i = 0; i < card.length; i++) {
                for (int j = 0; j < ranks.length; j++) {
                    if (card[i].equals(ranks[j])) {
                        if (!card[i].equals("J") || !useJokers) {
                            frequencies[j]++;
                        }
                        if (card[i].equals("J")) {
                            numJokers++;
                        }
                        cardRanks[i] = j;
                    }
                }
            }
            Arrays.sort(frequencies);
            strength = (frequencies[frequencies.length - 1] + ((useJokers)? numJokers : 0)) * 2;
            //handle 3 of a kind/two pair edge case
            if (frequencies[frequencies.length - 2] == 2) {
                strength += 1;
            }
        }

        @Override
        public int compareTo(Object o) {
            Hand other = (Hand) o;
            if (strength != other.strength) {
                return strength - other.strength;
            } else {
                for (int i = 0; i < cardRanks.length; i++) {
                    if (cardRanks[i] != other.cardRanks[i]) {
                        return other.cardRanks[i] - cardRanks[i];
                    }
                }
                return 0;
            }
        }
    }
}
