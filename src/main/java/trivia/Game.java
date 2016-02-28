package trivia;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.OutputStreamAnnouncePrinter;
import trivia.place.PlaceFactory;
import trivia.player.*;

import java.io.PrintStream;
import java.util.LinkedList;

public class Game {
    private final AnnouncePrinter announcePrinter;
    private final PlayersAnnouncer playersAnnouncer;
    private final PrintStream outputStream;
    private final Players players;
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayerIndex = 0;
    boolean isGettingOutOfPenaltyBox;
    private final PlayerFactory playerFactory;
    private final PlaceFactory placeFactory;

    public Game(PrintStream outputStream) {
        this.outputStream = outputStream;
        this.playerFactory = new PlayerFactory();
        this.placeFactory = new PlaceFactory();
        this.players = new Players();
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
        announcePrinter = new OutputStreamAnnouncePrinter(outputStream);
        playersAnnouncer = new PlayersAnnouncer(players);
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean add(String playerName) {
        Player player = playerFactory.create(playerName);
        player.setPlace(placeFactory.createStartingPlace());
        players.add(player);
        purses[players.count().intValue()] = 0;
        inPenaltyBox[players.count().intValue()] = false;

        playersAnnouncer.announceLastAddedPlayer(announcePrinter);
        playersAnnouncer.announcePlayerCount(announcePrinter);
        return true;
    }

    public void roll(int roll) {
        currentPlayerAnnouncer().announcePlayerAsCurrent(announcePrinter);
        announceRoll(roll);

        if (isCurrentPlayerInPenaltyBox()) {
            if (roll % 2 != 0) {
                setPlayerOutOfPenaltyBox();

                currentPlayerAnnouncer().announceIsGettingOutOfPenaltyBox(announcePrinter);
                getCurrentPlayer().move(roll);

                announcePlayerNewPlace();
                announceCurrentCategory();
                askQuestion();
            } else {
                currentPlayerAnnouncer().announceIsNotGettingOutOfThePenaltyBox(announcePrinter);
                setPlayerGettingOutOfPenaltyBox();
            }
        } else {
            getCurrentPlayer().move(roll);
            announcePlayerNewPlace();
            announceCurrentCategory();
            askQuestion();
        }

    }

    private void setPlayerGettingOutOfPenaltyBox() {
        isGettingOutOfPenaltyBox = false;
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
        return players.getPlayerByIndex(currentPlayerIndex);
    }

    private PlayerAnnouncer currentPlayerAnnouncer() {
        return new PlayerAnnouncer(getCurrentPlayer());
    }

    private void setPlayerOutOfPenaltyBox() {
        isGettingOutOfPenaltyBox = true;
    }

    private boolean isCurrentPlayerInPenaltyBox() {
        return inPenaltyBox[currentPlayerIndex];
    }

    private void announceRoll(int roll) {
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

    public boolean wasCorrectlyAnswered() {
        if (isCurrentPlayerInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox()) {
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
        if (currentPlayerIndex == players.count().intValue()) currentPlayerIndex = 0;
    }

    private void announceCurrentPlayerGoldCoins() {
        outputStream.println(getCurrentPlayerName() + " now has " + purses[currentPlayerIndex] + " Gold Coins.");
    }

    private void incrementPlayerGold() {
        purses[currentPlayerIndex]++;
    }

    private void announceCorrectAnswer() {
        outputStream.println("Answer was correct!!!!");
    }

    private boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public boolean wrongAnswer() {
        announceWrongAnswer();
        currentPlayerAnnouncer().announceSentInPenaltyBox(announcePrinter);
        setPlayerInPenaltyBox();

        nextPlayer();
        return true;
    }

    private void setPlayerInPenaltyBox() {
        inPenaltyBox[currentPlayerIndex] = true;
    }

    private void announceWrongAnswer() {
        outputStream.println("Question was incorrectly answered");
    }


    private boolean didCurrentPlayerWin() {
        return !(purses[currentPlayerIndex] == 6);
    }

}
