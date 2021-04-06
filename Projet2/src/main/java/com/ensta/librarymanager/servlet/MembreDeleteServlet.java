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

public class MembreDeleteServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/membre_delete")) {
            DeleteMembres(request,response);
        }
    }

    private void DeleteMembres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        int id = -1;
        if (request.getParameter("id") != null)
            id = Integer.parseInt(request.getParameter("id"));

        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        List<Membre> membres = new ArrayList<>();

        try {
            membres = membreService.getList();
            if (id > -1)
                request.setAttribute("id", id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("liste des membes ", membres);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        List<Membre> membres = new ArrayList<>();

        try {
            if (request.getParameter("id") == "")
                throw new ServletException("aucun membre selectionné");
            else if (!empruntService.getListCurrentByMembre(Integer.parseInt(request.getParameter("id"))).isEmpty()) {
                throw new ServletException("impossible de supprimer ce membre");
            } else {
                membreService.delete(Integer.parseInt(request.getParameter("id")));

                membres = membreService.getList();

                request.setAttribute("liste de membres", membres);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ServletException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            //  list of the current Books:
            try {
                membres = membreService.getList();
            } catch (ServiceException serviceException) {
                System.out.println(serviceException.getMessage());
                serviceException.printStackTrace();
            }

            request.setAttribute("Liste de livres ", membres);
            request.setAttribute("erreur", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
            dispatcher.forward(request, response);
        }
    }
}
