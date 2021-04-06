package com.ensta.librarymanager.service;

import com.ensta.librarymanager.DAO.*;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MembreServiceImpl implements MembreService{
    private static MembreServiceImpl instance;

    private MembreServiceImpl(){}

    public static MembreServiceImpl getInstance(){
        if (instance == null){
            instance = new MembreServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws ServiceException {
        MembreDaoImpl membredaoimpl=MembreDaoImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try {
            membres = membredaoimpl.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membres;
    }


    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        MembreDaoImpl membredaoimpl=MembreDaoImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        List<Membre> membresEmpruntPossible = new ArrayList<>();
        try {
            membres = membredaoimpl.getList();
            for(int i=0;i<membres.size();i++){
                Membre m= membres.get(i);
                if(EmpruntServiceImpl.getInstance().isEmpruntPossible(m))
                    membresEmpruntPossible.add(m);
            }
            System.out.println("GET :" +membresEmpruntPossible);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membresEmpruntPossible;
    }


    @Override
    public Membre getById(int id) throws ServiceException {
        MembreDaoImpl membredaoimpl=MembreDaoImpl.getInstance();
        Membre membre = new Membre();
        try {
            membre = membredaoimpl.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membre;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException {
        MembreDaoImpl membredaoimpl=MembreDaoImpl.getInstance();
        int idMembre=0;
        try{
            if(nom!=null && prenom!=null)
                idMembre = membredaoimpl.create(nom.toUpperCase(), prenom, adresse, email, telephone);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return idMembre;
    }

    @Override
    public void update(Membre membre) throws ServiceException {
        MembreDaoImpl membredaoimpl=MembreDaoImpl.getInstance();
        try{
            if(membre.getNom()!=null && membre.getPrenom()!=null) {
                membre.setNom(membre.getNom().toUpperCase());
                membredaoimpl.update(membre);
            }
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }


    @Override
    public void delete(int id) throws ServiceException {
        MembreDaoImpl membredaoimpl=MembreDaoImpl.getInstance();
        try{
            membredaoimpl.delete(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }

    @Override
    public int count() throws ServiceException {
        MembreDaoImpl membredaoimpl=MembreDaoImpl.getInstance();
        int total=0;
        try {
            total = membredaoimpl.count();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return total;
    }
}
