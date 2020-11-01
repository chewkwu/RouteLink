/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.BookingDAO;
import entities.Booking;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
//import java.time.LocalDate;
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
public class MkBooking extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MkBooking</title>");            
            out.println("</head>");
            out.println("<body>");
             Booking book = new Booking();
		book.setLocation(request.getParameter("location"));
		book.setDestination(request.getParameter("destination"));
		book.setNumbOfPassengers(Integer.parseInt(request.getParameter("psng")));
		book.setDepartureTime(request.getParameter("deptime"));
		book.setCustomerID(request.getParameter("custphone"));
		book.setBookingStatus("pending");
		book.setPaymentStatus("not paid");
		//book.setBookingDate(Date.valueOf(LocalDate.now()));
		book.setBookingDate(new Date(new java.util.Date().getTime()));
		book.setCalculatedCost(Double.parseDouble(request.getParameter("cost")));
		try {
			Booking booked = new BookingDAO().addBooking(book);
			if(booked != null) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/book.jsp");
			    request.setAttribute("bkn", booked);
			    rd.forward(request, response);
			}else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookfail.jsp");
			    request.setAttribute("msg", "Failed to book trip");
			    rd.forward(request, response);
			}
		} catch (Exception e) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/bknfail.jsp");
		    request.setAttribute("msg", "Failed : " + e.getMessage());
			Logger.getLogger(MkBooking.class.getName()).log(Level.SEVERE, null, e);
			rd.forward(request, response);
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
