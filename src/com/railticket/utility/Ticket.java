package com.railticket.utility;

import com.railticket.TransportMode.Train;
import com.railticket.Users.Passenger;

public class Ticket {
private int pnrNo;
private String fromStation;
private String ToStation;
private int fare;
private Passenger passenger;
private Train train;

    public Ticket(int pnrNo, String fromStation, String toStation, int fare, Passenger passenger, Train train) {
        this.pnrNo = pnrNo;
        this.fromStation = fromStation;
        ToStation = toStation;
        this.fare = fare;
        this.passenger = passenger;
        this.train = train;
    }

    public int getPnrNo() {
        return pnrNo;
    }

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return ToStation;
    }

    public int getFare() {
        return fare;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Train getTrain() {
        return train;
    }
}
