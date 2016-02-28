package trivia.place;

public class Place {
    private final Integer number;

    Place(Integer number) {
        this.number = number;
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
}
