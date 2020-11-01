/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.BookingDAO;
import entities.Booking;
import java.io.IOException;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Youcee
 */
@Path("bookingService")
public class BookingService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookingService
     */
    public BookingService() {
    }

    /**
     * Retrieves representation of an instance of services.BookingService
     * @param booking
     * @return an instance of java.lang.String
     * @throws java.lang.ClassNotFoundException
     * @throws java.io.IOException
     */
    @Path("addBookingService")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Booking addBookingService(Booking booking) throws ClassNotFoundException, IOException, Exception {
        return new BookingDAO().addBooking(booking);
    }

    /**
     * PUT method for updating or creating an instance of BookingService
     * @param bookingID
     * @return 
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    @Path("viewBookingService")
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Booking viewBookingService(long bookingID) throws SQLException, ClassNotFoundException {
        return new BookingDAO().viewBooking(bookingID);
    }
    
    
    @Path("removeBookingService")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void removeBookingService(long bookingID) throws SQLException, Exception{
        new BookingDAO().removeBooking(bookingID);
    }
}
