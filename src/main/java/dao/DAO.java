package dao;

import entity.Notebooks;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface DAO {

    boolean createTable (Statement statement) throws SQLException;
    boolean deleteTable (Statement statement) throws SQLException;
    boolean addNotebook (Statement statement, Notebooks notebook) throws SQLException;
    List<Notebooks> getNotebookById(Statement statement, int id) throws SQLException;

}
