package org.example;


import java.time.LocalDate;
import java.util.Arrays;

public class Reservation {
    private int reservationID;
    private CardType discountType;
    private int ticketCount;
    private double[] ticketsPrices;
    private long createdOn;

    public Reservation() {
    }

    public Reservation(CardType discountType, int ticketCount) {
        this.discountType = discountType;
        this.ticketCount = ticketCount;
        this.ticketsPrices = new double[ticketCount];
        this.createdOn = LocalDate.now().toEpochDay();
    }

    public int getReservationID() {
        return this.reservationID;
    }

    public double[] getTicketsPrices() {
        return this.ticketsPrices;
    }

    public void setTicketsPrices(double[] ticketsPrices) {
        this.ticketsPrices = ticketsPrices;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public CardType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(CardType discountType) {
        this.discountType = discountType;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public void setCreatedOn(long localDate) {
        this.createdOn = localDate;
    }

    public long getCreatedOn() {
        return this.createdOn;
    }

    public void customToString() {
        System.out.println("Reservation ID: " + this.getReservationID());
        System.out.println("Discount type: " + this.getDiscountType());
        System.out.println("Number of reserved tickets: " + this.getTicketCount());
        System.out.println("Total price for the reservation: " + Arrays.toString(this.getTicketsPrices()));
        System.out.println("------------------------------------------------------");
    }
}

