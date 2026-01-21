import java.sql.*;
import java.util.Scanner;

public class App {

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            // JDBC Driver & Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/theatreDB",
                    "root",
                    "root"
            );

            int choice;

            do {
                System.out.println("\n===== THEATRE MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Booking");
                System.out.println("2. View Booking by ID");
                System.out.println("3. View All Bookings");
                System.out.println("4. Update Booking by ID");
                System.out.println("5. Delete Booking by ID");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        insertBooking();
                        break;
                    case 2:
                        viewBookingById();
                        break;
                    case 3:
                        viewAllBookings();
                        break;
                    case 4:
                        updateBookingById();
                        break;
                    case 5:
                        deleteBookingById();
                        break;
                    case 6:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 6);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. ADD BOOKING
    static void insertBooking() throws Exception {

        String sql = "INSERT INTO theatre_management VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Booking ID: ");
        ps.setInt(1, sc.nextInt());
        sc.nextLine();

        System.out.print("Theatre Name: ");
        ps.setString(2, sc.nextLine());

        System.out.print("Movie Name: ");
        ps.setString(3, sc.nextLine());

        System.out.print("Show Date (yyyy-mm-dd): ");
        ps.setDate(4, Date.valueOf(sc.nextLine()));

        System.out.print("Show Time: ");
        ps.setString(5, sc.nextLine());

        System.out.print("Seat Number: ");
        ps.setString(6, sc.nextLine());

        System.out.print("Customer Name: ");
        ps.setString(7, sc.nextLine());

        System.out.print("Phone Number: ");
        ps.setString(8, sc.nextLine());

        System.out.print("Ticket Price: ");
        ps.setDouble(9, sc.nextDouble());
        sc.nextLine();

        ps.setString(10, "Booked");

        ps.executeUpdate();
        System.out.println("Booking added successfully.");
    }

    // 2. VIEW BOOKING BY ID
    static void viewBookingById() throws Exception {

        String sql = "SELECT * FROM theatre_management WHERE booking_id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Enter Booking ID: ");
        ps.setInt(1, sc.nextInt());
        sc.nextLine();

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            printRow(rs);
        } else {
            System.out.println("Booking not found.");
        }
    }

    // 3. VIEW ALL BOOKINGS
    static void viewAllBookings() throws Exception {

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM theatre_management");

        while (rs.next()) {
            printRow(rs);
        }
    }

    // 4. UPDATE BOOKING BY ID
    static void updateBookingById() throws Exception {

        System.out.println("1. Update Seat Number");
        System.out.println("2. Update Movie Name");
        System.out.print("Choose option: ");
        int opt = sc.nextInt();
        sc.nextLine();

        if (opt == 1) {
            String sql = "UPDATE theatre_management SET seat_number=? WHERE booking_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("New Seat Number: ");
            ps.setString(1, sc.nextLine());

            System.out.print("Booking ID: ");
            ps.setInt(2, sc.nextInt());
            sc.nextLine();

            ps.executeUpdate();
            System.out.println("Seat updated successfully.");

        } else if (opt == 2) {
            String sql = "UPDATE theatre_management SET movie_name=? WHERE booking_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("New Movie Name: ");
            ps.setString(1, sc.nextLine());

            System.out.print("Booking ID: ");
            ps.setInt(2, sc.nextInt());
            sc.nextLine();

            ps.executeUpdate();
            System.out.println("Movie updated successfully.");
        }
    }

    // 5. DELETE BOOKING BY ID
    static void deleteBookingById() throws Exception {

        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Do you want to cancel booking? (y/n): ");
        char ch = sc.next().charAt(0);

        if (ch == 'y' || ch == 'Y') {
            String sql = "DELETE FROM theatre_management WHERE booking_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Your booking has been cancelled.");
        } else {
            System.out.println("Abort cancel.");
        }
    }

    // COMMON PRINT METHOD
    static void printRow(ResultSet rs) throws Exception {
        System.out.println(
                rs.getInt(1) + " | " +
                rs.getString(2) + " | " +
                rs.getString(3) + " | " +
                rs.getDate(4) + " | " +
                rs.getString(5) + " | " +
                rs.getString(6) + " | " +
                rs.getString(7) + " | " +
                rs.getString(8) + " | " +
                rs.getDouble(9) + " | " +
                rs.getString(10)
        );
    }
}
