package com.railticket.Users;

import com.railticket.utility.Ticket;

public class Passenger {
private int age;
private String name;
private char gender;


    public Passenger(int age, String name, char gender) {
        this.age = age;
        this.name = name;
        this.gender = gender;
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

}
