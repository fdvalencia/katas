package org.federico.greedisgood;

import java.util.Arrays;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


public class Greed {

    private static final int MAX_DICE_REPETITION = 3;

    public static int greedy(int[] dice) {
        Map<Integer, Long> groupDiceValueByRepetition = Arrays.stream(dice)
                .boxed()
                .collect(groupingBy(identity(), counting()));

        return groupDiceValueByRepetition.entrySet().stream().map(
                        diceValueByRepetition -> threeRepetitionDiceValue(diceValueByRepetition.getValue(), diceValueByRepetition.getKey()) +
                                residualThreeRepetitionDiceValue(diceValueByRepetition.getValue(), diceValueByRepetition.getKey()) +
                                lessThanThreeRepetitionDiceValue(diceValueByRepetition.getValue(), diceValueByRepetition.getKey()))
                .reduce(0L, Long::sum)
                .intValue();
    }

    private static int threeRepetitionDiceValue(Long diceRepetition, Integer diceValue) {
        if (diceRepetition >= MAX_DICE_REPETITION) {
            if (diceValue == 1) {
                return 1000;
            } else if (diceValue == 2) {
                return 200;
            } else if (diceValue == 3) {
                return 300;
            } else if (diceValue == 4) {
                return 400;
            } else if (diceValue == 5) {
                return 500;
            } else if (diceValue == 6) {
                return 600;
            } else return 0;
        } else
            return 0;
    }

    private static long residualThreeRepetitionDiceValue(Long diceRepetition, Integer diceValue) {
        long residual = diceRepetition - MAX_DICE_REPETITION;
        if (diceRepetition >= MAX_DICE_REPETITION && diceValue == 1) {
            return residual * 100;
        } else if (diceRepetition >= MAX_DICE_REPETITION && diceValue == 5) {
            return residual * 50;
        } else return 0;
    }

    private static long lessThanThreeRepetitionDiceValue(Long diceRepetition, Integer diceValue) {
        if (diceRepetition < MAX_DICE_REPETITION && diceValue == 1)
            return 100 * diceRepetition;
        else if (diceRepetition < MAX_DICE_REPETITION && diceValue == 5) {
            return 50 * diceRepetition;
        } else {
            return 0;
        }
    }
}
