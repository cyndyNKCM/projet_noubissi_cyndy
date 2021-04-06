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

public class LivreDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/livre_details")) {

            int id = -1;
            if (request.getParameter("id") != null)
                id = Integer.parseInt(request.getParameter("id"));
            System.out.println(id);

            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            List<Emprunt> emprunts= new ArrayList<>();

            try {
                if (id > -1) {
                    request.setAttribute("id", id);
                    emprunts= empruntService.getListCurrentByLivre(id);
                    request.setAttribute("emprunts", emprunts);
                }
            } catch (ServiceException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            livreService.update(new Livre(Integer.parseInt(request.getParameter("id")), request.getParameter("titre"), request.getParameter("auteur"), request.getParameter("isbn")));
            request.setAttribute("Liste d'emprunts", empruntService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id"))));
            request.setAttribute("id", Integer.parseInt(request.getParameter("id")));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            try {
                request.setAttribute("Liste d'emprunts", empruntService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id"))));
            } catch (ServiceException serviceException) {
                System.out.println(serviceException.getMessage());
                serviceException.printStackTrace();
            }

            request.setAttribute("id", Integer.parseInt(request.getParameter("id")));
            request.setAttribute("erreur", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}