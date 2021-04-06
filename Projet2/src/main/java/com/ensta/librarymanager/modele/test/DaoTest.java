package com.ensta.librarymanager.modele.test;
import com.ensta.librarymanager.DAO.*;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

import java.time.LocalDate;
import java.util.*;

public class DaoTest {
    public static void main(String[] args) {
        MembreDaoImpl mem = MembreDaoImpl.getInstance();
        LivreDaoImpl livre = LivreDaoImpl.getInstance();
        EmpruntDaoImpl emp = EmpruntDaoImpl.getInstance();


        List<Livre> livres=new ArrayList<>();

        try {
            livres=livre.getList();
        } catch (DaoException daoException) {
            daoException.printStackTrace();
        }

        List<Membre> membres=new ArrayList<>();
        try {
            membres=mem.getList();
        } catch (DaoException daoException) {
            daoException.printStackTrace();
        }

        List<Emprunt> emprunts=new ArrayList<>();
        try {
            emprunts=emp.getList();
        } catch (DaoException daoException) {
            daoException.printStackTrace();
        }


        try {
            Livre l = livre.getById(1);
        } catch (DaoException d) {
            d.printStackTrace();
        }


        try {
            Membre m= mem.getById(1);
        } catch (DaoException daoException) {
            daoException.printStackTrace();
        }

        try {
            Emprunt emprunt= emp.getById(1);
        } catch (DaoException daoException) {
            daoException.printStackTrace();
        }

        int nbEmpr=0;
        try {
            nbEmpr=emp.count();
        } catch (DaoException daoException) {
            daoException.printStackTrace();
        }

        int nbL;
        try {
            nbL=livre.count();
        } catch (DaoException daoException) {
            daoException.printStackTrace();
        }

        int nbM;
        try {
            nbM=mem.count();
        } catch (DaoException daoException) {
            daoException.printStackTrace();
        }

    }
}
