/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.StaffDAO;
import entities.Staff;
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
public class StaffLogin extends HttpServlet {

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
            out.println("<title>Servlet StaffLogin</title>");            
            out.println("</head>");
            out.println("<body>");
             try {
			Staff staff = new StaffDAO().getStaff(request.getParameter("stfID"));
			if(staff != null) {
				if(staff.getStaffPassword().equals(request.getParameter("pword"))) {
					if(staff.getStaffType().equals("staff")) {
                                                User user = new User();
						user.setUsername(staff.getStaffPhone());
						user.setUserRole(staff.getStaffType());
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/staffmenu.jsp");
						request.getSession().setAttribute("user", user);
                                                rd.forward(request, response);
					}else {
						User user = new User();
						user.setUsername(staff.getStaffPhone());
						user.setUserRole(staff.getStaffType());
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminmenu.jsp");
						request.getSession().setAttribute("user", user);
                                                rd.forward(request, response);
					}
				}else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/stafflogfail.jsp");
					String msg = "Incorrect password";
					request.setAttribute("msg", msg);
					rd.forward(request, response);
				}
			}else{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/stafflogfail.jsp");
				String msg = "No such staff";
				request.setAttribute("msg", msg);
				rd.forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(StaffLogin.class.getName()).log(Level.SEVERE, null, e);
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
