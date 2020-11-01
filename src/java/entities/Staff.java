package entities;

public class Staff {

	private String staffPhone;
	private String staffFirstName;
	private String staffLastName;
	private String address;
	private String staffType;
	private String staffPassword;
	
	public String getStaffPhone() {
		return staffPhone;
	}
	public String getStaffFirstName() {
		return staffFirstName;
	}
	public String getStaffLastName() {
		return staffLastName;
	}
	public String getAddress() {
		return address;
	}
	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
	}
	public void setStaffFirstName(String staffFirstName) {
		this.staffFirstName = staffFirstName;
	}
	public void setStaffLastName(String staffLastName) {
		this.staffLastName = staffLastName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStaffType() {
		return staffType;
	}
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}
	public String getStaffPassword() {
		return staffPassword;
	}
	public void setStaffPassword(String staffPassword) {
		this.staffPassword = staffPassword;
	}
		
}
