package com.railticket.repository;

import java.util.ArrayList;
import java.util.List;

import com.railticket.Users.Passenger;
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

}
