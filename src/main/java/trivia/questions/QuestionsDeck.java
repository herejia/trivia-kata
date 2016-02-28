package trivia.questions;

import java.util.HashMap;
import java.util.Map;

public final class QuestionsDeck {
    private final Map<Category, CategoryDeck> categoryDecks;

    public QuestionsDeck() {
        this.categoryDecks = new HashMap<>();
        QuestionsDeckFactory questionsDeckFactory = new QuestionsDeckFactory();
        this.categoryDecks.put(CategoryFactory.createPop(), questionsDeckFactory.createPopDeck());
        this.categoryDecks.put(CategoryFactory.createScience(), questionsDeckFactory.createScienceDeck());
        this.categoryDecks.put(CategoryFactory.createSports(), questionsDeckFactory.createSportsDeck());
        this.categoryDecks.put(CategoryFactory.createRock(), questionsDeckFactory.createRockDeck());
    }

    public CategoryDeck getDeck(Category category) {
        return categoryDecks.get(category);
    }
}
