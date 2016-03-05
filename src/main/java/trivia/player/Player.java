package trivia.player;

import trivia.Game;
import trivia.announcement.AnnouncePrinter;
import trivia.gold.GoldAmount;
import trivia.gold.GoldFactory;
import trivia.place.Place;
import trivia.place.PlaceFactory;

public class Player {
    private final String name;
    private Place place;
    private final PlaceFactory placeFactory;
    private GoldAmount goldAmount;
    private final GoldFactory goldFactory;
    private final PlayerAnnouncer playerAnnouncer;
    private final AnnouncePrinter announcePrinter;

    Player(String name, Place startingPlace, GoldAmount startingAmount, AnnouncePrinter announcePrinter) {
        this.name = name;
        this.place = startingPlace;
        this.goldAmount = startingAmount;
        this.announcePrinter = announcePrinter;
        this.placeFactory = new PlaceFactory();
        this.goldFactory = new GoldFactory();
        playerAnnouncer = new PlayerAnnouncer(this);
    }

    public String getName() {
        return name;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void move(int steps) {
        this.place = placeFactory.create(place, steps);
        announcePlayerNewPlace();
    }

    private void announcePlayerNewPlace() {
        this.playerAnnouncer.announceNewPlace(announcePrinter);
    }

    public void setGoldAmount(GoldAmount goldAmount) {
        this.goldAmount = goldAmount;
    }

    public void incrementGoldAmount() {
        GoldAmount newAmount = this.goldAmount.plus(1);
        this.goldAmount = newAmount;
    }

    public boolean hasWinningGoldAmount() {
        GoldAmount winningGoldAmount = goldFactory.createWinningGoldAmount();
        return goldAmount.equals(winningGoldAmount);
    }

    public GoldAmount getGoldAmount() {
        return goldAmount;
    }

    public void doesPlayerHaveWinningAmount(Game game) {
        if (hasWinningGoldAmount()) {
            game.playerHasWinningGoldAmount(this);
        }
    }
}
