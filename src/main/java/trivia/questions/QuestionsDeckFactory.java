package trivia.questions;

import java.util.List;

final class QuestionsDeckFactory {

    private QuestionsFactory questionsFactory;

    public QuestionsDeckFactory() {
        questionsFactory = new QuestionsFactory();
    }

    CategoryDeck createPopDeck() {
        CategoryDeck deck = new CategoryDeck();
        List<Question> popQuestions = questionsFactory.createPopQuestions(50);
        deck.add(popQuestions);
        return deck;
    }

    CategoryDeck createScienceDeck() {
        CategoryDeck deck = new CategoryDeck();
        List<Question> scienceQuestions = questionsFactory.createScienceQuestions(50);
        deck.add(scienceQuestions);
        return deck;
    }

    CategoryDeck createSportsDeck() {
        CategoryDeck deck = new CategoryDeck();
        List<Question> sportsQuestions = questionsFactory.createSportsQuestions(50);
        deck.add(sportsQuestions);
        return deck;
    }

    CategoryDeck createRockDeck() {
        CategoryDeck deck = new CategoryDeck();
        List<Question> rockQuestions = questionsFactory.createRockQuestions(50);
        deck.add(rockQuestions);
        return deck;
    }
}
