package trivia;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.OutputStreamAnnouncePrinter;
import trivia.player.*;

import java.io.PrintStream;
import java.util.LinkedList;

public class Game {
    private final AnnouncePrinter announcePrinter;
    private final PlayersAnnouncer playersAnnouncer;
    private final PrintStream outputStream;
    private final InGamePlayers inGamePlayers;
    private final PenaltyBox penaltyBox;

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayerIndex = 0;
    LeavingZone leavingZone = new LeavingZone();
    private final PlayerFactory playerFactory;

    public Game(PrintStream outputStream) {
        this.outputStream = outputStream;
        this.playerFactory = new PlayerFactory();
        this.inGamePlayers = new InGamePlayers();
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
        announcePrinter = new OutputStreamAnnouncePrinter(outputStream);
        playersAnnouncer = new PlayersAnnouncer(inGamePlayers);
        penaltyBox = new PenaltyBox();
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
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
            announceCurrentCategory();
            askQuestion();
        }

    }

    private void onEvenRollInPenaltyBox() {
        leavingZone.staying();
        currentPlayerAnnouncer().announceIsNotGettingOutOfThePenaltyBox(announcePrinter);
    }

    private void onOddRollInPenaltyBox(Roll roll) {
        leavingZone.leaving();
        currentPlayerAnnouncer().announceIsGettingOutOfPenaltyBox(announcePrinter);
        getCurrentPlayer().move(roll.intValue());
        announcePlayerNewPlace();
        announceCurrentCategory();
        askQuestion();
    }

    private void announceCurrentCategory() {
        outputStream.println("The category is " + currentCategory());
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
        if (currentCategory() == "Pop")
            outputStream.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            outputStream.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            outputStream.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            outputStream.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (getCurrentPlayerPlace() == 0) return "Pop";
        if (getCurrentPlayerPlace() == 4) return "Pop";
        if (getCurrentPlayerPlace() == 8) return "Pop";
        if (getCurrentPlayerPlace() == 1) return "Science";
        if (getCurrentPlayerPlace() == 5) return "Science";
        if (getCurrentPlayerPlace() == 9) return "Science";
        if (getCurrentPlayerPlace() == 2) return "Sports";
        if (getCurrentPlayerPlace() == 6) return "Sports";
        if (getCurrentPlayerPlace() == 10) return "Sports";
        return "Rock";
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

    class LeavingZone {
        private Boolean isLeaving;

        LeavingZone() {
            isLeaving = true;
        }

        public void leaving() {
            this.isLeaving = true;
        }

        public void staying() {
            this.isLeaving = false;
        }

        public boolean isLeaving() {
            return isLeaving;
        }
    }
}
