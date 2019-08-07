package com.railticket.repository;

import java.util.List;
import java.util.Set;

import com.railticket.Users.Passenger;
import com.railticket.utility.Ticket;

public interface PassengerDaoImp {
public List<Passenger> getAllPassengers(); //to list all passengers.
public void save(Passenger passenger); // to save passenger's information.
public boolean checkPassengerIdDuplication(int passengerId);
public Passenger getPassengerById(int passengerId);

}
