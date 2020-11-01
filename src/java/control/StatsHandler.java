/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.AdminStats;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
public class StatsHandler extends HttpServlet {

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
            out.println("<title>Servlet StatsHandler</title>");            
            out.println("</head>");
            out.println("<body>");
            if(request.getParameter("statsChoice").equals("OverallBookings")) {
			try {
				int totalBookings = new AdminStats().overallBookings(Date.valueOf(request.getParameter("date")));
				String msg = "The overall bookings for the period is: " + totalBookings;
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminmsg.jsp");
				request.setAttribute("msg",msg);
				rd.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
			        Logger.getLogger(StatsHandler.class.getName()).log(Level.SEVERE, null, e);	
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminerror.jsp");
				request.setAttribute("msg","failed to access data, check logs");
				rd.forward(request, response);
			} 
		}else if(request.getParameter("statsChoice").equals("CompletedBookings")) {
			try {
				int completedBookings = new AdminStats().completedBookings(Date.valueOf(request.getParameter("date")));
				String msg = "The completed bookings for the period is: " + completedBookings;
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminmsg.jsp");
				request.setAttribute("msg",msg);
				rd.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminerror.jsp");
				request.setAttribute("msg","failed to access data, check logs");
				rd.forward(request, response);
			}
		}else if(request.getParameter("statsChoice").equals("AmountEarned")) {
			try {
				double amountEarned = new AdminStats().amountEarnedOverTime(Date.valueOf(request.getParameter("date")));
				String msg = "The amount earned for the period is: " + amountEarned;
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminmsg.jsp");
				request.setAttribute("msg",msg);
				rd.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminerror.jsp");
				request.setAttribute("msg","failed to access data, check logs");
				rd.forward(request, response);
			}
		}else{
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminerror.jsp");
                    request.setAttribute("msg","You didnt select a stats option");
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
