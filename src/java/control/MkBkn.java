/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.BookingDAO;
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
public class MkBkn extends HttpServlet {

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
            
            User user = (User)request.getSession().getAttribute("user");
            
            //pending count
            if(request.getServletPath().equalsIgnoreCase("/getPendingCount")){
                try {
                    System.out.println("pending is: " + new BookingDAO().getPendingCount());
                    out.print(new BookingDAO().getPendingCount());
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(MkBkn.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                out.close();
                return;
            }
            Booking book = new Booking();
		book.setLocation(request.getParameter("location"));
		book.setDestination(request.getParameter("destination"));
                book.setHasReturn(request.getParameter("hasReturn"));
		book.setNumbOfPassengers(Integer.parseInt(request.getParameter("psng")));
		book.setDepartureTime(request.getParameter("deptime"));
		book.setCustomerID(request.getParameter("custphone"));
                book.setLocationAddress(request.getParameter("address"));
		int capacity = 0;
			if(book.getNumbOfPassengers() > 0 && book.getNumbOfPassengers() <= 3) {
				capacity = 3;
			}else if(book.getNumbOfPassengers() > 3 && book.getNumbOfPassengers() <= 7) {
				capacity = 7;
			}else if(book.getNumbOfPassengers() > 7 && book.getNumbOfPassengers() >= 12){
				capacity = 12;
			}else{
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/bknfail.jsp");
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
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/bknfail.jsp");
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
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/bknfail.jsp");
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
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/bknfail.jsp");
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
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/bknfail.jsp");
					request.setAttribute("msg", "System Error, please check logs");
					rd.forward(request, response);
				}
			}
                         }
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/cnfbkn.jsp");
                        request.getSession().setAttribute("booking", book);
                        rd.forward(request, response);
	/*	try {
			Booking booked = new BookingDAO().addBooking(book);
			if(booked != null) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/bkn.jsp");
			    request.setAttribute("bkn", booked);
			    rd.forward(request, response);
			}else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/bknfail.jsp");
			    request.setAttribute("msg", "Failed to book trip");
			    rd.forward(request, response);
			}
		//} catch (ClassNotFoundException | SQLException | MalformedURLException e) {
                } catch (Exception e) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/bknfail.jsp");
		    request.setAttribute("msg", "Failed: " + e.getMessage());
		    rd.forward(request, response);
                    Logger.getLogger(MkBkn.class.getName()).log(Level.SEVERE, null, e);
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
