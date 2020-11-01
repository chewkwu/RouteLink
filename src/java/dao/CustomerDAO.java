package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Customer;

public class CustomerDAO {
	
	public void addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("INSERT INTO customer (customerPhone,customerFirstName,customerLastName) VALUES (?,?,?)");
		pst.setString(1, customer.getCustomerPhone());
		pst.setString(2, customer.getCustomerFirstName());
		pst.setString(3, customer.getCustomerLastName());
		pst.execute();
		SwiftAccessor.closeConnection(conn);
	}
	
	public Customer getCustomer(String phone) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM customer WHERE customerPhone=?");
		pst.setString(1, phone);
		ResultSet rst = pst.executeQuery();
		if(rst.next()) {
			Customer cust = new Customer();
			cust.setCustomerPhone(rst.getString(1));
			cust.setCustomerFirstName(rst.getString(2));
			cust.setCustomerLastName(rst.getString(3));
			SwiftAccessor.closeConnection(conn);
			return cust;
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
	public void updateCustomer(String phone,Customer customer) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("UPDATE customer SET customerPhone=?, customerFirstName=?, customerLastName=? WHERE customerPhone=?");
		pst.setString(1, customer.getCustomerPhone());
                pst.setString(2, customer.getCustomerFirstName());
                pst.setString(3, customer.getCustomerLastName());
                pst.setString(4, phone);
		pst.execute();
		SwiftAccessor.closeConnection(conn);
	}
	
	public void removeCustomer(String phone) throws SQLException, ClassNotFoundException {
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("DELETE FROM customer WHERE customerPhone=?");
		pst.setString(1, phone);
		pst.execute();
		SwiftAccessor.closeConnection(conn);
	}
	
	public List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException{
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM customer");
		ResultSet rst = pst.executeQuery();
		if(rst != null) {
			List<Customer> customers = new ArrayList<>();
			while(rst.next()) {
				Customer customer = new Customer();
				customer.setCustomerPhone(rst.getString(1));
				customer.setCustomerFirstName(rst.getString(2));
				customer.setCustomerLastName(rst.getString(3));
				customers.add(customer);
			}
			SwiftAccessor.closeConnection(conn);
			return customers;
		}else {
			SwiftAccessor.closeConnection(conn);
			return null;
		}
	}
	
}
