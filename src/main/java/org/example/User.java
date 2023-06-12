package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int userId;
    private String name;
    private String egn;
    private CardType card;
    private List<Reservation> reservations;

    public User(String name, String egn, CardType card) {
        this.name = name;
        this.egn = egn;
        this.card = card;
        this.reservations = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public CardType getCard() {
        return card;
    }

    public void setCard(CardType card) {
        this.card = card;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void customToString() {
        System.out.println("UserID: " + this.userId);
        System.out.println("Name: " + this.name);
        System.out.println("EGN: " + this.egn);
        System.out.println("Card type: " + this.card);

        System.out.println("Reservations IDs:");
        for (Reservation res : reservations) {
            System.out.println(res.getReservationID());
        }
    }
}
