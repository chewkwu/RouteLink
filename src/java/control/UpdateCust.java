/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.CustomerDAO;
import entities.Customer;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class UpdateCust extends HttpServlet {

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
            out.println("<title>Servlet UpdateCust</title>");            
            out.println("</head>");
            out.println("<body>");
             User user = (User)request.getSession().getAttribute("user");
                try {
                    Customer cust = new Customer();
                    cust.setCustomerFirstName(request.getParameter("fname"));
                    cust.setCustomerLastName(request.getParameter("lname"));
                    cust.setCustomerPhone(request.getParameter("phone"));
                    new CustomerDAO().updateCustomer(request.getParameter("currphone"), cust);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/custfeed.jsp");
                    request.setAttribute("feed", "Customer updated Successfully");
                    rd.forward(request, response);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(UpdateCust.class.getName()).log(Level.SEVERE, null, ex);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/custfeed.jsp");
                    request.setAttribute("feed", "Failed to add Customer, please check logs");
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
