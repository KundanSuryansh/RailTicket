package com.railticket.Driver;

import com.railticket.Data.TrainData;
import com.railticket.TransportMode.Train;
import com.railticket.Users.Passenger;
import com.railticket.repository.TicketDao;
import com.railticket.repository.TrainDao;
import com.railticket.utility.Ticket;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {

        TrainData trainData=new TrainData();
        System.out.println("\n******WELCOME TO TRAIN TICKET BOOKING SYSTEM******\n");
        System.out.println("\n******SELECT THE PROPER OPTION******\n");
        while (true)
        {
            System.out.println("\n  1.list all the trains   2.Search trains based on source and destination    3.Book Ticket    4.Check pnr status   5.Route of train");
            int option = sc.nextInt();
            if(option>5)
                break;
            chooseOption(option);
        }

    }
    public static void chooseOption(int option)
    {
        switch (option)
        {
            case 1: listAllTrains();
                    break;
            case 2: searchTrains();
                    break;
            case 3: bookTicket();
                    break;
            case 4: checkPnrStatus();
                    break;
            case 5: routeOftrain();
                    break;

        }
    }
    public static void listAllTrains()
    {
        Set<Train> trains=TrainDao.getInstance().getAllTrains();
        System.out.println("Train Number"+"  "+"Train Name"+"    "+"Source"+"    "+"Destination"+"  "+"Total Seat"+"  "+"Available Seat");
        for(Train t:trains)
        {
            System.out.println("       "+t.getTrainNumber()+"        "+t.getName()+"     "+t.getStations()[0]+"         "+t.getStations()[t.getStations().length-1]+"         "+t.getTotalSeat()+"          "+t.getAvailableSeat());
        }
    }
    public static void searchTrains()
    {
        System.out.print("enter the source station :");
        String source=sc.next();
        System.out.print("enter the destination station :");
        String destination=sc.next();
        Set<Train> trains=TrainDao.getInstance().searchTrain(source,destination);
        System.out.println("Train Number"+"    "+"Train Name"+"     "+"Total Seat"+"  "+"Available Seat");
        for(Train t:trains)
        {
            System.out.println("    "+t.getTrainNumber()+"        "+t.getName()+"         "+t.getTotalSeat()+"          "+t.getAvailableSeat());
        }

    }
    public static void bookTicket()
    {
        System.out.println("***********ENTER YOUR DETAILS***********\n");
        System.out.print("Name : ");
        String name=sc.next();
        System.out.print("Age : ");
        int age=sc.nextInt();
        System.out.print("Gender : ");
        String gender=sc.next();
        System.out.print("Source : ");
        String source=sc.next();
        System.out.print("Destination : ");
        String destination=sc.next();
        Set<Train> trains=TrainDao.getInstance().searchTrain(source,destination);
        if(trains.isEmpty())
        {
            System.out.println("no any train in the given route");
        }
        else
        {
            System.out.println("List of trains between the given source and destination are :");
            System.out.println("Train Number"+"    "+"Train Name"+"     "+"Total Seat"+"  "+"Available Seat");
            for(Train t:trains)
            {
                System.out.println("    "+t.getTrainNumber()+"        "+t.getName()+"         "+t.getTotalSeat()+"          "+t.getAvailableSeat());
            }
            System.out.println("select the train number for ticket booking");
            int trainNo=sc.nextInt();
            Train train=TrainDao.getInstance().getTrainByTrainNo(trainNo);
            Passenger passenger=new Passenger(age,name,gender.charAt(0));
            Random random=new Random();
            int pnrNo=random.nextInt(1000);
            while(TicketDao.getInstance().checkPnrDuplicaton(pnrNo))
            {
                pnrNo=random.nextInt(1000);
            }
            int fare=TrainDao.getInstance().fare(source,destination,train);
            Ticket ticket=new Ticket(pnrNo,source,destination,fare,passenger,train);
            TicketDao.getInstance().save(ticket);
            train.setAvailableSeat(train.getTotalSeat()-TicketDao.getInstance().getCountOfTickets(train));
            System.out.println("Your Ticket has been booked,please note your PNR number");
            System.out.println("********YOUR PNR NUMBER : "+ticket.getPnrNo()+" **********");
        }
    }
    public static void checkPnrStatus()
    {
        System.out.print("Enter the pnr No:");
        int pnr=sc.nextInt();
        Ticket ticket=TicketDao.getInstance().getTicketByPnr(pnr);
        if(ticket==null)
        {
            System.out.println("Incorrect PNR");
        }
        else
        {
            System.out.println("your ticket has been confirmed already for train number "+ticket.getTrain().getTrainNumber()+" from "+ticket.getFromStation()+" to "+ticket.getToStation());
        }
    }
    public static void routeOftrain()
    {
        System.out.print("enter the Train number : ");
        int trainNo=sc.nextInt();
        Train train=TrainDao.getInstance().getTrainByTrainNo(trainNo);
        System.out.print("Start"+" ----> ");
        for(String s:train.getStations())
        {
            System.out.print(s+" ----> ");
        }
        System.out.println("Stop");
    }




}
