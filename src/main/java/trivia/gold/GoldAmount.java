package trivia.gold;

public final class GoldAmount {
    private final int amount;
    private final GoldFactory goldFactory;

    GoldAmount(int amount) {
        this.amount = amount;
        this.goldFactory = new GoldFactory();
    }

    public GoldAmount plus(int amountToAdd) {
        return goldFactory.create(this.amount, amountToAdd);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoldAmount that = (GoldAmount) o;

        return amount == that.amount;

    }

    @Override
    public int hashCode() {
        return amount;
    }
}
