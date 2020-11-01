/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.DistanceMatrixDAO;
import entities.DistanceMatrix;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Youcee
 */
@Path("distanceMatrixService")
public class DistanceMatrixService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DistanceMatrixService
     */
    public DistanceMatrixService() {
    }

    /**
     * Retrieves representation of an instance of services.DistanceMatrixService
     * @param fromLocation
     * @param toLocation
     * @return an instance of java.lang.String
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    @Path("getDistanceMatrixService")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public DistanceMatrix getDistanceMatrixService(String fromLocation, String toLocation) throws ClassNotFoundException, SQLException {
        return new DistanceMatrixDAO().getDistanceMatrix(fromLocation, toLocation);
    }

}
