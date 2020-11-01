package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Driver;

public class DriverDAO {
	
	public void registerDriver(Driver driver) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("INSERT INTO driver VALUES (?,?,?,?)");
		pst.setString(1, driver.getDriverPhone());
		pst.setString(2, driver.getFirstName());
		pst.setString(3, driver.getLastName());
		pst.setString(4, driver.getAddress());
		pst.execute();
		SwiftAccessor.closeConnection(conn);
	}
	
	public void updateDriver(Driver driver) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("UPDATE driver SET lastName=?, firstName=? WHERE driverPhone=?");
	    pst.setString(1, driver.getLastName());
	    pst.setString(2, driver.getAddress());
            pst.setString(3, driver.getDriverPhone());
	    pst.execute();
	    SwiftAccessor.closeConnection(conn);
	}
	
	public void removeDriver(String phone) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("DELETE FROM driver WHERE driverPhone=?");
		pst.setString(1, phone);
		pst.execute();
		SwiftAccessor.closeConnection(conn);
	}
	
	public Driver getDriver(String phone) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM driver WHERE driverPhone=?");
		pst.setString(1, phone);
		ResultSet rst = pst.executeQuery();
		if(rst.next()) {
			Driver driver = new Driver();
			driver.setDriverPhone(rst.getString(1));
			driver.setFirstName(rst.getString(2));
			driver.setLastName(rst.getString(3));
			driver.setAddress(rst.getString(4));
			SwiftAccessor.closeConnection(conn);
			return driver;
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
	public List<Driver> getAllDrivers() throws SQLException, ClassNotFoundException{
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM driver");
		ResultSet rst = pst.executeQuery();
		if(rst != null) {
			List<Driver> drivers = new ArrayList<>();
			while(rst.next()) {
				Driver driver = new Driver();
				driver.setDriverPhone(rst.getString(1));
				driver.setFirstName(rst.getString(2));
				driver.setLastName(rst.getString(3));
				driver.setAddress(rst.getString(4));
				drivers.add(driver);
			}
			SwiftAccessor.closeConnection(conn);
			return drivers;
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
}
