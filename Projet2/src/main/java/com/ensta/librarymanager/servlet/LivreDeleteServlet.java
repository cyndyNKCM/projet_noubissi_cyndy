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

public class LivreDeleteServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/livre_delete")) {
            DeleteLivres(request,response);
        }
    }

    private void DeleteLivres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        int id = -1;
        if (request.getParameter("id") != null)
            id = Integer.parseInt(request.getParameter("id"));

        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();

        try {
            livres = livreService.getList();
            if (id > -1)
                request.setAttribute("id", id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("liste des livres ", livres);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();

        try {
            if (request.getParameter("id") == "")
                throw new ServletException("aucun livre selectionné");
            else if (!empruntService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id"))).isEmpty()) {
                throw new ServletException("impossible de supprimer un livre emprunté");
            } else {
                livreService.delete(Integer.parseInt(request.getParameter("id")));

                livres = livreService.getList();

                request.setAttribute("liste de livres", livres);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
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
                livres = livreService.getList();
            } catch (ServiceException serviceException) {
                System.out.println(serviceException.getMessage());
                serviceException.printStackTrace();
            }

            request.setAttribute("Liste de livres ", livres);
            request.setAttribute("erreur", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
            dispatcher.forward(request, response);
        }
    }
}

