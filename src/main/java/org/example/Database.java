package org.example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final Gson gson;
    private List<Reservation> reservations;
    private List<User> users;
    private int lastReservationID;
    private int lastUserId;
    public Database() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.reservations = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public List<Reservation> getReservations() {
        return this.reservations;
    }
    public int getLastReservationID() {
        return this.lastReservationID;
    }

    public void writeToFile(List<Reservation> reservations) {
        try (PrintWriter out = new PrintWriter(new FileWriter("reservations.json"))) {
            System.out.println("Database::writeToFile: " + this.gson.toJson(reservations));
            out.write(this.gson.toJson(reservations));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readFromFileOnStartUp() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("reservations.json"));
        } catch (FileNotFoundException e) {
            System.out.println("Database::FileNotFound.");
            System.out.println(e.getMessage());
        }

        Type listOfMyClassObject = new TypeToken<ArrayList<Reservation>>() {}.getType();

        if (reader != null) {
            try {
                this.reservations = this.gson.fromJson(reader, listOfMyClassObject);
                if (null == this.reservations) {
                    this.reservations = new ArrayList<>();
                }
            } catch (Exception e) {
                System.out.println("Database::readFromFileOnStartUp: File empty");
                this.reservations = new ArrayList<>();
            }
        } else {
            System.out.println("Database::readFromFileOnStartUp: Error creating BufferedReader");
        }

        // Get last inserted ID
        if (!this.reservations.isEmpty()) {
            this.lastReservationID = this.reservations.get(this.reservations.size() - 1).getReservationID();
        } else {
            this.lastReservationID = 0;
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public int getLastUserId() {
        return lastUserId;
    }

    public void readUsersFromFileOnStartUp() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("users.json"));
        } catch (FileNotFoundException e) {
            System.out.println("Database::FileNotFound.");
            System.out.println(e.getMessage());
        }

        Type listOfMyClassObject = new TypeToken<ArrayList<User>>() {}.getType();

        if (reader != null) {
            try {
                this.users = this.gson.fromJson(reader, listOfMyClassObject);
                if (null == this.users) {
                    this.users = new ArrayList<>();
                }
            } catch (Exception e) {
                System.out.println("Database::readFromFileOnStartUp: File empty");
                this.users = new ArrayList<>();
            }
        } else {
            System.out.println("Database::readUsersFromFileOnStartUp: Error creating BufferedReader");
        }

        // Get last inserted ID
        if (!this.users.isEmpty()) {
            this.lastUserId = this.users.get(this.users.size() - 1).getUserId();
        } else {
            this.lastUserId = 0;
        }
    }

    public void writeUsersToFile(List<User> users) {
        try (PrintWriter out = new PrintWriter(new FileWriter("users.json"))) {
            System.out.println("Database::writeToFile: " + this.gson.toJson(users));
            out.write(this.gson.toJson(users));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
