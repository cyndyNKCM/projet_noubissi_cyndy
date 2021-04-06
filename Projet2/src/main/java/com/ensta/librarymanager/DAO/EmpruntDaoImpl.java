package com.ensta.librarymanager.DAO;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.persistence.ConnectionManager;
import org.h2.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

public class EmpruntDaoImpl implements EmpruntDao {
    private static EmpruntDaoImpl instance;

    private EmpruntDaoImpl() {
    }

    public static EmpruntDaoImpl getInstance() {
        if (instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }

    private static final String SELECT_ALL_QUERY1 = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
    private static final String SELECT_ALL_QUERY2 = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
    private static final String SELECT_ALL_QUERY3 = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
    private static final String SELECT_ALL_QUERY4 = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
    private static final String SELECT_ONE_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
    private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?;";
    private static final String SELECT_QUERY = "SELECT COUNT(*) AS count FROM emprunt;";


    @Override
    public List<Emprunt> getList() throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY1);
            res = preparedStatement.executeQuery();
            while (res.next()) {

                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")));
                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
                LocalDate d1 = res.getDate("dateEmprunt").toLocalDate();
                Date d = res.getDate("dateRetour");
                LocalDate d2 = null;
                if (d != null)
                    d2 = res.getDate("dateRetour").toLocalDate();
                Emprunt emp = new Emprunt(res.getInt("id"), m, l, d1, d2);
                emprunts.add(emp);
            }
            System.out.println("GET: " + emprunts);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY2);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                LocalDate d1 = res.getDate("dateEmprunt").toLocalDate();
                Date d = res.getDate("dateRetour");
                LocalDate d2 = null;
                if (d != null)
                    d2 = res.getDate("dateRetour").toLocalDate();
                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")));
                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
                Emprunt emp = new Emprunt(res.getInt("id"), m, l, d1, d2);
                emprunts.add(emp);
            }
            System.out.println("GET: " + emprunts);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts non rendus", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY3);
            preparedStatement.setInt(1, idMembre);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                LocalDate d1 = res.getDate("dateEmprunt").toLocalDate();
                Date d = res.getDate("dateRetour");
                LocalDate d2 = null;
                if (d != null)
                    d2 = res.getDate("dateRetour").toLocalDate();
                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")));
                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
                Emprunt emp = new Emprunt(res.getInt("id"), m, l, d1, d2);
                emprunts.add(emp);
            }
            System.out.println("GET: " + emprunts);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts non rendus par membre", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;
    }


    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY4);
            preparedStatement.setInt(1, idLivre);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                LocalDate d1 = res.getDate("dateEmprunt").toLocalDate();
                Date d = res.getDate("dateRetour");
                LocalDate d2 = null;
                if (d != null)
                    d2 = res.getDate("dateRetour").toLocalDate();
                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")));
                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
                Emprunt emp = new Emprunt(res.getInt("id"), m, l, d1, d2);
                emprunts.add(emp);
            }
            System.out.println("GET: " + emprunts);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts non rendus par livre", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;
    }


    @Override
    public Emprunt getById(int id) throws DaoException {
        Emprunt emprunt = new Emprunt();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if (res.next()) {

                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")));
                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
                LocalDate d1 = res.getDate("dateEmprunt").toLocalDate();
                LocalDate d2 = res.getDate("dateEmprunt").toLocalDate();
                emprunt=new Emprunt(res.getInt("id"), m, l, d1, d2);


            }
            System.out.println("GET: " + emprunt);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de l'emprunt: id=" + id, e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunt;
    }


    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour) throws DaoException {
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setInt(1, idMembre);
            preparedStatement.setInt(2, idLivre);
            preparedStatement.setDate(3,java.sql.Date.valueOf(dateEmprunt));
            preparedStatement.setDate(3,java.sql.Date.valueOf(dateRetour));
            preparedStatement.executeUpdate();


            System.out.println("creation de l'emprunt réussie");
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création de l'emprunt", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void update(Emprunt emprunt) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, emprunt.getM().getIdM());
            preparedStatement.setInt(2, emprunt.getL().getIdL());
            preparedStatement.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.setInt(5,emprunt.getIdE());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE: " + emprunt);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour de l'emprunt: " + emprunt, e);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int count() throws DaoException {
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int nb=0;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                nb=res.getInt(1);
            }

            System.out.println("GET: " + nb);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du nombre d'emprunts", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return nb;
    }
}
