package trivia.questions;

import java.util.LinkedList;
import java.util.List;

public final class CategoryDeck {
    private LinkedList<Question> questions;

    public CategoryDeck() {
        this.questions = new LinkedList<>();
    }

    public void add(List<Question> questions) {
        this.questions.addAll(questions);
    }

    public Question getTop() {
        return questions.getLast();
    }

    public void discardTop() {
        questions.removeLast();
    }
}
