package com.ism.core.Repository;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.ism.core.dataSource.DataSourceImpl;
import com.ism.data.entities.Identifiable;

public abstract class RepositoryBDImpl<T> extends DataSourceImpl implements Repository<T> {

    protected abstract void setInsertParameters(PreparedStatement ps, T object) throws SQLException;
    protected abstract String generateInsertQuery();
    protected abstract String getTableName();
    protected abstract T converToObject(ResultSet rs) throws SQLException;
    protected abstract void setIdFromResultSet(T object, ResultSet rs) throws SQLException;
    protected String tableName;

    @Override
    public boolean insert(T object) {
        String query = generateInsertQuery();
        try (Connection connection = connexion(); 
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setInsertParameters(ps, object);
            int nbre = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                setIdFromResultSet(object,rs);
            }
            return nbre > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion : " + e.getMessage());
            return false;
        }
    }

    protected String generateSelectQuery() {
        return "SELECT * FROM " + getTableName();
    }

    @Override
        public List<T> selectAll() {
            List<T> list = new ArrayList<>();
            String query = generateSelectQuery();
            try (Connection connection = connexion();
                 PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                // Parcours des résultats
                while (rs.next()) {
                    T entity = converToObject(rs);
                    list.add(entity);
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des données : " + e.getMessage());
                e.printStackTrace();
            }
            return list;
        } 
     
    @Override
    public T selectById(int id) {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = connexion();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return converToObject(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération par ID : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
}
   @Override
    public void remove(T object) {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = connexion();
            PreparedStatement ps = connection.prepareStatement(query)) {
            
            // Supposons que votre objet T a une méthode getId() pour obtenir son identifiant.
            int id = ((Identifiable) object).getId(); // Assurez-vous que T implémente Identifiable ou cast directement si approprié.
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
            e.printStackTrace();
        }
    }


}
