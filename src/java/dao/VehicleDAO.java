package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entities.Vehicle;

public class VehicleDAO {

	public Vehicle addVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("INSERT INTO vehicle VALUES (?,?,?,?,?)");
	    pst.setString(1, vehicle.getVehicleID());
	    pst.setString(2, vehicle.getVehicleType());
            pst.setString(3, vehicle.getDriverID());
	    pst.setString(4, vehicle.getAvailabilityStatus());
	    pst.setInt(5, vehicle.getCapacity());
	    pst.execute();
	    SwiftAccessor.closeConnection(conn);
	    return vehicle;
	}
	
	public Vehicle updateVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("UPDATE vehicle SET availabilityStatus=? WHERE vehicleID=?");
		pst.setString(1, vehicle.getAvailabilityStatus());
                pst.setString(2, vehicle.getVehicleID());
		pst.execute();
		PreparedStatement pst2 = conn.prepareStatement("SELECT * FROM vehicle WHERE vehicleID=?");
		pst2.setString(1, vehicle.getVehicleID());
		ResultSet rst = pst2.executeQuery();
		if(rst.next()) {
			vehicle.setVehicleType(rst.getString(2));
		}
		SwiftAccessor.closeConnection(conn);
		return vehicle;
	}
	
	public void removeVehicle(String id) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("DELETE FROM vehile WHERE vehicleID=?");
		pst.setString(1, id);
		pst.execute();
		SwiftAccessor.closeConnection(conn);
	}
	
	public Vehicle getVehicle(String vehicleID) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM vehicle WHERE vehicleID=?");
		pst.setString(1, vehicleID);
		ResultSet rst = pst.executeQuery();
		if(rst.next()) {
			Vehicle vehicle = new Vehicle();
			vehicle.setVehicleID(rst.getString(1));
			vehicle.setVehicleType(rst.getString(2));
                        vehicle.setDriverID(rst.getString(3));
			vehicle.setAvailabilityStatus(rst.getString(4));
			vehicle.setCapacity(rst.getInt(5));
			SwiftAccessor.closeConnection(conn);
			return vehicle;
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
	public List<Vehicle> getAllVehicles() throws SQLException, ClassNotFoundException{
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM vehicle");
		ResultSet rst = pst.executeQuery();
		if(rst != null) {
			List<Vehicle> vehicles = new ArrayList<>();
			while(rst.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setVehicleID(rst.getString(1));
				vehicle.setVehicleType(rst.getString(2));
				vehicle.setAvailabilityStatus(rst.getString(3));
				vehicle.setDriverID(rst.getString(4));
				vehicles.add(vehicle);
			}
			SwiftAccessor.closeConnection(conn);
			return vehicles;
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
	public int vehicleTypeCount(Vehicle vehicle) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) FROM vehicles WHERE vehicleType=? AND availabilityStatus=?");
		ResultSet rst = pst.executeQuery();
		if(rst.next()) {
			int count = rst.getInt(1);
			SwiftAccessor.closeConnection(conn);
			return count;
		}else {
			SwiftAccessor.closeConnection(conn);
			return 0;
		}
	}
	
	public Vehicle getDriverVehicle( String driverID) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM vehicle WHERE driverID=?");
		pst.setString(1, driverID);
		ResultSet rst = pst.executeQuery();
		if(rst.next()) {
			Vehicle vehicle = new Vehicle();
			vehicle.setVehicleID(rst.getString(1));
			vehicle.setVehicleType(rst.getString(2));
			vehicle.setAvailabilityStatus(rst.getString(3));
			vehicle.setDriverID(rst.getString(4));
			SwiftAccessor.closeConnection(conn);
			return vehicle;
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
	public List<Vehicle> availableVehicless(int cap) throws ClassNotFoundException, SQLException{
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM vehicle WHERE availabilityStatus=? AND capacity>=?");
		pst.setString(1, "available");
		pst.setInt(2, cap);
		ResultSet rst = pst.executeQuery();
		List<Vehicle> vehicles = new ArrayList<>();
		while(rst.next()) {
			Vehicle vehicle = new Vehicle();
			vehicle.setVehicleID(rst.getString(1));
			vehicle.setVehicleType(rst.getString(2));
			vehicle.setDriverID(rst.getString(3));
			vehicle.setAvailabilityStatus(rst.getString(4));
			vehicle.setCapacity(rst.getInt(5));
			vehicles.add(vehicle);
		}
		SwiftAccessor.closeConnection(conn);
		return vehicles;
	}
	
}
