package trivia;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.OutputStreamAnnouncePrinter;
import trivia.player.*;
import trivia.questions.*;

import java.io.PrintStream;

@Deprecated
public class Game {
    private final AnnouncePrinter announcePrinter;
    private final PlayersAnnouncer playersAnnouncer;
    private final PrintStream outputStream;
    private final InGamePlayers inGamePlayers;
    private final PenaltyBox penaltyBox;
    private final QuestionsDeck questionsDeck;

    int currentPlayerIndex = 0;
    private final PlayerFactory playerFactory;

    public Game(PrintStream outputStream) {
        this.outputStream = outputStream;
        playerFactory = new PlayerFactory();
        inGamePlayers = new InGamePlayers();
        announcePrinter = new OutputStreamAnnouncePrinter(outputStream);
        playersAnnouncer = new PlayersAnnouncer(inGamePlayers);
        penaltyBox = new PenaltyBox();
        questionsDeck = new QuestionsDeck();
    }

    public boolean add(String playerName) {
        Player player = playerFactory.create(playerName);
        inGamePlayers.add(player);
        playersAnnouncer.announceLastAddedPlayer(announcePrinter);
        playersAnnouncer.announcePlayerCount(announcePrinter);
        return true;
    }

    public void roll(Roll roll) {
        currentPlayerAnnouncer().announcePlayerAsCurrent(announcePrinter);

        announceRoll(roll);
        if (penaltyBox.detains(getCurrentPlayer())) {
            if (roll.isOdd()) {
                onOddRollInPenaltyBox(roll);
            } else {
                onEvenRollInPenaltyBox();
            }
        } else {
            getCurrentPlayer().move(roll.intValue());
            announcePlayerNewPlace();
            announceCategory();
            askQuestion();
        }
    }

    private void announceCategory() {
        new CategoryAnnouncer(getCurrentCategory()).announceCategory(announcePrinter);
    }

    private void onEvenRollInPenaltyBox() {
        currentPlayerAnnouncer().announceIsNotGettingOutOfThePenaltyBox(announcePrinter);
    }

    private void onOddRollInPenaltyBox(Roll roll) {
        currentPlayerAnnouncer().announceIsGettingOutOfPenaltyBox(announcePrinter);
        getCurrentPlayer().move(roll.intValue());
        announcePlayerNewPlace();
        announceCategory();
        askQuestion();
    }

    private Category getCurrentCategory() {
        return getCurrentPlayer().getPlace().getCategory();
    }

    private void announcePlayerNewPlace() {
        outputStream.println(getCurrentPlayerName()
                + "'s new location is "
                + getCurrentPlayerPlace());
    }

    private String getCurrentPlayerName() {
        Player currentPlayer = getCurrentPlayer();
        return currentPlayer.getName();
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

    private int getCurrentPlayerPlace() {
        return getCurrentPlayer().getPlace().intValue();
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
}
