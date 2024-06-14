import java.io.FileWriter;

public class Ticket {
    //instance variables to store ticket information
    private char row;
    private int seat;
    private double price;
    private Person person;

    //constructor to initialize ticket object with provided information
    public Ticket(char row, int seat, double price, Person person) {
        setRow(row);
        setSeat(seat);
        setPrice(price);
        setPerson(person);
    }

    //getter and setter methods for row,seat,price and person
    public char getRow() {         //getters retrieve the values of private fields
        return row;
    }

    public void setRow(char row) {  //setters modify or set the values of private fields in a class
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print information of a ticket
    public void print_tickets_info() {
        System.out.println("------------------------------------------------");
        System.out.println("Person Information:");
        person.printInfo();
        System.out.println("Ticket Information:");
        System.out.println("\tRow: " +getRow() + " Seat: " + getSeat());
        System.out.println("\tPrice: £" + getPrice());
        System.out.println();
    }

    //method to save the ticket information to the file
    public void save(){
        // The try-with-resources statement is used here to automatically close the FileWriter object after the try block finishes execution.
        try( FileWriter myFile = new FileWriter( ""+getRow()+ getSeat() + ".txt")){
             // Open a file with filename composed of row and seat indices

            myFile.write("Person Information:\n");
            myFile.write("\tName:"+person.getName()+"\n");
            myFile.write("\tsurName:"+person.getSurname()+"\n");
            myFile.write("\temail:"+person.getEmail()+"\n");
            myFile.write("Ticket Information:\n");
            myFile.write("\tRow: " +getRow() + " Seat: " + getSeat()+"\n");
            myFile.write("\tPrice: £" +getPrice()+"\n");
        }catch(Exception e){
            System.out.println(e);
        }
    }

}

