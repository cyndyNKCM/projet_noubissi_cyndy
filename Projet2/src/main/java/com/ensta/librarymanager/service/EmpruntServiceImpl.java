package com.ensta.librarymanager.service;

import com.ensta.librarymanager.DAO.EmpruntDaoImpl;
import com.ensta.librarymanager.DAO.LivreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntServiceImpl implements EmpruntService{
    private static EmpruntServiceImpl instance;

    private EmpruntServiceImpl(){}

    public static EmpruntServiceImpl getInstance(){
        if (instance == null){
            instance = new EmpruntServiceImpl();
        }
        return instance;
    }


    @Override
    public List<Emprunt> getList() throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntdaoimpl.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntdaoimpl.getListCurrent();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }


    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntdaoimpl.getListCurrentByMembre(idMembre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }


    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntdaoimpl.getListCurrentByLivre(idLivre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }


    @Override
    public Emprunt getById(int id) throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            emprunt = empruntdaoimpl.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunt;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour) throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        try {
            empruntdaoimpl.create(idMembre, idLivre, dateEmprunt, dateRetour);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }


    @Override
    public void returnBook(int id) throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            emprunt = empruntdaoimpl.getById(id);
            LocalDate today=LocalDate.now();
            emprunt.setDateRetour(today);
            empruntdaoimpl.update(emprunt);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }


    @Override
    public int count() throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        int total=0;
        try {
            total = empruntdaoimpl.count();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return total;
    }


    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        boolean est_dispo=false;
        try {
            emprunts = empruntdaoimpl.getListCurrentByLivre(idLivre);
            if(emprunts.size()==0)
                est_dispo=true;
            else
                est_dispo=false;

        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return est_dispo;
    }


    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        EmpruntDaoImpl empruntdaoimpl=EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        boolean emprunt_possible=false;
        try {
            emprunts = empruntdaoimpl.getListCurrentByMembre(membre.getIdM());
            if(emprunts.size() < membre.getAbonnement().getQuota())
                emprunt_possible=true;
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunt_possible;
    }
}
