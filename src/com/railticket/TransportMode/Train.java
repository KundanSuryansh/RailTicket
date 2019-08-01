package com.railticket.TransportMode;

public class Train {
private int trainNumber;
private String name;
private String[] stations;
private int availableSeat;
final private int totalSeat=100;

    public Train(int trainNumber, String name, String[] stations) {
        this.trainNumber = trainNumber;
        this.name = name;
        this.stations = stations;
        this.availableSeat=100;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getName() {
        return name;
    }

    public String[] getStations() {
        return stations;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }
}
