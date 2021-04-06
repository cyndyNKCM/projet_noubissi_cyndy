package com.ensta.librarymanager.modele.test;

import com.ensta.librarymanager.DAO.EmpruntDaoImpl;
import com.ensta.librarymanager.DAO.LivreDaoImpl;
import com.ensta.librarymanager.DAO.MembreDaoImpl;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.*;
import java.util.*;

import java.time.LocalDate;

public class ServiceTest {
    public static void main(String[] args) {
        MembreServiceImpl mem = MembreServiceImpl.getInstance();
        LivreServiceImpl liv = LivreServiceImpl.getInstance();
        EmpruntServiceImpl emp = EmpruntServiceImpl.getInstance();

        Membre m= null;
        try {
            m = mem.getById(2);
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }

        Livre l= null;
        try {
            l = liv.getById(2);
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }

        Emprunt e= null;
        try {
            e = emp.getById(2);
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }


        List<Membre> me=new ArrayList<>();
        try {
           me= mem.getListMembreEmpruntPossible();

        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }


        List<Livre> livres=new ArrayList<>();
        try {
            livres= liv.getListDispo();

        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }



        /*retourner un livre*/
        try {
            emp.returnBook(1);
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }

    }

}
