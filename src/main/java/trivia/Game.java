package trivia;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.OutputStreamAnnouncePrinter;
import trivia.move.DetainedPlayerMove;
import trivia.move.FreePlayerMove;
import trivia.move.OnLeavePlayerMove;
import trivia.player.*;
import trivia.questions.*;

import java.io.PrintStream;

@Deprecated
public class Game {
    private final AnnouncePrinter announcePrinter;
    private final PrintStream outputStream;
    private final InGamePlayers inGamePlayers;
    private final PenaltyBox penaltyBox;
    private final QuestionsDeck questionsDeck;

    int currentPlayerIndex = 0;

    public Game(PrintStream outputStream) {
        this.outputStream = outputStream;
        announcePrinter = new OutputStreamAnnouncePrinter(outputStream);
        inGamePlayers = new InGamePlayers(announcePrinter);
        penaltyBox = new PenaltyBox();
        questionsDeck = new QuestionsDeck();
    }

    public void add(String playerName) {
        inGamePlayers.newPlayer(playerName);
    }

    public void roll(Roll roll) {
        currentPlayerAnnouncer().announcePlayerAsCurrent(announcePrinter);

        announceRoll(roll);
        Player currentPlayer = getCurrentPlayer();
        penaltyBox.playerMoves(roll, currentPlayer, new DetainedPlayerMove(this), new FreePlayerMove(this));
    }

    private void freePlayerMove(Roll roll) {
        getCurrentPlayer().move(roll.intValue());
        announceCategory();
        askQuestion();
    }

    public void dicesHaveBeenRolled(Roll roll) {
        getCurrentPlayer().move(roll.intValue());
        announceCategory();
        askQuestion();
    }

    private void detainedPlayerMove(Roll roll) {
        if (roll.isOdd()) {
            currentPlayerAnnouncer().announceIsGettingOutOfPenaltyBox(announcePrinter);
            freePlayerMove(roll);
        } else {
            currentPlayerAnnouncer().announceIsNotGettingOutOfThePenaltyBox(announcePrinter);
        }
    }

    private void announceCategory() {
        new CategoryAnnouncer(getCurrentCategory()).announceCategory(announcePrinter);
    }

    private Category getCurrentCategory() {
        return getCurrentPlayer().getPlace().getCategory();
    }

    private Player getCurrentPlayer() {
        return inGamePlayers.getPlayerByIndex(currentPlayerIndex);
    }

    private PlayerAnnouncer currentPlayerAnnouncer() {
        return new PlayerAnnouncer(getCurrentPlayer());
    }

    private void announceRoll(Roll roll) {
        outputStream.println("They have rolled a " + roll);
    }

    private void askQuestion() {
        CategoryDeck questionDeck = getCurrentCategoryQuestionsDeck();
        Question card = questionDeck.getTop();
        outputStream.println(card);
        questionDeck.discardTop();
    }

    private CategoryDeck getCurrentCategoryQuestionsDeck() {
        Category category = getCurrentCategory();
        return questionsDeck.getDeck(category);
    }

    public boolean wasCorrectlyAnswered(Roll roll) {
        if (penaltyBox.detains(getCurrentPlayer())) {
            if (roll.isOdd()) {
                announceCorrectAnswer();
                incrementPlayerGold();
                announceCurrentPlayerGoldCoins();
                boolean winner = didCurrentPlayerWin();
                nextPlayer();
                return winner;
            } else {
                nextPlayer();
                return true;
            }
        } else {
            announceAnswerWasCorrent();
            incrementPlayerGold();
            announceCurrentPlayerGoldCoins();
            boolean winner = didCurrentPlayerWin();
            nextPlayer();
            return winner;
        }
    }

    private void announceAnswerWasCorrent() {
        outputStream.println("Answer was corrent!!!!");
    }

    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == inGamePlayers.count().intValue()) currentPlayerIndex = 0;
    }

    private void announceCurrentPlayerGoldCoins() {
        currentPlayerAnnouncer().announceGoldAmount(announcePrinter);
    }

    private void incrementPlayerGold() {
        getCurrentPlayer().incrementGoldAmount();
    }

    private void announceCorrectAnswer() {
        outputStream.println("Answer was correct!!!!");
    }

    public boolean wrongAnswer() {
        announceWrongAnswer();
        currentPlayerAnnouncer().announceSentInPenaltyBox(announcePrinter);
        penaltyBox.detain(getCurrentPlayer());

        nextPlayer();
        return true;
    }

    private void announceWrongAnswer() {
        outputStream.println("Question was incorrectly answered");
    }


    private boolean didCurrentPlayerWin() {
        return !getCurrentPlayer().hasWinningGoldAmount();
    }

    public void onLeavePlayerRolled() {
        currentPlayerAnnouncer().announceIsGettingOutOfPenaltyBox(announcePrinter);
    }

    public void playerStaysIn() {
        currentPlayerAnnouncer().announceIsNotGettingOutOfThePenaltyBox(announcePrinter);
    }
}
