public class Person {
    //instance variables to store person information
    private String name;
    private String surname;
    private String email;

    public Person(String name, String surname, String email){ //constructor that initializes a Person object with the provided information about the person.
                                                                   // constructor to initialize ticket object with provided information
        setName(name);             //initialize the name attribute
        setSurname(surname);             //initialize the surname attribute
        setEmail(email);          //initialize the email attribute
    }

    public String getName() {
        return name;      // Return the name attribute
    }
    public void setName(String name) {
        this.name = name;    // Set the attribute with the provided value
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void printInfo() {
        System.out.println("\tName: " + getName());
        System.out.println("\tSurname: " + getSurname());
        System.out.println("\tEmail: " + getEmail());
    }
}
