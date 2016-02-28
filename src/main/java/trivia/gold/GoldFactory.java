package trivia.gold;

public final class GoldFactory {

    public GoldAmount create(int amount) {
        return new GoldAmount(amount);
    }

    public GoldAmount create(int startingAmount, int amountToAdd) {
        int newAmount = startingAmount + amountToAdd;
        return new GoldAmount(newAmount);
    }

    public GoldAmount createWinningGoldAmount() {
        return new GoldAmount(6);
    }
}
