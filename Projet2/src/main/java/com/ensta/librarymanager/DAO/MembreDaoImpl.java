package com.ensta.librarymanager.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.modele.abonnement;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao {
    private static MembreDaoImpl instance;

    private MembreDaoImpl(){}

    public static MembreDaoImpl getInstance(){
        if (instance == null){
            instance = new MembreDaoImpl();
        }
        return instance;
    }

    private static final String SELECT_ALL_QUERY="Select id, nom, prenom, adresse, email, telephone, abonnement FROM Membre;";
    private static final String SELECT_ONE_QUERY="Select id, nom, prenom, adresse, email, telephone, abonnement FROM Membre WHERE id=?;";
    private static final String CREATE_QUERY="INSERT INTO Membre(nom, prenom, adresse, email, telephone,abonnement) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY="UPDATE Membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?,abonnement = ? WHERE id = ?;";
    private static final String DELETE_QUERY="DELETE FROM Membre WHERE id = ?;";
    private static final String SELECT_QUERY="SELECT COUNT(*) AS count FROM Membre;";


    @Override
    public List<Membre> getList() throws DaoException {
        List<Membre> membres=new ArrayList<>();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            res = preparedStatement.executeQuery();
            while(res.next()) {
                Membre m = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abonnement.valueOf(res.getString("abonnement")));
                membres.add(m);
            }
            System.out.println("GET: " + membres);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des livres", e);
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
        return membres;
    }

    @Override
    public Membre getById(int id) throws DaoException {
        Membre membre=new Membre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1,id);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                membre.setIdM(res.getInt("id"));
                membre.setNom(res.getString("nom"));
                membre.setPrenom(res.getString("prenom"));
                membre.setTelephone(res.getString("telephone"));
                membre.setAdresse(res.getString("adresse"));
                membre.setEmail(res.getString("email"));
                membre.setA(abonnement.valueOf(res.getString("abonnement")));
            }
            System.out.println("GET: " + membre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du livre: id=" + id, e);
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
        return membre;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Membre m=new Membre(nom,prenom,adresse,email,telephone);
        int id =-1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, m.getNom());
            preparedStatement.setString(2, m.getPrenom());
            preparedStatement.setString(3,m.getAdresse());
            preparedStatement.setString(4,m.getEmail());
            preparedStatement.setString(5,m.getTel());
            preparedStatement.setString(6,m.getAbonnement().toString());
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if(res.next()){
                id = res.getInt(1);
            }

            System.out.println("creation du membre réussie");
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du membre" , e);
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
        return id;
    }

    @Override
    public void update(Membre membre) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4,membre.getAdresse());
            preparedStatement.setString(5,membre.getTel());
            preparedStatement.setString(6,membre.getAbonnement().toString());
            preparedStatement.setInt(7,membre.getIdM());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE: " + membre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du film: " + membre, e);
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
    public void delete(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("suppression réussie");
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la suppression du membre: " + id, e);
        }  finally {
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
            throw new DaoException("Problème lors de la récupération du nombre de membres", e);
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

