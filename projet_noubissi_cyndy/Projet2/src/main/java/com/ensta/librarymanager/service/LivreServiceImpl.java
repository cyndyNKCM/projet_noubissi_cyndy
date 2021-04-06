package com.ensta.librarymanager.service;

import com.ensta.librarymanager.DAO.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;

import java.util.*;

public class LivreServiceImpl implements LivreService{
    private static LivreServiceImpl instance;

    private LivreServiceImpl(){}

    public static LivreServiceImpl getInstance(){
        if (instance == null){
            instance = new LivreServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Livre> getList() throws ServiceException {
        LivreDaoImpl livredaoimpl=LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livredaoimpl.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return livres;
    }

    @Override
    public List<Livre> getListDispo() throws ServiceException {
        LivreDaoImpl livredaoimpl=LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livredaoimpl.getList();
            for(int i=1;i<=livres.size();i++){
                Livre l= livredaoimpl.getById(i);
                if(!EmpruntServiceImpl.getInstance().isLivreDispo(l.getIdL()))
                    livres.remove(l);
            }
            System.out.println("GET: " + livres);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return livres;
    }

    @Override
    public Livre getById(int id) throws ServiceException {
        LivreDaoImpl livredaoimpl=LivreDaoImpl.getInstance();
        Livre livre = new Livre();
        try {
            livre = livredaoimpl.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return livre;
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws ServiceException {
        LivreDaoImpl livredaoimpl=LivreDaoImpl.getInstance();
        int idLivre = 0;
        try {
            if(titre!=null)
                idLivre = livredaoimpl.create(titre,auteur,isbn);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return idLivre;
    }

    @Override
    public void update(Livre livre) throws ServiceException {
        LivreDaoImpl livredaoimpl=LivreDaoImpl.getInstance();
        try {
            if(livre.getTitre()!=null)
                livredaoimpl.update(livre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        LivreDaoImpl livredaoimpl=LivreDaoImpl.getInstance();
        try {
            livredaoimpl.delete(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }

    @Override
    public int count() throws ServiceException {
        LivreDaoImpl livredaoimpl=LivreDaoImpl.getInstance();
        int total=0;
        try {
            total = livredaoimpl.count();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return total;
    }
}
