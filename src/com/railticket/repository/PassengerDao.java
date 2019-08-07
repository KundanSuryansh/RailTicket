package com.railticket.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.railticket.Users.Passenger;
import com.railticket.utility.Ticket;

/** this is Singleton class,which is holding all the passengers details in from of arraylist
 *
 * **/
public class PassengerDao implements PassengerDaoImp {

	ArrayList<Passenger> passengers=new ArrayList<Passenger>();

	private static PassengerDao SINGLE_INSTANCE=null;
	private PassengerDao(){}
	public static PassengerDao getInstance()
	{
		if(SINGLE_INSTANCE==null)
			SINGLE_INSTANCE=new PassengerDao();
		return SINGLE_INSTANCE;

	}
	@Override
	public List<Passenger> getAllPassengers() {
		return passengers;
	}


	@Override
	public void save(Passenger passenger) {
		passengers.add(passenger);

		
	}

	@Override
	public boolean checkPassengerIdDuplication(int passengerId) {
		for (Passenger passenger:passengers)
		{
			if(passengerId==passenger.getPassengerId())
				return true;
		}
		return false;
	}

	@Override
	public Passenger getPassengerById(int passengerId) {
		Passenger passenger=null;
		for(Passenger tempPassenger:passengers)
		{
			if(tempPassenger.getPassengerId()==passengerId)
			{
				passenger=tempPassenger;
				break;
			}
		}
		return passenger;
	}
}
