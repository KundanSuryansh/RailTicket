package com.railticket.repository;

import java.util.List;

import com.railticket.Users.Passenger;

public interface PassengerDaoImp {
public List<Passenger> getAllPassengers(); //to list all passengers.
public void save(Passenger passenger); // to save passenger's information.
}
