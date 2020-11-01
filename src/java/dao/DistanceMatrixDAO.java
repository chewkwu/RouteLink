package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.DistanceMatrix;

public class DistanceMatrixDAO {

	public DistanceMatrix getDistanceMatrix(String fromLocation, String toLocation) throws ClassNotFoundException, SQLException {
		DistanceMatrix distance = new DistanceMatrix();
		Connection conn = SwiftAccessor.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM distanceMatrix WHERE fromLocation= ? AND toLOcation=?");
		pst.setString(1, fromLocation);
		pst.setString(2, toLocation);
		ResultSet rst = pst.executeQuery();
		if(rst.next()) {
			distance.setFromLocation(rst.getString(1));
			distance.setToLocation(rst.getString(2));
			distance.setTime(rst.getDouble(3));
			distance.setDistance(rst.getDouble(4));
		}
		return distance;
	}
	
}
