package entities;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "booking")
public class Booking {

	private long bookingID;
	private String vehicleID;
	private double tripDistance;
	private String hasReturn;
	private double calculatedCost;
	private String bookingStatus;
	private String paymentStatus;
	private double paidAmount;
	private String departureTime;
	private Date bookingDate;
	private String arrivalTime;
	private String customerID;
	private String location;
	private String destination;
	private int numbOfPassengers;
	private String locationAddress;
	
	public long getBookingID() {
		return bookingID;
	}
	public String getVehicleID() {
		return vehicleID;
	}
	public double getTripDistance() {
		return tripDistance;
	}
	public String isHasReturn() {
		return hasReturn;
	}
	public double getCalculatedCost() {
		return calculatedCost;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public String getLocationAddress() {
		return locationAddress;
	}
	@XmlElement
	public void setBookingID(long bookingID) {
		this.bookingID = bookingID;
	}
	@XmlElement
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}
	@XmlElement
	public void setCalculatedCost(double calculatedCost) {
		this.calculatedCost = calculatedCost;
	}
	@XmlElement
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	@XmlElement
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	@XmlElement
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	@XmlElement
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	@XmlElement
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	@XmlElement
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	@XmlElement
	public String getCustomerID() {
		return customerID;
	}
	@XmlElement
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	@XmlElement
	public String getLocation() {
		return location;
	}
	@XmlElement
	public String getDestination() {
		return destination;
	}
	@XmlElement
	public void setLocation(String location) {
		this.location = location;
	}
	@XmlElement
	public void setDestination(String destination) {
		this.destination = destination;
	}
	@XmlElement
	public int getNumbOfPassengers() {
		return numbOfPassengers;
	}
	@XmlElement
	public void setNumbOfPassengers(int numbOfPassengers) {
		this.numbOfPassengers = numbOfPassengers;
	}
	@XmlElement
	public void setTripDistance(double tripDistance) {
		this.tripDistance = tripDistance;
	}
	@XmlElement
	public void setHasReturn(String hasReturn) {
		this.hasReturn = hasReturn;
	}
	@XmlElement
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}
        
}
