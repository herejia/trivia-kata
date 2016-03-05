package trivia.move;

import trivia.Game;
import trivia.Roll;

public class OnLeavePlayerMove extends FreePlayerMove {
    private Game game;

    public OnLeavePlayerMove(Game game) {
        super(game);
        this.game = game;
    }

    @Override
    public void move(Roll roll) {
        game.onLeavePlayerRolled();
        super.move(roll);
    }

    public void stayIn() {
        game.playerStaysIn();
    }
}
