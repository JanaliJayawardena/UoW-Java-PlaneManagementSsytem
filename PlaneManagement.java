import java.io.File;
import java.util.Scanner;
import java.util.InputMismatchException;

public class PlaneManagement {
    private static int[][] seats = new int[4][];         //initialize the seats array
    private static Ticket[][] tickets = new Ticket[4][]; // initialize the tickets array
    private static Scanner input = new Scanner(System.in);  // Create a scanner object to read user input

    public static void main(String[]args){
        initializeArrays(); //initialize the seat and tickets array

        System.out.println("Welcome to the Plane Management application");  //print the welcome message
        System.out.println();

        int option;
            do {   //main menu loop
                try {
                userMenu(); //display the user menu
                System.out.print("Please select your option: ");
                option = input.nextInt();
                switch (option) {   //handle user's choices
                    case 0:
                        System.out.println("Exit the programme");
                        break;
                    case 1:
                        System.out.println("Buy a seat");
                        buy_seat();
                        break;
                    case 2:
                        System.out.println("Cancel a seat");
                        cancel_seat();
                        break;
                    case 3:
                        System.out.println("find first available seat");
                        find_first_available();
                        break;
                    case 4:
                        System.out.println("Show seating plan");
                        show_seating_plan();
                        break;
                    case 5:
                        System.out.println("Print ticket information and total sales ");
                        print_tickets_info();
                        break;
                    case 6:
                        System.out.println("Search ticket");
                        search_ticket();
                        break;
                    default:
                        System.out.println("Invalid option");
                }
                }catch(InputMismatchException e){           //handle invalid input
                    System.out.println("Invalid Input. Please enter a valid option.");
                    input.nextLine();        // Clear the invalid input
                    option=-1;              // Reset the option to an invalid value to continue the loop
                }
            } while (option != 0);

    }
    private static void initializeArrays(){
        seats[0] = new int[14];    //row A
        seats[1] = new int[12];    //row B
        seats[2] = new int[12];    //row C
        seats[3] = new int[14];    //row D

        tickets[0] = new Ticket[14];
        tickets[1] = new Ticket[12];
        tickets[2] = new Ticket[12];
        tickets[3] = new Ticket[14];


        for (int rowIdx = 0; rowIdx < seats.length; rowIdx++) {
            for (int seatIdx = 0; seatIdx < seats[rowIdx].length; seatIdx++) {
                seats[rowIdx][seatIdx] = 0;               // Initialize all seats as available (0)
            }
        }
    }
    public static void userMenu(){    //method to display user menu
        System.out.println("**************************************************");
        System.out.println("*                   MENU OPTIONS                 *");
        System.out.println("**************************************************");
        System.out.println("\t 1) Buy a seat");
        System.out.println("\t 2) Cancel a seat");
        System.out.println("\t 3) Find first available seat");
        System.out.println("\t 4) Show seating plan");
        System.out.println("\t 5) Print tickets information and total sales ");
        System.out.println("\t 6) Search tickets");
        System.out.println("\t 0) Quit");
        System.out.println("**************************************************");
    }
    private static int[] takeSeatInputs() {   //Method to take seat inputs from the user
        int[] indices = new int[2];   // Create an array to store row and seat indices
        while (true) {     // Continuous loop to prompt user for seat selection until valid input is received
            try {
                System.out.print("Enter the row letter (A-D): ");
                char row = input.next().toUpperCase().charAt(0);      //char only takes the first character of what we entered
                int rowIdx = row - 'A';                               //A-A= 65-65= 0; convert the char into decimal to check the row index
                // Calculate the row index based on the ASCII value of the character

                System.out.print("Enter the seat number(1-" + seats[rowIdx].length + "): ");
                int seatNumber = input.nextInt();
                int seatIdx = seatNumber - 1;                         //cause the array starts from 0th index

                if (seatIdx < 0 || seatIdx >= seats[rowIdx].length) {
                    System.out.println("Invalid seat number.");
                } else {
                    indices[0] = rowIdx;    // Store valid row index in the indices array
                    indices[1] = seatIdx;   // Store valid seat index in the indices array
                    break;                  // Exit the loop since valid input is received
                }
            } catch (Exception e) {
                if (e instanceof InputMismatchException) {
                    System.out.println("Invalid input.");  //when the user enters a character when a number is expected.
                    input.nextLine(); // Consume the invalid input to avoid infinite loop
                                     // If this exception is not handled properly, the invalid input remains in the input buffer,
                                     //This method reads the entire line of input (including the invalid input) and discards it.
                                     // By doing this, the input buffer is cleared, allowing the program to prompt the user again for fresh input.
                } else if (e instanceof ArrayIndexOutOfBoundsException) {  //The 'ArrayIndexOutOfBoundsException' is thrown when the row index calculate
                                                                           // from the input is outside the bounds of the 'seats' array
                    System.out.println("Invalid row number.");     // when you give a number to the row letter and gives another letter rather than (A-D)
                } else {
                    System.out.println("An error occurred. Please try again");
                }
            }
        }
        return indices;        // Return the array containing valid row and seat indices
    }


    private static void buy_seat(){
        // Prompt user to select a seat and get the row and seat indexes
        int[] indices = takeSeatInputs();
        int rowIdx = indices[0];
        int seatIdx = indices[1];

        if(seats[rowIdx][seatIdx]==1) {   // Check if the seat is already taken
            System.out.println("Seat already taken.");   // Display message if seat is already occupied
        }else{
            // Prompt user to enter person's information
            System.out.print("Enter person's name: ");
            String name = input.next();
            System.out.print("Enter person's surname: ");
            String surname = input.next();
            System.out.print("Enter person's email: ");
            String email = input.next();

            Person person = new Person(name, surname, email);   // Create a Person object with the provided information
            double price = calculatePrice(rowIdx, seatIdx);     // Calculate the price of the ticket based on the seat position
            Ticket ticket = new Ticket((char)('A'+rowIdx), seatIdx+1 , price, person); // Create a Ticket object for the purchased seat

            // Mark the seat as purchased in the seats array and store the ticket in the tickets array
            seats[rowIdx][seatIdx]=1;
            tickets[rowIdx][seatIdx] = ticket ;

            System.out.println("Seat purchased successfully.");
            ticket.save();   // save the ticket info to the file
            System.out.println("Ticket info saved to the file successfully .");
        }
    }

    private static void cancel_seat() {
        //Prompt user to select a seat and get the row and seat indices
        int[] indices = takeSeatInputs();   // this function gets the row and seat indices from the user
        int rowIdx = indices[0];
        int seatIdx = indices[1];

        if (seats[rowIdx][seatIdx]==0){  //check if the seat is already available
            System.out.println("Seat already available.");
        }else{
            seats[rowIdx][seatIdx]=0;         //make the seat as available in the seats array
            tickets[rowIdx][seatIdx] = null;  //make the ticket as null in the ticket array
            String filename = ""+ (char)('A' + rowIdx) + (seatIdx + 1) + ".txt";   //create the filename for the ticket associated with the cancel seat
            File file = new File(filename);  //create a file object with the filename
            if(file.exists()){               //check if the ticket file exists
                try {
                    file.delete();
                }catch(Exception e){      // Handle any exceptions occurred while deleting the file
                    System.out.println(e);
                }
            }
            System.out.println("Seat cancelled successfully.");
        }
    }
    private static void find_first_available(){
            for (int rowIdx= 0; rowIdx < seats.length; rowIdx++) {
                for (int seatIdx = 0; seatIdx < seats[rowIdx].length; seatIdx++){
                    if (seats[rowIdx][seatIdx]==0) { //check if the seat is available
                        char row = (char)('A'+rowIdx);  //convert the row index to the letter
                        System.out.println("First available seat: Row " + row + ", Seat " + (seatIdx + 1)); //print the location of the fist available seat
                        return;
                    }
                }
            }
            System.out.println("No available seats");
    }

    private static void show_seating_plan(){
        for (int rowIdx = 0; rowIdx < seats.length; rowIdx++) {        //loop through each row
            for (int seatIdx = 0; seatIdx < seats[rowIdx].length; seatIdx++){  //loop through each seat in the row
                if (seats[rowIdx][seatIdx]==0){
                    System.out.print("O ");   //print '0' if seat is available
                }else{
                    System.out.print("X ");   //print 'X' if seat is taken
                }
            }
            System.out.println();
        }
    }
    private static double calculatePrice(int rowIdx, int seatIdx) {
        double price;  //variable to store the calculated price
        //define the price range for different seat positions
        if(seatIdx>=0 && seatIdx<=4){   //seat 1-5
            price = 200.0;
        }else if (seatIdx>=5 && seatIdx<=8){  //seat 6-9
            price = 150.0;
        }else  { //seat 10-14
            price = 180.0;
        }
        return price;
    }

    private static void print_tickets_info() {
        double totalSales = 0.0;       //variable to store the total sales
        for (int rowIdx = 0; rowIdx < tickets.length; rowIdx++) {
            for (int seatIdx = 0; seatIdx < tickets[rowIdx].length; seatIdx++) {
                Ticket ticket = tickets[rowIdx][seatIdx];  //get the ticket at the current seat
                if (ticket != null) {  //check if the ticket exists at the current seat
                    ticket.print_tickets_info();
                    totalSales += ticket.getPrice();  //add the ticket price to the total sales
                }
            }
        }
        System.out.println("Total Sales: £" + totalSales);
    }

    private static void search_ticket(){
        int[] indices = takeSeatInputs(); //prompt the user to select a seat and get the row and seat indexes
        int rowIdx = indices[0];
        int seatIdx = indices[1];

        Ticket ticket = tickets[rowIdx][seatIdx];  //get the ticket at the specified seat
        if(ticket != null){                        //check if a ticket exists at the specified seat
            ticket.print_tickets_info();              //print ticket information if found
        }else{
            System.out.println("This seat is available");
        }

    }
}
