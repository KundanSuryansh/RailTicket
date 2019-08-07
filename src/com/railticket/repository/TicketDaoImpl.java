package com.railticket.repository;

import com.railticket.TransportMode.Train;
import com.railticket.Users.Passenger;
import com.railticket.utility.Ticket;

import java.util.Set;

public interface TicketDaoImpl {
    public Set<Ticket> getAllTickets();
    public Set<Ticket> getTicketByTrain(Train train);
    public Set<Integer> getAllPnrNo();
    public boolean checkPnrDuplicaton(int pnrNo);
    public void save(Ticket ticket);
    public int getCountOfTickets(Train train);
    public Ticket getTicketByPnr(int pnrNo);
    public boolean removeTicket(Ticket ticket);
    public  Set<Ticket> getTicketsByPassenger(Passenger passenger);
}
