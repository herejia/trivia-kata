package trivia;

import trivia.player.Player;
import trivia.player.Players;

final class PenaltyBox {
    private final Players players;

    PenaltyBox() {
        players = new Players();
    }

    private boolean detains(Player player) {
        return players.contains(player);
    }

    void detain(Player currentPlayer) {
        players.add(currentPlayer);
    }

    void playerMoves(Roll roll, Player movingPlayer, Game game) {
        if (detains(movingPlayer)) {
            roll.detainedPlayerMoves(movingPlayer, game, this);
        } else {
            game.freePlayerMoves(movingPlayer, roll);
        }
    }

    void detainedPlayerRolledOdd(Player player, Game game, Roll roll) {
        game.playerLeavesPenaltyBox(player, roll);
    }

    void detainedPlayerRolledEven(Player detainedPlayer, Game game) {
        game.playerStaysIn(detainedPlayer);
    }

    boolean playerAnswersCorrectly(Game game, Player answeringPlayer, Roll roll) {
        if (detains(answeringPlayer)) {
            return game.detainedPlayerAnswersCorrectly(roll);
        } else {
            return game.freePlayerAnswersCorrectly();
        }
    }
}
