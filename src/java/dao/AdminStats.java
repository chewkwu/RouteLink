package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Booking;
import entities.Vehicle;

public class AdminStats {

	public List<Booking> allUnpaidTrips() throws SQLException, ClassNotFoundException{
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM booking WHERE bookingStatus=completed AND CalculatedCost>AmountPaid");
		ResultSet rst = pst.executeQuery();
		if(rst != null) {
			List<Booking> bookings = new ArrayList<>();
			while(rst.next()) {
				Booking booking = new Booking();
		    	booking.setBookingID(rst.getLong(1));
		    	booking.setVehicleID(rst.getString(2));
		    	booking.setTripDistance(rst.getDouble(3));
		    	booking.setHasReturn(rst.getString(4));
		    	booking.setCalculatedCost(rst.getDouble(5));
		    	booking.setBookingStatus(rst.getString(6));
		    	booking.setPaymentStatus(rst.getString(7));
		    	booking.setPaidAmount(rst.getDouble(8));
		    	booking.setBookingDate(rst.getDate(9));
		    	booking.setDepartureTime(rst.getString(10));
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
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
	public int overallBookings(Date date) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) FROM booking WHERE BookingDate >= ?");
		pst.setDate(1, date);
		ResultSet rst = pst.executeQuery();
		if(rst != null && rst.next()) {
			int count = rst.getInt(1);
			SwiftAccessor.closeConnection(conn);
			return count;
		}else {
			SwiftAccessor.closeConnection(conn);
			return 0;
		}
	}
	
	public int completedBookings(Date date) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) FROM booking WHERE BookingDate >= ? AND BookingStatus=completed");
		pst.setDate(1, date);
		ResultSet rst = pst.executeQuery();
		if(rst != null && rst.next()) {
			int count = rst.getInt(1);
			SwiftAccessor.closeConnection(conn);
			return count;
		}else {
			SwiftAccessor.closeConnection(conn);
			return 0;
		}
	}
	
	public double amountEarnedOverTime(Date date) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT AmountPaid FROM booking WHERE BookingDate >= ?");
		pst.setDate(1, date);
		ResultSet rst = pst.executeQuery();
		if(rst != null) {
			double earned = 0;
			while(rst.next()) {
				earned = earned + rst.getDouble(1);
			}
			SwiftAccessor.closeConnection(conn);
			return earned;
		}else {
			SwiftAccessor.closeConnection(conn);
			return 0.0;
		}
	}
	
	public int driverTrips(String driverID) throws SQLException, ClassNotFoundException {
		Vehicle vehicle = new VehicleDAO().getDriverVehicle(driverID);
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) booking WHERE bookingStatus=completed AND Vehicle_ID=?");
		pst.setString(1, vehicle.getVehicleID());
		ResultSet rst = pst.executeQuery();
		if(rst != null && rst.next()) {
			int count = rst.getInt(1);
			SwiftAccessor.closeConnection(conn);
			return count;
		}else {
			SwiftAccessor.closeConnection(conn);
			return 0;
		}
	}
	
}
