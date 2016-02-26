import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import trivia.GameRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GameTest {

    @Test
    public void shouldRunAsExpected42Scenario() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GameRunner gameRunner = new GameRunner(new PrintStream(baos));

        gameRunner.run();

        Assert.assertThat(baos.toString(), IsEqual.equalTo(
                "Chet was added\n" +
                "They are player number 1\n" +
                "Pat was added\n" +
                "They are player number 2\n" +
                "Sue was added\n" +
                "They are player number 3\n" +
                "Chet is the current player\n" +
                "They have rolled a 1\n" +
                "Chet's new location is 1\n" +
                "The category is Science\n" +
                "Science Question 0\n" +
                "Answer was corrent!!!!\n" +
                "Chet now has 1 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 4\n" +
                "Pat's new location is 4\n" +
                "The category is Pop\n" +
                "Pop Question 0\n" +
                "Answer was corrent!!!!\n" +
                "Pat now has 1 Gold Coins.\n" +
                "Sue is the current player\n" +
                "They have rolled a 1\n" +
                "Sue's new location is 1\n" +
                "The category is Science\n" +
                "Science Question 1\n" +
                "Question was incorrectly answered\n" +
                "Sue was sent to the penalty box\n" +
                "Chet is the current player\n" +
                "They have rolled a 1\n" +
                "Chet's new location is 2\n" +
                "The category is Sports\n" +
                "Sports Question 0\n" +
                "Answer was corrent!!!!\n" +
                "Chet now has 2 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 5\n" +
                "Pat's new location is 9\n" +
                "The category is Science\n" +
                "Science Question 2\n" +
                "Answer was corrent!!!!\n" +
                "Pat now has 2 Gold Coins.\n" +
                "Sue is the current player\n" +
                "They have rolled a 3\n" +
                "Sue is getting out of the penalty box\n" +
                "Sue's new location is 4\n" +
                "The category is Pop\n" +
                "Pop Question 1\n" +
                "Answer was correct!!!!\n" +
                "Sue now has 1 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 2\n" +
                "Chet's new location is 4\n" +
                "The category is Pop\n" +
                "Pop Question 2\n" +
                "Answer was corrent!!!!\n" +
                "Chet now has 3 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 2\n" +
                "Pat's new location is 11\n" +
                "The category is Rock\n" +
                "Rock Question 0\n" +
                "Answer was corrent!!!!\n" +
                "Pat now has 3 Gold Coins.\n" +
                "Sue is the current player\n" +
                "They have rolled a 2\n" +
                "Sue is not getting out of the penalty box\n" +
                "Chet is the current player\n" +
                "They have rolled a 4\n" +
                "Chet's new location is 8\n" +
                "The category is Pop\n" +
                "Pop Question 3\n" +
                "Answer was corrent!!!!\n" +
                "Chet now has 4 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 1\n" +
                "Pat's new location is 0\n" +
                "The category is Pop\n" +
                "Pop Question 4\n" +
                "Answer was corrent!!!!\n" +
                "Pat now has 4 Gold Coins.\n" +
                "Sue is the current player\n" +
                "They have rolled a 2\n" +
                "Sue is not getting out of the penalty box\n" +
                "Chet is the current player\n" +
                "They have rolled a 4\n" +
                "Chet's new location is 0\n" +
                "The category is Pop\n" +
                "Pop Question 5\n" +
                "Answer was corrent!!!!\n" +
                "Chet now has 5 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 1\n" +
                "Pat's new location is 1\n" +
                "The category is Science\n" +
                "Science Question 3\n" +
                "Answer was corrent!!!!\n" +
                "Pat now has 5 Gold Coins.\n" +
                "Sue is the current player\n" +
                "They have rolled a 3\n" +
                "Sue is getting out of the penalty box\n" +
                "Sue's new location is 7\n" +
                "The category is Rock\n" +
                "Rock Question 1\n" +
                "Answer was correct!!!!\n" +
                "Sue now has 2 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 1\n" +
                "Chet's new location is 1\n" +
                "The category is Science\n" +
                "Science Question 4\n" +
                "Answer was corrent!!!!\n" +
                "Chet now has 6 Gold Coins.\n"));
    }
}
