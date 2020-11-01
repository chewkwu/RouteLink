package entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class Customer {

	private String customerPhone;
	private String customerFirstName;
	private String customerLastName;
	
	public String getCustomerPhone() {
		return customerPhone;
	}
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	@XmlElement
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	@XmlElement
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	@XmlElement
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	
}
