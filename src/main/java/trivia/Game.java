package trivia;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private final PrintStream outputStream;
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game(PrintStream outputStream) {
        this.outputStream = outputStream;
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean add(String playerName) {


        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        announcePlayerWasAdded(playerName);
        announcePlayerCount();
        return true;
    }

    private void announcePlayerCount() {
        outputStream.println("They are player number " + players.size());
    }

    private void announcePlayerWasAdded(String playerName) {
        outputStream.println(playerName + " was added");
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        announceCurrentPlayer();
        announceRoll(roll);

        if (isCurrentPlayerInPenaltyBox()) {
            if (roll % 2 != 0) {
                setPlayerOutOfPenaltyBox();

                announcePlayerOutOfPenaltyBox();
                movePlayer(roll);

                announcePlayerNewOPlace();
                announceCurrentCategory();
                askQuestion();
            } else {
                announcePlayerStaysInPenaltyBox();
                setPlayerGettingOutOfPenaltyBox();
            }
        } else {
            movePlayer(roll);
            announcePlayerNewOPlace();
            announceCurrentCategory();
            askQuestion();
        }

    }

    private void setPlayerGettingOutOfPenaltyBox() {
        isGettingOutOfPenaltyBox = false;
    }

    private void announcePlayerStaysInPenaltyBox() {
        announcePlayerStaysInPenaltyBox(players.get(currentPlayer) + " is not getting out of the penalty box");
    }

    private void announcePlayerStaysInPenaltyBox(String s) {
        outputStream.println(s);
    }

    private void announceCurrentCategory() {
        outputStream.println("The category is " + currentCategory());
    }

    private void announcePlayerNewOPlace() {
        outputStream.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
    }

    private void movePlayer(int steps) {
        places[currentPlayer] = places[currentPlayer] + steps;
        if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
    }

    private void announcePlayerOutOfPenaltyBox() {
        outputStream.println(players.get(currentPlayer) + " is getting out of the penalty box");
    }

    private void setPlayerOutOfPenaltyBox() {
        isGettingOutOfPenaltyBox = true;
    }

    private boolean isCurrentPlayerInPenaltyBox() {
        return inPenaltyBox[currentPlayer];
    }

    private void announceRoll(int roll) {
        outputStream.println("They have rolled a " + roll);
    }

    private void announceCurrentPlayer() {
        outputStream.println(players.get(currentPlayer) + " is the current player");
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
        if (places[currentPlayer] == 0) return "Pop";
        if (places[currentPlayer] == 4) return "Pop";
        if (places[currentPlayer] == 8) return "Pop";
        if (places[currentPlayer] == 1) return "Science";
        if (places[currentPlayer] == 5) return "Science";
        if (places[currentPlayer] == 9) return "Science";
        if (places[currentPlayer] == 2) return "Sports";
        if (places[currentPlayer] == 6) return "Sports";
        if (places[currentPlayer] == 10) return "Sports";
        return "Rock";
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
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    private void announceCurrentPlayerGoldCoins() {
        outputStream.println(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");
    }

    private void incrementPlayerGold() {
        purses[currentPlayer]++;
    }

    private void announceCorrectAnswer() {
        outputStream.println("Answer was correct!!!!");
    }

    private boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public boolean wrongAnswer() {
        announceWrongAnswer();
        announcePlayerInPenaltyBox();
        setPlayerInPenaltyBox();

        nextPlayer();
        return true;
    }

    private void setPlayerInPenaltyBox() {
        inPenaltyBox[currentPlayer] = true;
    }

    private void announcePlayerInPenaltyBox() {
        outputStream.println(players.get(currentPlayer) + " was sent to the penalty box");
    }

    private void announceWrongAnswer() {
        outputStream.println("Question was incorrectly answered");
    }


    private boolean didCurrentPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
