/**
 * Book My Stay Application
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Demonstrates abstraction, inheritance, and simple availability handling.
 *
 * @author YourName
 * @version 2.0
 */

// ABSTRACT CLASS
abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// SINGLE ROOM
class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

// DOUBLE ROOM
class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

// SUITE ROOM
class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// MAIN CLASS (SAME AS UC1)
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 2.0\n");

        // CREATE ROOM OBJECTS
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // STATIC AVAILABILITY
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // DISPLAY DETAILS
        System.out.println("---- Single Room ----");
        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        System.out.println("---- Double Room ----");
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        System.out.println("---- Suite Room ----");
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}

