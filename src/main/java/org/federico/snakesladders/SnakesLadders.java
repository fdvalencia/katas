package org.federico.snakesladders;

import java.util.Arrays;
import java.util.List;

public class SnakesLadders {
    private final Player playerOne = new Player("Player 1");
    private final Player playerTwo = new Player("Player 2");
    private final List<Player> players = Arrays.asList(playerOne, playerTwo);
    private int playerTurn = 0;
    private boolean gameOver = false;

    public String play(int diceOne, int diceTwo) {
        if (isGameOver()) {
            return gameIsOverMessage();
        } else {
            return movePLayer(diceOne, diceTwo);
        }
    }

    private boolean isGameOver() {
        return gameOver;
    }

    private String gameIsOverMessage() {
        return "Game over!";
    }

    private String movePLayer(int diceOne, int diceTwo) {
        Player currentPlayer = players.get(playerTurn);
        currentPlayer.move(diceOne + diceTwo);
        playerTurn = nextPlayerTurn(diceOne, diceTwo, players.size());
        gameOver = currentPlayer.hasWon();
        return currentPlayer.playerPosition();
    }

    private int nextPlayerTurn(int diceOne, int diceTwo, int numberOfPlayers) {
        boolean playerRepeatsTurn = diceOne == diceTwo;
        if (playerRepeatsTurn) {
            return playerTurn;
        } else {
            return nextPlayerTurnOrBackToFirstPlayerTurn(numberOfPlayers);
        }
    }

    private int nextPlayerTurnOrBackToFirstPlayerTurn(int numberOfPlayers) {
        boolean firstPlayerTurn = playerTurn == numberOfPlayers - 1;
        if (firstPlayerTurn)
            return 0;
        else
            return playerTurn + 1;
    }

    private class Player {
        private final String name;
        private final Board board = new Board();

        private boolean hasWon = false;
        private int position = 0;

        private Player(String name) {
            this.name = name;
        }

        public String playerPosition() {
            if (position < board.lastSquarer()) {
                return name + " is on square " + position;
            } else {
                return name + " Wins!";
            }
        }

        public void move(int nPositions) {
            int possiblePosition = board.position(position + nPositions);
            if (possiblePosition > board.lastSquarer()) {
                position = board.position(board.lastSquarer() - (possiblePosition - board.lastSquarer()));
            } else {
                position = possiblePosition;
            }
            setHasWon(position == board.lastSquarer());
        }

        private void setHasWon(boolean playerHasWon) {
            hasWon = playerHasWon;
        }

        public boolean hasWon() {
            return hasWon;
        }
    }

    private class Board {
        private final List<Square> specialSquare = rules();
        private final static int lastSquare = 100;

        private List<Square> rules() {

            Square ladderPositionTwo = new Square(2, 38);
            Square ladderPositionSeven = new Square(7, 14);
            Square ladderPositionEight = new Square(8, 31);
            Square ladderPositionFifteen = new Square(15, 26);
            Square ladderPositionTwentyOne = new Square(21, 42);
            Square ladderPositionTwentyEight = new Square(28, 84);
            Square ladderPositionThirtySix = new Square(36, 44);
            Square ladderPositionFiftyOne = new Square(51, 67);
            Square ladderPositionSeventyOne = new Square(71, 91);
            Square ladderPositionSeventyEight = new Square(78, 98);
            Square ladderPositionEightySeven = new Square(87, 94);

            Square snakePositionSixteen = new Square(16, 6);
            Square snakePositionFortySix = new Square(46, 25);
            Square snakePositionFortyNine = new Square(49, 11);
            Square snakePositionSixtyTwo = new Square(62, 19);
            Square snakePositionFortyFour = new Square(64, 60);
            Square snakePositionSeventyFour = new Square(74, 53);
            Square snakePositionEightyNine = new Square(89, 68);
            Square snakePositionNinetyTwo = new Square(92, 88);
            Square snakePositionNinetyFive = new Square(95, 75);
            Square snakePositionNinetyNine = new Square(99, 80);

            return Arrays.asList(ladderPositionTwo, ladderPositionSeven, ladderPositionEight,
                    ladderPositionFifteen, ladderPositionTwentyEight, ladderPositionThirtySix,
                    ladderPositionFiftyOne, ladderPositionSeventyOne, ladderPositionEightySeven,
                    ladderPositionTwentyOne, ladderPositionSeventyEight,

                    snakePositionSixteen, snakePositionFortySix, snakePositionFortyNine, snakePositionSixtyTwo,
                    snakePositionFortyFour, snakePositionSeventyFour, snakePositionEightyNine, snakePositionNinetyTwo,
                    snakePositionNinetyFive, snakePositionNinetyNine);
        }

        public int position(int position) {
            return specialSquare.stream()
                    .filter(square -> square.getOrigin() == position)
                    .findFirst()
                    .map(Square::getDestination)
                    .orElse(position);
        }

        public int lastSquarer() {
            return lastSquare;
        }
    }

    private class Square {
        private final int origin;
        private final int destination;

        public Square(int origin, int destination) {
            this.origin = origin;
            this.destination = destination;
        }

        public int getOrigin() {
            return origin;
        }

        public int getDestination() {
            return destination;
        }
    }
}
