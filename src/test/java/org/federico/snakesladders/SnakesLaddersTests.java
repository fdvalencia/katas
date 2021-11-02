package org.federico.snakesladders;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SnakesLaddersTests {

    private static final String PLAYER_ONE_MESSAGE = "Player 1 is on square ";
    private static final String PLAYER_TWO_MESSAGE = "Player 2 is on square ";

    @Test
    void first_throw_is_player_one_turn() {
        SnakesLadders game = new SnakesLadders();

        assertThat(game.play(1, 2)).isEqualTo(PLAYER_ONE_MESSAGE + 3);
    }

    @Test
    void second_throw_is_player_two_turn() {
        SnakesLadders game = new SnakesLadders();

        game.play(1, 2);

        assertThat(game.play(1, 2)).isEqualTo(PLAYER_TWO_MESSAGE + 3);
    }

    @Test
    void third_throw_is_player_one() {
        SnakesLadders game = new SnakesLadders();

        game.play(1, 2);
        game.play(1, 2);

        assertThat(game.play(1, 2)).isEqualTo(PLAYER_ONE_MESSAGE + 6);
    }

    @Test
    void two_dice_with_same_value_makes_player_for_second_have_a_second_turn() {
        SnakesLadders game = new SnakesLadders();

        game.play(1, 1);

        assertThat(game.play(1, 2)).isEqualTo(PLAYER_ONE_MESSAGE + 41);
    }

    @Test
    void player_one_wins_and_player_two_looses() {
        SnakesLadders game = new SnakesLadders();

        game.play(45, 45);

        assertThat(game.play(4, 6)).isEqualTo("Player 1 Wins!");
        assertThat(game.play(1, 2)).isEqualTo("Game over!");
    }

    @Test
    void player_wins_on_double_turn() {
        SnakesLadders game = new SnakesLadders();

        game.play(45, 45);

        assertThat(game.play(5, 5)).isEqualTo("Player 1 Wins!");
        assertThat(game.play(1, 2)).isEqualTo("Game over!");
    }


    @Test
    void bounce_back() {
        SnakesLadders game = new SnakesLadders();

        game.play(45, 45);

        assertThat(game.play(6, 6)).isEqualTo(PLAYER_ONE_MESSAGE + 98);
    }

    @Test
    void bounce_back_to_ladder() {
        SnakesLadders game = new SnakesLadders();

        game.play(45, 45);

        assertThat(game.play(6, 5)).isEqualTo(PLAYER_ONE_MESSAGE + 80);
    }

    @Test
    public void exampleTests() {
        SnakesLadders game = new SnakesLadders();

        assertThat(game.play(1, 1)).isEqualTo("Player 1 is on square 38");
        assertThat(game.play(1, 5)).isEqualTo("Player 1 is on square 44");
        assertThat(game.play(6, 2)).isEqualTo("Player 2 is on square 31");
        assertThat(game.play(1, 1)).isEqualTo("Player 1 is on square 25");
    }

}
