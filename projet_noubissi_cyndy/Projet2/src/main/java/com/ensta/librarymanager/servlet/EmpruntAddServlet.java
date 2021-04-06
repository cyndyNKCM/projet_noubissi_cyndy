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


public class EmpruntAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if (action.equals("/emprunt_add")) {
            showMembreEmpruntsPossibles(request,response);
            showLivresDispo(request,response);
        }
    }


    private List<Livre> showLivresDispo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LivreServiceImpl livreImplService = LivreServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livreImplService.getListDispo();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("livres_dispo", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_add.jsp");
        dispatcher.forward(request, response);
        return livres;
    }


    private List<Membre> showMembreEmpruntsPossibles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        MembreServiceImpl membreImplService = MembreServiceImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try {
            membres = membreImplService.getListMembreEmpruntPossible();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("membres_dispo", membres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_add.jsp");
        dispatcher.forward(request, response);
        return membres;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLivre = Integer.parseInt(request.getParameter("idLivre"));
        int idMembre = Integer.parseInt(request.getParameter("idMembre"));
        LocalDate DateEmprunt=LocalDate.parse(request.getParameter("dateEmprunt"))  ;
        LocalDate DateRetour=LocalDate.parse(request.getParameter("dateRetour"))  ;
        EmpruntServiceImpl empruntImplService = EmpruntServiceImpl.getInstance();


        try {
            empruntImplService.create(idMembre, idLivre, DateEmprunt, DateRetour);

        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
