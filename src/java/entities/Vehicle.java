package entities;

public class Vehicle {

	private String vehicleID;
	private String vehicleType;
	private String driverID;
	private String availabilityStatus;
	private int capacity;
	
	public String getVehicleID() {
		return vehicleID;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public String getDriverID() {
		return driverID;
	}
	public String getAvailabilityStatus() {
		return availabilityStatus;
	}
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}
	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
