package trivia;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.OutputStreamAnnouncePrinter;
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

    void add(String playerName) {
        inGamePlayers.newPlayer(playerName);
    }

    void roll(Roll roll) {
        currentPlayerAnnouncer().announcePlayerAsCurrent(announcePrinter);
        announceRoll(roll);
        penaltyBox.playerMoves(roll, getCurrentPlayer(), this);
    }

    void freePlayerMoves(Player byPlayer, Roll roll) {
        byPlayer.move(roll.intValue());
        announceCategory();
        askQuestion();
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
        return penaltyBox.playerAnswersCorrectly(this, getCurrentPlayer(), roll);
    }

    public boolean detainedPlayerAnswersCorrectly(Roll roll) {
        return roll.detainedPlayerAnswersCorrectly(this);
    }

    boolean detainedPlayerAnswersCorrectlyButRolledEven() {
        nextPlayer();
        return true;
    }

    boolean detainedPlayerAnswersCorrectlyAndRolledOdd() {
        announceCorrectAnswer();
        incrementPlayerGold();
        announceCurrentPlayerGoldCoins();
        boolean winner = didCurrentPlayerWin();
        nextPlayer();
        return winner;
    }

    public boolean freePlayerAnswersCorrectly() {
        announceAnswerWasCorrent();
        incrementPlayerGold();
        announceCurrentPlayerGoldCoins();
        boolean winner = didCurrentPlayerWin();
        nextPlayer();
        return winner;
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

    void playerLeavesPenaltyBox(Player detainedPlayer, Roll roll) {
        PlayerAnnouncer playerAnnouncer = new PlayerAnnouncer(detainedPlayer);
        playerAnnouncer.announceIsGettingOutOfPenaltyBox(announcePrinter);
        freePlayerMoves(detainedPlayer, roll);
    }

    void playerStaysIn(Player detainedPlayer) {
        PlayerAnnouncer playerAnnouncer = new PlayerAnnouncer(detainedPlayer);
        playerAnnouncer.announceIsNotGettingOutOfThePenaltyBox(announcePrinter);
    }
}
