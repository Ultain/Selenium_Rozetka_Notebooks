package dao;

import entity.Notebooks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NotebooksDAO implements DAO {

    @Override
    public boolean createTable(Statement statement) throws SQLException {
        String query = "create table NOTEBOOKS (" +
                "NOTEBOOK_ID int(4) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "MODEL_NAME varchar(100) NOT NULL," +
                "SCREEN varchar(100)," +
                "PROCESSOR varchar(100)," +
                "RAM varchar(100)," +
                "STORAGE varchar(100)," +
                "VIDEO_CARD varchar(100)," +
                "WEIGHT varchar(100))";
        return statement.execute(query);
    }

    @Override
    public boolean deleteTable(Statement statement) throws SQLException {
        String query = "drop table NOTEBOOKS";
        return statement.execute(query);
    }

    @Override
    public boolean addNotebook(Statement statement, Notebooks notebook) throws SQLException {

        String query = "INSERT INTO NOTEBOOKS (MODEL_NAME, SCREEN, PROCESSOR, RAM, STORAGE, VIDEO_CARD, WEIGHT) VALUES("
                +"'"+notebook.getModelName()+"', '"
                +notebook.getScreen()+"', '"
                +notebook.getProcessor()+"', '"
                +notebook.getRAM()+"', '"
                +notebook.getStorage()+"', '"
                +notebook.getVideoCard()+"', '"
                +notebook.getWeight()+"')";

        return statement.execute(query);
    }

    @Override
    public List<Notebooks> getNotebookById(Statement statement, int id) throws SQLException {
        List<Notebooks> listOfNotebooks = new ArrayList<>();
        String query = "select * from NOTEBOOKS where NOTEBOOK_ID = " + id;

        ResultSet result1 = null;
        try {
            result1 = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while(result1.next()){

            listOfNotebooks.add(new Notebooks(result1.getInt("NOTEBOOK_ID"),
                    result1.getString("MODEL_NAME"),
                    result1.getString("SCREEN"),
                    result1.getString("PROCESSOR"),
                    result1.getString("RAM"),
                    result1.getString("STORAGE"),
                    result1.getString("VIDEO_CARD"),
                    result1.getString("WEIGHT")));

        }
        return listOfNotebooks;
    }

}
