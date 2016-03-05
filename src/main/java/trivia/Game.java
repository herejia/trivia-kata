package trivia;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.OutputStreamAnnouncePrinter;
import trivia.flow.VerdictListener;
import trivia.player.*;
import trivia.questions.*;

import java.io.PrintStream;

public class Game {
    private final AnnouncePrinter announcePrinter;
    private final PrintStream outputStream;
    private final InGamePlayers inGamePlayers;
    private final PenaltyBox penaltyBox;
    private final QuestionsDeck questionsDeck;
    int currentPlayerIndex = 0;
    private VerdictListener verdictListener;

    public Game(PrintStream outputStream, VerdictListener verdictListener) {
        this.outputStream = outputStream;
        this.verdictListener = verdictListener;
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

    void wasCorrectlyAnswered(Roll roll) {
        penaltyBox.playerAnswersCorrectly(this, getCurrentPlayer(), roll);
    }

    void detainedPlayerAnswersCorrectly(Roll roll) {
        roll.detainedPlayerAnswersCorrectly(this);
    }

    boolean detainedPlayerAnswersCorrectlyButRolledEven() {
        nextPlayer();
        return true;
    }

    void detainedPlayerAnswersCorrectlyAndRolledOdd() {
        freePlayerAnswersCorrectly();
    }

    void freePlayerAnswersCorrectly() {
        announceCorrectAnswer();
        incrementPlayerGold();
        announceCurrentPlayerGoldCoins();
        getCurrentPlayer().doesPlayerHaveWinningAmount(this);
        nextPlayer();
    }

    private void announceCorrectAnswer() {
        outputStream.println("Answer was correct!!!!");
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

    public void wrongAnswer() {
        announceWrongAnswer();
        currentPlayerAnnouncer().announceSentInPenaltyBox(announcePrinter);
        penaltyBox.detain(getCurrentPlayer());

        nextPlayer();
    }

    private void announceWrongAnswer() {
        outputStream.println("Question was incorrectly answered");
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

    public void playerHasWinningGoldAmount(Player player) {
        verdictListener.hasWon(player);
    }
}
