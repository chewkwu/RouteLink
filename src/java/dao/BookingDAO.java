package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Booking;
import entities.Message;
import entities.Vehicle;
import java.io.IOException;
import java.sql.Date;
//import java.time.LocalDate;
import messageclient.MessageClient;

//my imports
//import java.util.Date;
import java.text.SimpleDateFormat;

public class BookingDAO {

    private final String pattern1 = "yyyy-MM-dd";
    private final String pattern2 = "kk:mm:ss";
    SimpleDateFormat formatter;

    public Booking addBooking(Booking booking) throws SQLException, ClassNotFoundException, IOException, Exception {
        Connection conn = SwiftAccessor.getConnection();
        PreparedStatement pst = conn.prepareStatement("INSERT INTO booking (tripDistance,hasReturn,calculatedCost,bookingStatus,paymentStatus,paidAmount,departureTime,bookingDate,customerID,location,destination,numbOfPassengers,locationAddress) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        pst.setDouble(1, booking.getTripDistance());
        pst.setString(2, booking.isHasReturn());
        pst.setDouble(3, booking.getCalculatedCost());
        pst.setString(4, "pending");
        pst.setString(5, "not paid");
        pst.setDouble(6, 0);
        pst.setString(7, booking.getDepartureTime());
        //pst.setDate(8, Date.valueOf(LocalDate.now()));
        pst.setDate(8, new Date(new java.util.Date().getTime()));
        pst.setString(9, booking.getCustomerID());
        pst.setString(10, booking.getLocation());
        pst.setString(11, booking.getDestination());
        pst.setInt(12, booking.getNumbOfPassengers());
        pst.setString(13, booking.getLocationAddress());
        pst.execute();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs != null && rs.next()) {
            booking.setBookingID((rs.getLong(1)));
            Message message = new Message();
            message.setUsername("applab");
            message.setPassword("java@1986");
            message.setRouteID(0);
            message.setSender("RoadLink");
            message.setTo(booking.getCustomerID());
            message.setBody("Booking registetred, Please wait for confirmation");
            new MessageClient().sendMessage(message);
            SwiftAccessor.closeConnection(conn);
            return booking;
        } else {
            SwiftAccessor.closeConnection(conn);
            return null;
        }
    }

    public Booking viewBooking(long bookingID) throws SQLException, ClassNotFoundException {
        Connection conn = SwiftAccessor.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM booking WHERE bookingID=?");
        pst.setLong(1, bookingID);
        ResultSet rst = pst.executeQuery();
        if (rst.next()) {
            Booking booking = new Booking();
            booking.setBookingID(rst.getLong(1));
            booking.setVehicleID(rst.getString(2));
            booking.setTripDistance(rst.getDouble(3));
            booking.setHasReturn(rst.getString(4));
            booking.setCalculatedCost(rst.getDouble(5));
            booking.setBookingStatus(rst.getString(6));
            booking.setPaymentStatus(rst.getString(7));
            booking.setPaidAmount(rst.getDouble(8));
            booking.setDepartureTime(rst.getString(9));
            booking.setBookingDate(rst.getDate(10));
            booking.setArrivalTime(rst.getString(11));
            booking.setCustomerID(rst.getString(12));
            booking.setLocation(rst.getString(13));
            booking.setDestination(rst.getString(14));
            booking.setNumbOfPassengers(rst.getInt(15));
            booking.setLocationAddress(rst.getString(16));
            SwiftAccessor.closeConnection(conn);
            return booking;
        } else {
            SwiftAccessor.closeConnection(conn);
            return null;
        }
    }

    public Booking makeBookingOngoing(Booking booking) throws SQLException, ClassNotFoundException, IOException, Exception {
        Connection conn = SwiftAccessor.getConnection();
        PreparedStatement pst = conn.prepareStatement("UPDATE booking SET bookingStatus=?, vehicleID=? WHERE bookingID=?");
        pst.setString(1, booking.getBookingStatus());
        pst.setString(2, booking.getVehicleID());
        pst.setLong(3, booking.getBookingID());
        pst.execute();
            Message message = new Message();
            message.setUsername("applab");
            message.setPassword("java@1986");
            message.setRouteID(0);
            message.setSender("RoadLink");
            message.setTo(booking.getCustomerID());
            Vehicle vehicle = new VehicleDAO().getVehicle(booking.getVehicleID());
            message.setBody("Booking confirmed, The drivers phone number: " + vehicle.getDriverID() +
                    "\nThe price is: " + booking.getCalculatedCost());
            new MessageClient().sendMessage(message);
            Message message2 = new Message();
            message2.setUsername("applab");
            message2.setPassword("java@1986");
            message2.setRouteID(0);
            message2.setSender("RoadLink");
            message2.setTo(vehicle.getDriverID());
            message2.setBody("New Booking"
                    + "\nBooking ID: " + booking.getBookingID()
                    + "\nCustomer ID: " + booking.getCustomerID()
                    + "\nLocation: " + booking.getLocation()
                    + "\nLocation Address: " + booking.getLocationAddress()
                    + "\nDestination: " + booking.getDestination()
                    + "\nPrice: " + booking.getCalculatedCost()
                    + "\nDeparture Time: " + booking.getDepartureTime());
            new MessageClient().sendMessage(message2);
            PreparedStatement pst2 = conn.prepareStatement("SELECT * FROM booking WHERE bookingID=?");
            pst2.setLong(1, booking.getBookingID());
            ResultSet rst = pst2.executeQuery();
            if (rst.next()) {
                Booking book = new Booking();
                book.setBookingID(rst.getLong(1));
                book.setVehicleID(rst.getString(2));
                book.setTripDistance(rst.getDouble(3));
                booking.setHasReturn(rst.getString(4));
                book.setCalculatedCost(rst.getDouble(5));
                book.setBookingStatus(rst.getString(6));
                book.setPaymentStatus(rst.getString(7));
                book.setPaidAmount(rst.getDouble(8));
                book.setDepartureTime(rst.getString(9));
                book.setBookingDate(rst.getDate(10));
                book.setArrivalTime(rst.getString(11));
                book.setCustomerID(rst.getString(12));
                booking.setLocation(rst.getString(13));
                booking.setDestination(rst.getString(14));
                booking.setNumbOfPassengers(rst.getInt(15));
                booking.setLocationAddress(rst.getString(16));
                SwiftAccessor.closeConnection(conn);
                return book;
            } else {
                SwiftAccessor.closeConnection(conn);
                return null;
            }
    }

    public void payForBooking(Booking booking) throws SQLException, ClassNotFoundException {
        Connection conn = SwiftAccessor.getConnection();
        PreparedStatement pst = conn.prepareStatement("UPDATE booking SET paymentStatus=?, bookingStatus=?, arrivalTime=?, paidAmount=? WHERE bookingID=?");
        pst.setString(1, "paid");
        pst.setString(2, "completed");
        //pst.setTime(4, Time.valueOf(LocalTime.now()));
        pst.setString(3,booking.getArrivalTime()) ;
        pst.setDouble(4, booking.getPaidAmount());
        pst.setLong(5, booking.getBookingID());
        pst.execute();
        Booking book = viewBooking(booking.getBookingID());
        Vehicle vehicle = new VehicleDAO().getVehicle(book.getVehicleID());
        System.out.println(vehicle.getVehicleID());
        vehicle.setAvailabilityStatus("available");
        new VehicleDAO().updateVehicle(vehicle);
        SwiftAccessor.closeConnection(conn);
    }

    public List<Booking> showAllBookings() throws SQLException, ClassNotFoundException {
        Connection conn = SwiftAccessor.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM booking");
        ResultSet rst = pst.executeQuery();
        if (rst != null) {
            List<Booking> bookings = new ArrayList<>();
            while (rst.next()) {
                Booking booking = new Booking();
                booking.setBookingID(rst.getLong(1));
                booking.setVehicleID(rst.getString(2));
                booking.setTripDistance(rst.getDouble(3));
                booking.setHasReturn(rst.getString(4));
                booking.setCalculatedCost(rst.getDouble(5));
                booking.setBookingStatus(rst.getString(6));
                booking.setPaymentStatus(rst.getString(7));
                booking.setPaidAmount(rst.getDouble(8));
                booking.setDepartureTime(rst.getString(9));
                booking.setBookingDate(rst.getDate(10));
                booking.setArrivalTime(rst.getString(11));
                booking.setCustomerID(rst.getString(12));
                booking.setLocation(rst.getString(13));
                booking.setDestination(rst.getString(14));
                booking.setNumbOfPassengers(rst.getInt(15));
                booking.setLocationAddress(rst.getString(16));
                bookings.add(booking);
            }
            SwiftAccessor.closeConnection(conn);
            return bookings;
        } else {
            SwiftAccessor.closeConnection(conn);
            return null;
        }
    }

    public void removeBooking(long id) throws ClassNotFoundException, SQLException, Exception {
        Booking book = viewBooking(id);
        Connection conn = SwiftAccessor.getConnection();
        PreparedStatement pst = conn.prepareStatement("DELETE FROM booking WHERE bookingID=?");
        pst.setLong(1, id);
        pst.execute();
        Vehicle vehicle = new VehicleDAO().getVehicle(book.getVehicleID());
        vehicle.setAvailabilityStatus("available");
        new VehicleDAO().updateVehicle(vehicle);
         Message message = new Message();
            message.setUsername("applab");
            message.setPassword("java@1986");
            message.setRouteID(0);
            message.setSender("RoadLink");
            message.setTo(vehicle.getDriverID());
            message.setBody("Booking with ID: " + id + " has been cancelled");
            new MessageClient().sendMessage(message);
        SwiftAccessor.closeConnection(conn);
    }

    public Booking aPendingBookings() throws ClassNotFoundException, SQLException {
        Booking booking = null;
        Connection conn = SwiftAccessor.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM booking WHERE bookingStatus=?");
        pst.setString(1, "pending");
        ResultSet rst = pst.executeQuery();
        if (rst.next()) {
            booking = new Booking();
            booking.setBookingID(rst.getLong(1));
            booking.setVehicleID(rst.getString(2));
            booking.setTripDistance(rst.getDouble(3));
            booking.setHasReturn(rst.getString(4));
            booking.setCalculatedCost(rst.getDouble(5));
            booking.setBookingStatus(rst.getString(6));
            booking.setPaymentStatus(rst.getString(7));
            booking.setPaidAmount(rst.getDouble(8));
            booking.setDepartureTime(rst.getString(9));
            booking.setBookingDate(rst.getDate(10));
            booking.setArrivalTime(rst.getString(11));
            booking.setCustomerID(rst.getString(12));
            booking.setLocation(rst.getString(13));
            booking.setDestination(rst.getString(14));
            booking.setNumbOfPassengers(rst.getInt(15));
            booking.setLocationAddress(rst.getString(16));
            PreparedStatement pst2 = conn.prepareStatement("UPDATE booking SET bookingStatus=? WHERE bookingID=?");
            pst2.setString(1, "processing");
            pst2.setInt(2, (int) booking.getBookingID());
            pst2.execute();
            SwiftAccessor.closeConnection(conn);
        } else {
            SwiftAccessor.closeConnection(conn);
        }
        booking.setBookingStatus("procesing");
        return booking;
    }

    public int getPendingCount() throws ClassNotFoundException, SQLException {
        Booking booking = null;
        int count = 0;
        Connection conn = SwiftAccessor.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM booking WHERE bookingStatus=?");
        pst.setString(1, "pending");
        ResultSet rst = pst.executeQuery();

        while (rst.next()) {
            count = count + 1;
        }

        SwiftAccessor.closeConnection(conn);
        return count;
    }
}
