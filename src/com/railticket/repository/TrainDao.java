package com.railticket.repository;

import com.railticket.TransportMode.Train;
import com.railticket.utility.Ticket;

import java.util.LinkedHashSet;
import java.util.Set;
/** this is Singleton class,which is holding all the Trains in from of Set.
 *
 * **/
public class TrainDao implements TrainDaoImpl {

    LinkedHashSet<Train> trainContainer=new LinkedHashSet<Train>();
    private static TrainDao SINGLE_INSTANCE=null;
    private TrainDao(){}
    public static TrainDao getInstance()
    {
        if(SINGLE_INSTANCE==null)
            SINGLE_INSTANCE=new TrainDao();
        return SINGLE_INSTANCE;

    }
    @Override
    public Set<Train> getAllTrains() {
        return trainContainer;
    }

    @Override
    public Set<Train> searchTrain(String source, String destination) {
        LinkedHashSet<Train> filterTrainContainer=new LinkedHashSet<Train>();
        for(Train train :trainContainer)
        {
            String[] stations =train.getStations();
            int temp=0;
            for(String station : stations) {
                if (station.equals(source) || station.equals(destination)) {
                    temp++;
                }
                if (temp == 2) {
                    filterTrainContainer.add(train);
                }
            }
        }
        return filterTrainContainer;
    }

    @Override
    public void save(Train train) {
        trainContainer.add(train);
    }

    @Override
    public Train getTrainByTrainNo(int trainNo) {
        for (Train train:trainContainer)
        {
            if(train.getTrainNumber()==trainNo)
            {
                return train;
            }
        }
        return null;
    }

    @Override
    public int fare(String source, String destination,Train train) {
        String[] stations=train.getStations();
        int temp=-1;
        int count=0;
        for(String station:stations)
        {
            if(station.equals(source) || temp==0)
            {
                count++;
                temp=0;
            }
            if(station.equals(destination))
            {
                break;
            }
        }
        return 100*(count);

    }

    @Override
    public Train getTrainByTicket(Ticket ticket) {
        for(Train train :trainContainer)
        {
            if(train.getTrainNumber()==ticket.getTrain().getTrainNumber())
            {
                return train;
            }
        }
            return null;
    }
}
