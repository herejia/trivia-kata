package trivia.questions;

import java.util.ArrayList;
import java.util.List;

final class QuestionsFactory {
    List<Question> createPopQuestions(int number) {
        return createPopQuestions(number, "Pop");
    }
    List<Question> createScienceQuestions(int number) {
        return createPopQuestions(number, "Science");
    }
    List<Question> createSportsQuestions(int number) {
        return createPopQuestions(number, "Sports");
    }
    List<Question> createRockQuestions(int number) {
        return createPopQuestions(number, "Rock");
    }

    private List<Question> createPopQuestions(int number, String categoryName) {
        List<Question> popQuestions = new ArrayList<>();
        for (int questionNumber = number; questionNumber > -1; questionNumber--) {
            popQuestions.add(createPopQuestion(questionNumber, categoryName));
        }
        return popQuestions;
    }

    private Question createPopQuestion(final int questionNumber, final String categoryName) {
        return new Question(categoryName + " Question " + questionNumber);
    }
}
