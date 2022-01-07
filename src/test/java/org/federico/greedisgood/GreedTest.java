package org.federico.greedisgood;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class GreedTest {

    @Test
    void greedy() {
        Assertions.assertThat(Greed.greedy(new int[]{5, 1, 3, 4, 1})).isEqualTo(250);
        Assertions.assertThat(Greed.greedy(new int[]{1, 1, 1, 3, 1})).isEqualTo(1100);
        Assertions.assertThat(Greed.greedy(new int[]{2, 4, 4, 5, 4})).isEqualTo(450);
    }
}