package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserLogic {
    private Database database;
    private int lastId;
    private static final String ERROR_MESSAGE = "No users with this ID!";

    public UserLogic(Database database) {
        this.database = database;
    }

    public void createUser(User user) {
        List<User> users = database.getUsers();
        user.setUserId(database.getLastUserId() + 1);
        users.add(user);
    }

    public void viewAllUsers() {
        List<User> users = database.getUsers();
        int counter = 1;
        boolean isEmpty = true;

        for (User user : users) {
            System.out.println();
            System.out.println("User [" + counter++ + "]");
            System.out.println("-------------------");
            user.customToString();
            isEmpty = false;
        }

        if (isEmpty) {
            System.out.println(ERROR_MESSAGE);
        }
    }

    public void updateUserCardType(int userId, CardType newCardType) {
        List<User> users = database.getUsers();

        for (User user : users) {
            if (user.getUserId() == userId) {
                user.setCard(newCardType);
                break;
            }
        }
    }

    public User getSingleUser(String egn) {
        for (User user : database.getUsers()) {
            if (user.getEgn().equals(egn)) {
                return user;
            }
        }
        return null;
    }

    public void addReservationToUser(String currentUserEgn, Reservation reservation) {
        for (User user : database.getUsers()) {
            if (user.getEgn().equals(currentUserEgn)) {
                if (!user.getReservations().isEmpty()) {
                    this.lastId = user.getReservations().get(user.getReservations().size() - 1).getReservationID() + 1;
                } else {
                    this.lastId = 0;
                }

                reservation.setReservationID(this.lastId);
                user.getReservations().add(reservation);
            }
        }
    }

    public void deleteReservationFromUser(String currentUserEgn, int reservationId) {
        for (User user : database.getUsers()) {
            if (user.getEgn().equals(currentUserEgn)) {
                Reservation reservationToBeDeleted
                        = getReservationById(user, reservationId);
                if (null != reservationToBeDeleted) {
                    user.getReservations().remove(reservationToBeDeleted);
                } else {
                    System.out.println("Current user has no such reservation with the given ID!");
                }
            }
        }
    }

    // Utility method to deleteReservationFromUser
    private Reservation getReservationById(User user, int reservationId) {
        List<Reservation> userRes = user.getReservations();

        for (Reservation reservation : userRes) {
            if (reservation.getReservationID() == reservationId) {
                return reservation;
            }
        }
        return null;
    }

    public void viewPreviousReservationsForUser(String currentUserEgn) {
        List<Reservation> reservations = null;

        for (User user : database.getUsers()) {
            if (user.getEgn().equals(currentUserEgn)) {
                reservations = user.getReservations();
            }
        }

        List<Reservation> reservationsToBeDeleted = new ArrayList<>();
        boolean isEmpty = true;

        if (reservations != null) {
            for (Reservation res :
                    reservations) {
                if (LocalDate.now().toEpochDay() > (res.getCreatedOn() + 7)) {
                    reservationsToBeDeleted.add(res);
                }
            }
            reservations.removeAll(reservationsToBeDeleted);

            for (Reservation res :
                    reservations) {
                res.customToString();
                isEmpty = false;
            }
        } else {
            System.out.println("No reservations for the given user");
        }

        if (isEmpty) {
            System.out.println("No reservations for the given user");
        }
    }

    public void updateReservationByID(String currentUserEgn, int reservationIDToBeUpdated, CardType newCardType) {
        List<Reservation> reservations = null;

        for (User user : database.getUsers()) {
            if (user.getEgn().equals(currentUserEgn)) {
                reservations = user.getReservations();
            }
        }

        boolean isEmpty = true;
        if (reservations != null) {
            for (Reservation res :
                    reservations) {
                if (res.getReservationID() == reservationIDToBeUpdated) {
                    res.setDiscountType(newCardType);
                    isEmpty = false;
                }
            }
        } else {
            System.out.println("No reservations for the given user");
        }

        if (isEmpty) {
            System.out.println(ERROR_MESSAGE);
        }
    }
}
