package trivia;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.OutputStreamAnnouncePrinter;
import trivia.flow.VerdictListener;
import trivia.player.InGamePlayers;
import trivia.player.Player;
import trivia.player.PlayerAnnouncer;
import trivia.questions.*;

import java.io.PrintStream;

public class Game implements GameController {
    private final AnnouncePrinter announcePrinter;
    private final PrintStream outputStream;
    private final InGamePlayers inGamePlayers;
    private final PenaltyBox penaltyBox;
    private final QuestionsDeck questionsDeck;
    private VerdictListener verdictListener;
    private Player activePlayer;

    Game(PrintStream outputStream, VerdictListener verdictListener) {
        this.outputStream = outputStream;
        this.verdictListener = verdictListener;
        announcePrinter = new OutputStreamAnnouncePrinter(outputStream);
        inGamePlayers = new InGamePlayers(this, announcePrinter);
        penaltyBox = new PenaltyBox();
        questionsDeck = new QuestionsDeck();
    }

    @Override
    public void freePlayerMoves(Player byPlayer, Roll roll) {
        byPlayer.move(roll.intValue());
        announceCategory();
        askQuestion();
    }

    @Override
    public void playerLeavesPenaltyBox(Player detainedPlayer, Roll roll) {
        PlayerAnnouncer playerAnnouncer = new PlayerAnnouncer(detainedPlayer);
        playerAnnouncer.announceIsGettingOutOfPenaltyBox(announcePrinter);
        freePlayerMoves(detainedPlayer, roll);
    }

    @Override
    public void playerStaysInPenaltyBox(Player detainedPlayer) {
        PlayerAnnouncer playerAnnouncer = new PlayerAnnouncer(detainedPlayer);
        playerAnnouncer.announceIsNotGettingOutOfThePenaltyBox(announcePrinter);
    }

    public void activePlayerIs(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void playerHasWinningGoldAmount(Player player) {
        verdictListener.hasWon(player);
    }

    void add(String playerName) {
        inGamePlayers.newPlayer(playerName);
    }

    void roll(Roll roll) {
        currentPlayerAnnouncer().announcePlayerAsCurrent(announcePrinter);
        announceRoll(roll);
        penaltyBox.playerMoves(roll, activePlayer, this);
    }

    void wasCorrectlyAnswered(Roll roll) {
        penaltyBox.playerAnswersCorrectly(this, activePlayer, roll);
    }

    void detainedPlayerAnswersCorrectly(Roll roll) {
        roll.detainedPlayerAnswersCorrectly(this);
    }

    boolean detainedPlayerAnswersCorrectlyButRolledEven() {
        return true;
    }

    void detainedPlayerAnswersCorrectlyAndRolledOdd() {
        freePlayerAnswersCorrectly();
    }

    void freePlayerAnswersCorrectly() {
        announceCorrectAnswer();
        activePlayer.incrementGoldAmount();
        announceCurrentPlayerGoldCoins();
        activePlayer.doesPlayerHaveWinningAmount(this);
    }

    void wrongAnswer() {
        announceWrongAnswer();
        currentPlayerAnnouncer().announceSentInPenaltyBox(announcePrinter);
        penaltyBox.detain(activePlayer);
    }

    void turnIsOver() {
        inGamePlayers.nextPlayer();
    }

    private void announceCurrentPlayerGoldCoins() {
        currentPlayerAnnouncer().announceGoldAmount(announcePrinter);
    }

    private void announceCorrectAnswer() {
        outputStream.println("Answer was correct!!!!");
    }

    private void announceCategory() {
        new CategoryAnnouncer(getCurrentCategory()).announceCategory(announcePrinter);
    }

    private Category getCurrentCategory() {
        return activePlayer.getPlace().getCategory();
    }

    private PlayerAnnouncer currentPlayerAnnouncer() {
        return new PlayerAnnouncer(activePlayer);
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

    private void announceWrongAnswer() {
        outputStream.println("Question was incorrectly answered");
    }
}
