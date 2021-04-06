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


public class EmpruntListServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if ("/emprunt_list".equals(action)) {
            showAll(request, response);
        } else {
            showCurrent(request, response);
        }
    }

    private void showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntServiceImpl empruntImplService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts=new ArrayList<>();
        try {
            emprunts=empruntImplService.getList();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("liste_emprunts :",emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_list.jsp");
        dispatcher.forward(request, response);
    }


    private void showCurrent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntServiceImpl empruntImplService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts=new ArrayList<>();
        try {
            emprunts=empruntImplService.getListCurrent();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("liste_emprunts en cours :",emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_list.jsp");
        dispatcher.forward(request, response);
    }

}
