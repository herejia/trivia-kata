package trivia.questions;

public final class Question {
    private final String phrase;

    public Question(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public String toString() {
        return phrase;
    }
}
