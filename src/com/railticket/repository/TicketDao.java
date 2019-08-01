package com.railticket.repository;

import com.railticket.TransportMode.Train;
import com.railticket.utility.Ticket;

import java.util.LinkedHashSet;
import java.util.Set;

public class TicketDao implements TicketDaoImpl {
    LinkedHashSet<Ticket> ticketContainer=new LinkedHashSet<Ticket>();
    private static TicketDao SINGLE_INSTANCE=null;
    private TicketDao(){}
    public static TicketDao getInstance()
    {
        if(SINGLE_INSTANCE==null)
            SINGLE_INSTANCE=new TicketDao();
        return SINGLE_INSTANCE;

    }

    @Override
    public void save(Ticket ticket) {
    ticketContainer.add(ticket);
    }

    @Override
    public boolean checkPnrDuplicaton(int pnrNo) {
        for (Ticket ticket:ticketContainer)
        {
           if(pnrNo==ticket.getPnrNo())
               return true;
        }
        return false;
    }

    @Override
    public Set<Integer> getAllPnrNo() {
        LinkedHashSet<Integer> allPnr=new LinkedHashSet<Integer>();
        for (Ticket ticket:ticketContainer)
        {
            allPnr.add(ticket.getPnrNo());
        }
        return allPnr;
    }

    @Override
    public Set<Ticket> getAllTickets() {
        return ticketContainer;
    }

    @Override
    public Set<Ticket> getTicketByTrain(Train train) {
        LinkedHashSet<Ticket> filterTicketContainer=new LinkedHashSet<Ticket>();
        for(Ticket ticket:ticketContainer)
        {
            if(ticket.getTrain().getTrainNumber()==train.getTrainNumber())
            {
                filterTicketContainer.add(ticket);
            }
        }
        return filterTicketContainer;
    }

    @Override
    public Ticket getTicketByPnr(int pnrNo) {
        for(Ticket ticket:ticketContainer)
        {
            if(ticket.getPnrNo()==pnrNo)
            {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public int getCountOfTickets(Train train) {
        int count=0;
        for (Ticket ticket:ticketContainer)
        {
            if(ticket.getTrain().getTrainNumber()==train.getTrainNumber())
            {
                count++;
            }
        }
        return count;
    }
}
