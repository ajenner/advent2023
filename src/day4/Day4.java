package day4;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Day4 {

    class Card {
        Integer copies;
        String value;
        public Card(Integer copies, String value) {
            this.copies = copies;
            this.value = value;
        }
    }

    public static Integer part1(ArrayList<String> inputs) {
        int result = 0;
        for (String input : inputs) {
            result += parseInput(input);
        }
        return result;
    }

    public Integer part2(ArrayList<String> inputs) {
        int index = 0;
        ArrayList<Card> cards = buildCardList(inputs);
        for (Card currentCard : cards) {
            int numberOfWins = 0;
            String input = currentCard.value;
            input = input.split(":")[1];
            String[] scratchCardNumbers = input.split("\\|");
            Set<Integer> winningSet = new HashSet<Integer>();
            for (String number : scratchCardNumbers[0].split("\\s+")) {
                if (!number.equals("")) {
                    winningSet.add(Integer.parseInt(number));
                }
            }
            for (String number : scratchCardNumbers[1].split("\\s+")) {
                if (!number.equals("") && winningSet.contains(Integer.parseInt(number))) {
                    numberOfWins++;
                    if(!(numberOfWins + index > cards.size())) {
                        Card cardToCopy = cards.get(numberOfWins + index);
                        cardToCopy.copies += currentCard.copies;
                        cards.set(numberOfWins + index, cardToCopy);
                    }
                }
            }
            index++;
        }
        return sumCardCopies(cards);
    }

    public static int parseInput(String input) {
        int result = 0;
        input = input.split(":")[1];
        String[] scratchCardNumbers = input.split("\\|");
        Set<Integer> winningSet = new HashSet<Integer>();
        for (String number : scratchCardNumbers[0].split("\\s+")) {
            if (!number.equals("")) {
                winningSet.add(Integer.parseInt(number));
            }
        }
        for (String number : scratchCardNumbers[1].split("\\s+")) {
            if (!number.equals("") && winningSet.contains(Integer.parseInt(number))) {
                if (result == 0) {
                    result = 1;
                } else {
                    result *= 2;
                }
            }
        }
        return result;
    }

    private ArrayList<Card> buildCardList(ArrayList<String> inputs) {
        ArrayList<Card> result = new ArrayList<Card>();
        for (String input : inputs) {
            result.add(new Card(1, input));
        }
        return result;
    }

    private Integer sumCardCopies(ArrayList<Card> cards) {
        int result = 0;
        for (Card card : cards) {
            result += card.copies;
        }
        return result;
    }
}