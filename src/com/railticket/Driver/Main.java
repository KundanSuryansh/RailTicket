package com.railticket.Driver;

import com.railticket.Data.TrainData;
import com.railticket.Exceptions.NoAnyOptionMatchException;
import com.railticket.TransportMode.Train;
import com.railticket.Users.Passenger;
import com.railticket.repository.TicketDao;
import com.railticket.repository.TrainDao;
import com.railticket.utility.CommandLineTable;
import com.railticket.utility.Ticket;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/** this  is driver class
 * from where the execution begins..
 *
 * Kundan Suryansh
 *
 * **/

public class Main{
    private static Scanner sc=new Scanner(System.in);
    private static boolean success = false;
    public static void main(String[] args) {

        TrainData trainData=new TrainData();

        CommandLineTable table=new CommandLineTable(); //to print statements in tabular format.
        table.setShowVerticalLines(true);
        table.setHeaders("list all the trains","Search trains based on source and destination","Book Ticket","Check pnr status","Route of train","Exit");
        table.addRow("press 1","press 2","press 3","press 4","press 5","press 6");

        System.out.println("\n******WELCOME TO TRAIN TICKET BOOKING SYSTEM******\n");
        System.out.println("\n******SELECT THE PROPER OPTION******\n");

        while (true)
        {
            System.out.println("\n");
            table.print();
            System.out.println("\n");


            int option=-1;

            while (!success) {
                try {
                    System.out.print("Enter an option: ");
                    option = sc.nextInt();
                    if(option>6)
                    {
                        throw new NoAnyOptionMatchException();
                    }
                    success = true;
                } catch (InputMismatchException | NoAnyOptionMatchException e) {
                    sc.nextLine();
                    System.out.println("You have entered an invalid option");
                }
            }

            if(option==6)
                break;
            chooseOption(option);
        }

    }
    private static void chooseOption(int option)
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
    private static void listAllTrains()
    {
        Set<Train> trains=TrainDao.getInstance().getAllTrains();
        CommandLineTable table=new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Train Number","Train Name","Source","Destination","Total Seat","Available Seat");
        for(Train t:trains)
        {
            table.addRow(String.valueOf(t.getTrainNumber()),t.getName(),t.getStations()[0],t.getStations()[t.getStations().length-1],String.valueOf(t.getTotalSeat()),String.valueOf(t.getAvailableSeat()));
        }
        table.print();
    }
    private static void searchTrains()
    {
        System.out.print("enter the source station :");
        String source=sc.next();
        System.out.print("enter the destination station :");
        String destination=sc.next();
        Set<Train> trains=TrainDao.getInstance().searchTrain(source,destination);
        if(trains.isEmpty())
        {
            System.out.println("no any trains available for given location.");
        }
        printTable(trains);

    }
    private static void bookTicket()
    {
        System.out.println("***********ENTER YOUR DETAILS***********\n");

        success = false;
        String name=null,gender=null,source=null,destination=null;
        int age=0;

        while (!success) {
            try {
                System.out.print("Name : ");
                name = sc.next();
                System.out.print("Age : ");
                age = sc.nextInt();
                System.out.print("Gender (Male or Female): ");
                gender = sc.next();
                System.out.print("Source : ");
                source = sc.next();
                System.out.print("Destination : ");
                destination = sc.next();
                success = true;
            }
            catch (InputMismatchException e)
            {
                sc.nextLine();
                System.out.println("One or more information given above may not be in appropriate format.");
            }
        }


        Set<Train> trains=TrainDao.getInstance().searchTrain(source,destination);
        if(trains.isEmpty())
        {
            System.out.println("no any train in the given route");
        }
        else
        {
            System.out.println("List of trains between the given source and destination are :");
            System.out.println("Train Number"+"    "+"Train Name"+"     "+"Total Seat"+"  "+"Available Seat");
            printTable(trains);
            System.out.println("select the train number for ticket booking");

            success = false;
            int trainNo=0;
            while (!success) {
                try {
                    trainNo = sc.nextInt();
                    success=true;
                    }
                catch (InputMismatchException e)
                {
                    sc.nextLine();
                    System.out.println("Invalid Train number");
                }
            }


            Train train=TrainDao.getInstance().getTrainByTrainNo(trainNo);
            if(train.getAvailableSeat()!=0)
            {
                Passenger passenger = new Passenger(age, name, gender.charAt(0));
                Random random = new Random();
                int pnrNo = random.nextInt(1000);
                while (TicketDao.getInstance().checkPnrDuplicaton(pnrNo)) {
                    pnrNo = random.nextInt(1000);
                }
                int fare = TrainDao.getInstance().fare(source, destination, train);
                Ticket ticket = new Ticket(pnrNo, source, destination, fare, passenger, train);
                TicketDao.getInstance().save(ticket);
                train.setAvailableSeat(train.getTotalSeat() - TicketDao.getInstance().getCountOfTickets(train));
                System.out.println("Your Ticket has been booked,please note your PNR number");
                System.out.println("Amount deducted from your account is " + ticket.getFare());
                System.out.println("********YOUR PNR NUMBER : " + ticket.getPnrNo() + " **********");
            }
            else
            {
                System.out.println("There are no available seats present in "+train.getName()+" Express");
            }
        }
    }

    private static void checkPnrStatus()
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

    private static void routeOftrain()
    {
        System.out.print("enter the Train number : ");
        int trainNo=sc.nextInt();
        Train train=TrainDao.getInstance().getTrainByTrainNo(trainNo);
        System.out.print("Start"+" ----> ");
        for(String s:train.getStations()) {
            System.out.print(s + " ----> ");
        }
        System.out.println("Stop");
    }

    private static void printTable(Set<Train> trains)
    {
        CommandLineTable table=new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Train Number","Train Name","Total Seat","Available Seat");
        for(Train train:trains)
        {
            table.addRow(String.valueOf(train.getTrainNumber()),train.getName(),String.valueOf(train.getTotalSeat()),String.valueOf(train.getAvailableSeat()));
        }
        table.print();
        System.out.println("\n\n");
    }
}
