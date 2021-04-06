package com.ensta.librarymanager.servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.modele.*;
import java.time.LocalDate;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MembreAddServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/membre_add")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
            dispatcher.forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreserviceimpl = MembreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            int membreId = membreserviceimpl.create(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("adresse"), request.getParameter("email"), request.getParameter("telephone"));

            request.setAttribute("id", membreId);
            request.setAttribute("Liste d'emprunts", empruntService.getListCurrentByMembre(membreId));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            request.setAttribute("erreur", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
