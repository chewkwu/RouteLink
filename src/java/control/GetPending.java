/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.BookingDAO;
import dao.VehicleDAO;
import entities.Booking;
import entities.TheBooking;
import entities.User;
import entities.Vehicle;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yhoucee
 */
public class GetPending extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetPending</title>");            
            out.println("</head>");
            out.println("<body>");
             User user = (User)request.getSession().getAttribute("user");
		try {
			Booking aBook = new BookingDAO().aPendingBookings();
			if(aBook == null) {
				response.getWriter().println("There are no pending bookings");
				response.getWriter().println("You will be redirected in some seconds");
				new java.util.Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/booking.jsp");
						try {
							rd.forward(request, response);
						} catch (ServletException | IOException e) {
							e.printStackTrace();
						}
					}
					
				}, 5000);
			}else {
				List<Vehicle> vech = new VehicleDAO().availableVehicless(aBook.getNumbOfPassengers());
                                System.out.println(vech.size() + " vehicles in list");
                                TheBooking theBook = new TheBooking();
                                theBook.setBooking(aBook);
                                theBook.setVehicles(vech);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/pndbkn.jsp");
				request.setAttribute("theBook", theBook);
				rd.forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			 Logger.getLogger(GetPending.class.getName()).log(Level.SEVERE, null, e);
                }
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
