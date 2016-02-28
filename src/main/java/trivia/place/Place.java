package trivia.place;

import trivia.questions.Category;
import trivia.questions.CategoryFactory;

public class Place {
    private final Integer number;
    private final CategoryFactory categoryFactory;
    private final Category category;

    Place(Integer number) {
        this.number = number;
        this.categoryFactory = new CategoryFactory();
        category = categoryFactory.createFrom(this);
    }

    @Deprecated
    public int intValue() {
        return number;
    }

    /**
     * Maybe of a Board responsability
     */
    public Integer getPlaceNumber(int stepsAhead) {
        int computedPlaceNumber = number + stepsAhead;
        if (computedPlaceNumber > 11) {
            return computedPlaceNumber - 12;
        }
        return computedPlaceNumber;
    }

    public Category getCategory() {
        return category;
    }
}
