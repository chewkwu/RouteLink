/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.CustomerDAO;
import entities.Customer;
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
@Path("customerService")
public class CustomerService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerService
     */
    public CustomerService() {
    }

    /**
     * Retrieves representation of an instance of services.CustomerService
     * @param phone
     * @return an instance of java.lang.String
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    @Path("getCustomerService")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Customer getCustomerService(String phone) throws SQLException, ClassNotFoundException {
        return new CustomerDAO().getCustomer(phone);
    }

}
