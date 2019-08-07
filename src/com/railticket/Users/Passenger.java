package com.railticket.Users;

import com.railticket.repository.PassengerDao;
import com.railticket.utility.Ticket;

import java.util.Random;
import java.util.Set;

public class Passenger {
private int passengerId;
private int age;
private String name;
private char gender;
private Set<Ticket> ticket;
private String password;


    public Passenger(int age, String name, char gender,String password) {
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.password=password;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public int getPassengerId() { return passengerId; }

    public Set<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(Set<Ticket> ticket) {
        this.ticket = ticket;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassword() {
        return password;
    }
}
