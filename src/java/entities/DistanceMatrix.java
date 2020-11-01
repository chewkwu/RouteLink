package entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "distanceMatrix")
public class DistanceMatrix {

	private String fromLocation;
	private String toLocation;
	private double time;
	private double distance;
	
	public String getFromLocation() {
		return fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public double getTime() {
		return time;
	}
	public double getDistance() {
		return distance;
	}
        @XmlElement
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
        @XmlElement
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
        @XmlElement
	public void setTime(double time) {
		this.time = time;
	}
        @XmlElement
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}
