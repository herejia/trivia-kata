package trivia.move;

import trivia.Game;
import trivia.Roll;

public class DetainedPlayerMove implements PlayerMove {
    private Game game;

    public DetainedPlayerMove(Game game) {
        this.game = game;
    }

    @Override
    public void move(Roll roll) {
        roll.detainedPlayerMoves(new OnLeavePlayerMove(game));
    }
}
