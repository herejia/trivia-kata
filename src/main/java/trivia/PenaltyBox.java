package trivia;

import trivia.announcement.AnnouncePrinter;
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

    void detain(Player playerToDetain, AnnouncePrinter announcePrinter) {
        playerToDetain.announceSentInPenaltyBox(announcePrinter);
        players.add(playerToDetain);
    }

    void playerMoves(Roll roll, Player movingPlayer, GameController game) {
        if (detains(movingPlayer)) {
            roll.detainedPlayerMoves(movingPlayer, game, this);
        } else {
            game.freePlayerMoves(movingPlayer, roll);
        }
    }

    void detainedPlayerRolledOdd(Player player, GameController gameController, Roll roll) {
        gameController.playerLeavesPenaltyBox(player, roll);
    }

    void detainedPlayerRolledEven(Player detainedPlayer, GameController gameController) {
        gameController.playerStaysInPenaltyBox(detainedPlayer);
    }

    void playerAnswersCorrectly(Game game, Player answeringPlayer, Roll roll) {
        if (detains(answeringPlayer)) {
            game.detainedPlayerAnswersCorrectly(roll);
        } else {
            game.freePlayerAnswersCorrectly();
        }
    }
}
