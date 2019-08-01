package com.railticket.repository;

import java.util.List;

import com.railticket.Users.Passenger;

public interface PassengerDaoImp {
public List<Passenger> getAllPassengers();
public void save(Passenger passenger);
}
