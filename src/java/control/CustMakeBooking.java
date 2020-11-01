/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.DistanceMatrixDAO;
import entities.Booking;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class CustMakeBooking extends HttpServlet {

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
            out.println("<title>Servlet CustMakeBooking</title>");            
            out.println("</head>");
            out.println("<body>");
             User user = (User)request.getSession().getAttribute("user");
			Booking book = new Booking();
			book.setLocation(request.getParameter("location"));
			book.setDestination(request.getParameter("destination"));
			book.setHasReturn(request.getParameter("return"));
			book.setDepartureTime(request.getParameter("deptime"));
                        book.setLocationAddress(request.getParameter("lctnAdd"));
			book.setNumbOfPassengers(Integer.parseInt(request.getParameter("psng")));
                        book.setCustomerID(user.getUsername());
			int capacity = 0;
			if(book.getNumbOfPassengers() > 0 && book.getNumbOfPassengers() <= 3) {
				capacity = 3;
			}else if(book.getNumbOfPassengers() > 3 && book.getNumbOfPassengers() <= 7) {
				capacity = 7;
			}else if(book.getNumbOfPassengers() > 7 && book.getNumbOfPassengers() >= 12){
				capacity = 12;
			}else{
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookfail.jsp");
		            request.setAttribute("msg", "The maximum amount of passengers is 12");
			    rd.forward(request, response);
                        }
                        if(book.getLocation().equalsIgnoreCase("Enugu-North")){
                            double totalTime = 0;
                            double distance = 0;
                            if(book.getDestination().equalsIgnoreCase("Enugu-North")){
                                if(book.isHasReturn().equalsIgnoreCase("true")){
                                totalTime = 40;
                                distance = 30;
                                book.setCalculatedCost(12.5 * totalTime * book.getNumbOfPassengers());
                                book.setTripDistance(distance);
                                }else{
                                totalTime = 25;
                                distance = 20;
                                book.setCalculatedCost(15 * totalTime * book.getNumbOfPassengers());
                                book.setTripDistance(distance);
                                }
                            }else{
                                if(book.isHasReturn().equalsIgnoreCase("true")){
                                    try {
                                        totalTime += 11;
                                        distance += 9;
                                        totalTime += (new DistanceMatrixDAO().getDistanceMatrix(book.getLocation(), book.getDestination()).getTime() * 2);
                                        book.setCalculatedCost(12.5 * totalTime * book.getNumbOfPassengers());
                                        distance += (new DistanceMatrixDAO().getDistanceMatrix(book.getLocation(), book.getDestination()).getDistance() * 2);
                                        book.setTripDistance(distance);
                                    } catch (ClassNotFoundException | SQLException ex) {
                                        Logger.getLogger(CustMakeBooking.class.getName()).log(Level.SEVERE, null, ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookfail.jsp");
					request.setAttribute("msg", "System Error, please check logs");
					rd.forward(request, response);
                                    }
                                }else{
                                     try {
                                        totalTime += 11;
                                        distance += 9;
                                        totalTime += (new DistanceMatrixDAO().getDistanceMatrix(book.getLocation(), book.getDestination()).getTime());
                                        book.setCalculatedCost(12.5 * totalTime * book.getNumbOfPassengers());
                                        distance += (new DistanceMatrixDAO().getDistanceMatrix(book.getLocation(), book.getDestination()).getDistance());
                                        book.setTripDistance(distance);
                                    } catch (ClassNotFoundException | SQLException ex) {
                                        Logger.getLogger(CustMakeBooking.class.getName()).log(Level.SEVERE, null, ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookfail.jsp");
					request.setAttribute("msg", "System Error, please check logs");
					rd.forward(request, response);
                                    }
                                }
                            }
                        }else{
			if(book.isHasReturn().equalsIgnoreCase("true")) {
				double totalTime = 0;
                                double distance = 0;
				try {
					totalTime += new DistanceMatrixDAO().getDistanceMatrix("Enugu-North", book.getLocation()).getTime();
					totalTime += (new DistanceMatrixDAO().getDistanceMatrix(book.getLocation(), book.getDestination()).getTime() * 2);
					book.setCalculatedCost(12.5 * totalTime * book.getNumbOfPassengers());
                                        distance += new DistanceMatrixDAO().getDistanceMatrix("Enugu-North", book.getLocation()).getDistance();
					distance += (new DistanceMatrixDAO().getDistanceMatrix(book.getLocation(), book.getDestination()).getDistance() * 2);
                                        book.setTripDistance(distance);
				} catch (ClassNotFoundException | SQLException e) {
                                        Logger.getLogger(CustMakeBooking.class.getName()).log(Level.SEVERE, null, e);
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookfail.jsp");
					request.setAttribute("msg", "System Error, please check logs");
					rd.forward(request, response);
				}
			}else {
				double totalTime = 0;
                                double distance = 0;
				try {
					totalTime += new DistanceMatrixDAO().getDistanceMatrix("Enugu-North", book.getLocation()).getTime();
					totalTime += new DistanceMatrixDAO().getDistanceMatrix(book.getLocation(), book.getDestination()).getTime();
					book.setCalculatedCost(15 * totalTime * book.getNumbOfPassengers());
                                        distance += new DistanceMatrixDAO().getDistanceMatrix("Enugu-North", book.getLocation()).getDistance();
					distance += (new DistanceMatrixDAO().getDistanceMatrix(book.getLocation(), book.getDestination()).getDistance());
                                        book.setTripDistance(distance);
				} catch (ClassNotFoundException | SQLException e) {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookfail.jsp");
					request.setAttribute("msg", "System Error, please check logs");
					rd.forward(request, response);
				}
			}
                        }
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/cnfbook.jsp");
                        request.getSession().setAttribute("booking", book);
                        rd.forward(request, response);
           /*      try {
                     Booking booking = new BookingDAO().addBooking(book);
                     RequestDispatcher rd = getServletContext().getRequestDispatcher("/confbkn.jsp");
		     request.setAttribute("booking", booking);
		     rd.forward(request, response);	
		} catch (Exception ex) {
                     Logger.getLogger(CustMakeBooking.class.getName()).log(Level.SEVERE, null, ex);
                     RequestDispatcher rd = getServletContext().getRequestDispatcher("/bknerr.jsp");
                     request.setAttribute("msg", "Failed to add booking, please try again with valid entries");
                     rd.forward(request, response);
                 } */
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
