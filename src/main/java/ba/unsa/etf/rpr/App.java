package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.GuestManager;
import ba.unsa.etf.rpr.business.ReservationsManager;
import ba.unsa.etf.rpr.domain.Guests;
import ba.unsa.etf.rpr.domain.Reservations;
import ba.unsa.etf.rpr.exception.GuestException;
import ba.unsa.etf.rpr.exception.ReservationsException;
import org.apache.commons.cli.*;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;


/**
 * Hello world!
 *
 */
public class App 
{

    private static final Option addGuest = new Option("g", "add-guest", false, "Adding new guest to database. Params: first name, last name, email, password");
    private static final Option addReservation = new Option("r", "add-reservation", false, "Adding new reservation to database. Params: arrival date, check-otu date, guest-id, room-id");
    private static final Option getGuests = new Option("getG", "get-guests", false, "Get all guests form database");
    private static final Option getReservations = new Option("getR", "get-reservations", false, "Get all reservations from database");

    public static void printFormattedOptions(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);
        helpFormatter.printUsage(printWriter, 150, "java -jar quote-maker.jar [option] 'something else if needed' ");
        helpFormatter.printOptions(printWriter, 150, options, 2, 7);
        printWriter.close();
    }

    public static Options addOptions(){
        Options options = new Options();
        options.addOption(addGuest);
        options.addOption(addReservation);
        options.addOption(getGuests);
        options.addOption(getReservations);
        return options;
    }

    public static void main( String[] args ) throws Exception {
        Options options = addOptions();
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine cl = commandLineParser.parse(options, args);
        if((cl.hasOption(getReservations.getOpt()) || cl.hasOption(getReservations.getLongOpt()))){
            ReservationsManager reservationsManager = new ReservationsManager();
            List<Reservations> reservations = reservationsManager.getAll();
            for(Reservations x : reservations){
                System.out.println(x.toString() + "\n");
            }
        }else if(cl.hasOption(getGuests.getOpt()) || cl.hasOption(getGuests.getLongOpt())){
            GuestManager guestManager = new GuestManager();
            List<Guests> guests = guestManager.getAll();
            for(Guests guest : guests){
                System.out.println(guest.toString() + "\n");
            }
        }else if(cl.hasOption(addGuest.getOpt()) || cl.hasOption(addGuest.getLongOpt())){
            GuestManager guestManager = new GuestManager();
            Guests guest = new Guests();
            guest.setFirstName(cl.getArgList().get(0));
            guest.setLastName(cl.getArgList().get(1));
            guest.setEmail(cl.getArgList().get(2));
            guest.setPassword(cl.getArgList().get(3));
            try{
                guestManager.add(guest);
            } catch (GuestException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
            System.out.println("Gost uspjesno unesen!");
        }else if(cl.hasOption(addReservation.getOpt()) || cl.hasOption(addReservation.getLongOpt())){
            ReservationsManager reservationsManager = new ReservationsManager();
            Reservations reservation = new Reservations();
            reservation.setArrivalDate(Date.valueOf(cl.getArgList().get(0)));
            reservation.setCheckOutDate(Date.valueOf(cl.getArgList().get(1)));
            reservation.setGuest(Integer.parseInt(cl.getArgList().get(2)));
            reservation.setRoom_id(Integer.parseInt(cl.getArgList().get(3)));
            if(reservation.getArrivalDate().after(reservation.getCheckOutDate())) throw new Exception("Pogresan datum!");
            try{
                reservationsManager.add(reservation);
            } catch (ReservationsException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Rezervacija uspjesno unesena!");
        }else{
            printFormattedOptions(options);
            System.exit(-1);
        }
    }
}
