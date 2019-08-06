package com.railticket.Driver;

import com.railticket.Data.TrainData;
import com.railticket.Exceptions.NegativePnrNumberException;
import com.railticket.Exceptions.NegativeTrainNumberException;
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

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        TrainData trainData = new TrainData();

        CommandLineTable table = new CommandLineTable(); //to print statements in tabular format.
        table.setShowVerticalLines(true);
        table.setHeaders("list all the trains", "Search trains based on source and destination", "Book Ticket", "Check pnr status", "Route of train", "Cancel Ticket", "Exit");
        table.addRow("press 1", "press 2", "press 3", "press 4", "press 5", "press 6", "press 7");

        System.out.println("\n******WELCOME TO TRAIN TICKET BOOKING SYSTEM******\n");
        System.out.println("\n******SELECT THE PROPER OPTION******\n");
        while (true) {
            System.out.println("\n");
            table.print();
            System.out.println("\n");


            int option = -1;
            boolean success = false;
            while (!success) {
                try {
                    System.out.print("Enter an option: ");
                    option = sc.nextInt();
                    if (option > 7 || option <= 0) {
                        throw new NoAnyOptionMatchException();
                    }
                    success = true;
                } catch (InputMismatchException | NoAnyOptionMatchException e) {
                    sc.nextLine();
                    System.out.println("You have entered an invalid option");
                }
            }

            if (option == 7) {
                break;
            }
            chooseOption(option);
        }

    }

    private static void chooseOption(int option) {
        switch (option) {
            case 1:
                listAllTrains();
                break;
            case 2:
                searchTrains();
                break;
            case 3:
                bookTicket();
                break;
            case 4:
                checkPnrStatus();
                break;
            case 5:
                routeOftrain();
                break;
            case 6:
                cancelTicket();

        }
    }

    private static void listAllTrains() {
        Set<Train> trains = TrainDao.getInstance().getAllTrains();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Train Number", "Train Name", "Source", "Destination", "Total Seat", "Available Seat");
        for (Train t : trains) {
            table.addRow(String.valueOf(t.getTrainNumber()), t.getName(), t.getStations()[0], t.getStations()[t.getStations().length - 1], String.valueOf(t.getTotalSeat()), String.valueOf(t.getAvailableSeat()));
        }
        table.print();
    }

    private static void searchTrains() {
        System.out.print("enter the source station :");
        String source = sc.next();
        System.out.print("enter the destination station :");
        String destination = sc.next();
        Set<Train> trains = TrainDao.getInstance().searchTrain(source, destination);
        if (trains.isEmpty()) {
            System.out.println("no any trains available for given location.");
        } else {
            printTable(trains);
        }

    }

    private static void bookTicket() {
        System.out.println("***********ENTER YOUR DETAILS***********\n");

        boolean success = false;
        String name = null, gender = null, source = null, destination = null;
        int age = 0;

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
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("One or more information given above may not be in appropriate format.");
            }
        }
        Set<Train> trains = TrainDao.getInstance().searchTrain(source, destination);
        if (trains.isEmpty()) {
            System.out.println("no any train in the given route");
        } else {
            System.out.println("List of trains between the given source and destination are :");
            printTable(trains);
            System.out.println("select the train number for ticket booking");
            Train train = inputTrainNo();
            if (train != null) {
                if (train.getAvailableSeat() != 0) {
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
                } else {
                    System.out.println("There are no available seats present in " + train.getName() + " Express");
                }
            } else {
                System.out.println("Invalid Train number selected!..Try Again..");
            }

        }
    }

    private static void checkPnrStatus() {
        System.out.print("Enter the pnr No:");
        Ticket ticket = inputPnrNo();
        if (ticket == null) {
            System.out.println("Incorrect PNR");
        } else {
            System.out.println("your ticket has been confirmed already for train number " + ticket.getTrain().getTrainNumber() + " from " + ticket.getFromStation() + " to " + ticket.getToStation());
        }
    }

    private static void routeOftrain() {
        System.out.print("enter the Train number : ");
        Train train = inputTrainNo();
        if (train == null) {
            System.out.println("No such train number found.");
        } else {
            System.out.print("Start" + " ----> ");
            for (String s : train.getStations()) {
                System.out.print(s + " ----> ");
            }
            System.out.println("Stop");
        }
    }

    private static void cancelTicket() {
        System.out.print("Enter the pnr No:");
        Ticket ticket = inputPnrNo();
        if (ticket == null) {
            System.out.println("Incorrect PNR");
        }
        else
        {
            if(TicketDao.getInstance().removeTicket(ticket))
            {
                int availableSeat=TrainDao.getInstance().getTrainByTicket(ticket).getAvailableSeat();
                availableSeat+=1;
                TrainDao.getInstance().getTrainByTicket(ticket).setAvailableSeat(availableSeat);
                System.out.println("your ticket has been cancelled");
            }
        }
    }

    private static void printTable(Set<Train> trains) {
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Train Number", "Train Name", "Total Seat", "Available Seat");
        for (Train train : trains) {
            table.addRow(String.valueOf(train.getTrainNumber()), train.getName(), String.valueOf(train.getTotalSeat()), String.valueOf(train.getAvailableSeat()));
        }
        table.print();
        System.out.println("\n\n");
    }

    private static Train inputTrainNo() {
        boolean success = false;
        Train train;
        int trainNo = 0;
        while (!success) {
            try {
                trainNo = sc.nextInt();
                if(trainNo<0) {
                    throw new NegativeTrainNumberException();
                }
                success = true;
            } catch (InputMismatchException | NegativeTrainNumberException e) {
                sc.nextLine();
                System.out.println("Invalid Train number");
                System.out.println("enter the proper train no : ");
            }
        }
        train = TrainDao.getInstance().getTrainByTrainNo(trainNo);
        return train;
    }
    private  static Ticket inputPnrNo()
    {
        boolean success = false;
        Ticket ticket;
        int pnrNo = 0;
        while (!success) {
            try {
                pnrNo = sc.nextInt();
                if(pnrNo<0) {
                    throw new NegativePnrNumberException();
                }
                success = true;
            } catch (InputMismatchException | NegativePnrNumberException e) {
                sc.nextLine();
                System.out.println("Invalid pnr number");
                System.out.println("enter the proper pnr no : ");
            }
        }
        ticket = TicketDao.getInstance().getTicketByPnr(pnrNo);
        return ticket;
    }
}
