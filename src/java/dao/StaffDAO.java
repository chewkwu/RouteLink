package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Staff;

public class StaffDAO {
	
	public void addStaff(Staff staff) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("INSERT INTO staff VALUES (?,?,?,?,?,?)");
		pst.setString(1, staff.getStaffPhone());
		pst.setString(2, staff.getStaffLastName());
		pst.setString(3, staff.getStaffFirstName());
		pst.setString(4, staff.getAddress());
		pst.setString(5, staff.getStaffType());
		pst.setString(6, staff.getStaffPassword());
		pst.execute();
		SwiftAccessor.closeConnection(conn);
	}
	
	public Staff getStaff(String staffPhone) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM staff WHERE staffPhone=?");
		pst.setString(1, staffPhone);
		ResultSet rst = pst.executeQuery();
		if(rst.next()) {
			Staff staff = new Staff();
			staff.setStaffPhone(rst.getString(1));
			staff.setStaffLastName(rst.getString(2));
			staff.setStaffFirstName(rst.getString(3));
			staff.setAddress(rst.getString(4));
			staff.setStaffType(rst.getString(5));
			staff.setStaffPassword(rst.getString(6));
			SwiftAccessor.closeConnection(conn);
			return staff;
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
	public void updateStaff(Staff staff, String password) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("UPDATE staff SET staffPassword=? WHERE staffPhone=? AND staffPassword=?");
		pst.setString(1, password);
		pst.setString(2, staff.getStaffPhone());
		pst.setString(3, staff.getStaffPassword());
		pst.execute();
		SwiftAccessor.closeConnection(conn);
	}
	
	public void removeStaff(String phone) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("DELETE FROM staff WHERE staffPhone=?");
		pst.setString(1, phone);
		pst.execute();
		SwiftAccessor.closeConnection(conn);
	}
	
	public List<Staff> getAllStaff() throws SQLException, ClassNotFoundException{
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM staff");
		ResultSet rst = pst.executeQuery();
		if(rst != null) {
			List<Staff> staffs = new ArrayList<>();
			while(rst.next()) {
				Staff staff = new Staff();
				staff.setStaffPhone(rst.getString(1));
				staff.setStaffLastName(rst.getString(2));
				staff.setStaffFirstName(rst.getString(3));
				staff.setAddress(rst.getString(4));
				staff.setStaffType(rst.getString(5));
				staff.setStaffPassword(rst.getString(6));
				staffs.add(staff);
			}
			SwiftAccessor.closeConnection(conn);
			return staffs;
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
}
