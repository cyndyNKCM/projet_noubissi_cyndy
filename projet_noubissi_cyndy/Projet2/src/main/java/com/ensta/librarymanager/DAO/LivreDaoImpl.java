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
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDaoImpl implements LivreDao {
    private static LivreDaoImpl instance;

    private LivreDaoImpl(){}

    public static LivreDaoImpl getInstance(){
        if (instance == null){
            instance = new LivreDaoImpl();
        }
        return instance;
    }

    private static final String SELECT_ALL_QUERY="Select id, titre, auteur, isbn FROM Livre;";
    private static final String SELECT_ONE_QUERY="Select id, titre, auteur, isbn FROM Livre WHERE id=?;";
    private static final String CREATE_QUERY="INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
    private static final String UPDATE_QUERY="UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
    private static final String DELETE_QUERY="DELETE FROM livre WHERE id = ?;";
    private static final String SELECT_QUERY="SELECT COUNT(*) AS count FROM livre;";

    @Override
    public List<Livre> getList() throws DaoException {
        List<Livre> livres=new ArrayList<>();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            res = preparedStatement.executeQuery();
            while(res.next()) {
                Livre l = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
                livres.add(l);
            }
            System.out.println("GET: " + livres);
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
        return livres;
    }

    @Override
    public Livre getById(int id) throws DaoException {
        Livre livre=new Livre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1,id);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                 livre.setId(res.getInt("id"));
                 livre.setAuteur(res.getString("titre"));
                 livre.setTitre(res.getString("auteur"));
                 livre.setIsbn(res.getString("isbn"));
            }
            System.out.println("GET: " + livre);
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
        return livre;
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws DaoException {
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id =-1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3,isbn);
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if(res.next()){
                id = res.getInt(1);
            }

            System.out.println("creation du film réussie");
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du livre" , e);
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
    public void update(Livre livre) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4,livre.getIdL());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE: " + livre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du film: " + livre, e);
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
            throw new DaoException("Problème lors de la suppression du film: " + id, e);
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
            throw new DaoException("Problème lors de la récupération du nombre de films", e);
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
