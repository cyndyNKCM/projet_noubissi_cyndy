package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.modele.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if (action.equals("/dashboard")) {
            int membersNumber = -1;
            membersNumber = showMembersNumber();
            request.setAttribute("NombreDesMembres", membersNumber);

            // Récupérer le nombre des livres
            int booksNumber = -1;
            booksNumber = showBooksNumber();
            request.setAttribute("NombreDesLivres", booksNumber);

            // Récupérer le nombre des emprunts
            int empruntsNumber = -1;
            empruntsNumber = showEmpruntsNumber();
            request.setAttribute("NombreDesEmprunts", empruntsNumber);

            // Récupérer les emprunts non encore rendus
            List<Emprunt> emprunts = new ArrayList<>();
            emprunts = showCurrentEmprunts();
            request.setAttribute("emprunt_list", emprunts);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
            dispatcher.forward(request, response);
        }
    }


    /**
     * Récuperer le nombre des membres de la base de données
     */
    private int showMembersNumber() throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        int k = 0;
        try {
            k = membreService.count();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return k;
    }

    /**
     * Récuperer le nombre des livres de la base de données
     */
    private int showBooksNumber() throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        int k = 0;
        try {
            k = livreService.count();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return k;
    }

    /**
     * Récuperer le nombre des emprunts de la base de données
     */
    private int showEmpruntsNumber() throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        int k = 0;
        try {
            k = empruntService.count();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return k;
    }

    /**
     * Récupérer la liste des emprunts non encore rendus
     */
    private List<Emprunt> showCurrentEmprunts() throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntService.getListCurrent();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return emprunts;
    }
}
