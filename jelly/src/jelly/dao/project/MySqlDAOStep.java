package jelly.dao.project;

import jelly.User;
import jelly.dao.MySqlDAOFactory;
import jelly.database.MySqlClient;
import jelly.project.Board;
import jelly.project.State;
import jelly.project.Step;
import jelly.project.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MySqlDAOStep implements StepDAO {

    MySqlClient sql = MySqlDAOFactory.getConnection();

    @Override
    public boolean insertStep(int idStep, String name, Date initialDate, Date finalDate, int idBoard) {
        String query = "insert into step (nameStep, initialDateStep, finalDateStep, idBoard) values(?,?,?,?)";
        if(sql.connect()) {
            if(readStep(idStep) == null) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setString(1, name);
                    pQuery.setDate(2, new java.sql.Date(initialDate.getTime()));
                    pQuery.setDate(3, new java.sql.Date(finalDate.getTime()));
                    pQuery.setInt(4, idBoard);
                    pQuery.executeUpdate();
                    pQuery.close();
                    return true;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    System.out.println("1");
                    e.printStackTrace();
                }
            }
        }
        sql.close();
        return false;
    }

    @Override
    public boolean updateStep(int idStep, String name, Date initialDate, Date finalDate) {
        String query = "update step set nameStep = ?, initialDateStep = ?, finalDateStep = ? where idStep = ?";
        if(sql.connect()) {
            if(readStep(idStep) == null) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setString(1, name);
                    pQuery.setDate(2, new java.sql.Date(initialDate.getTime()));
                    pQuery.setDate(3, new java.sql.Date(finalDate.getTime()));
                    pQuery.setInt(4, idStep);
                    pQuery.executeUpdate();
                    pQuery.close();
                    return true;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    System.out.println("1");
                    e.printStackTrace();
                }
            }
        }
        sql.close();
        return false;
    }

    @Override
    public boolean deleteStep(int idStep) {
        String query = "delete from step where idStep = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idStep);
                pQuery.executeUpdate();
                return true;
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        sql.close();
        return false;
    }

    @Override
    public Step readStep(int idStep) {
        String query = "select * from step where idStep = ?";
        String nameStep = "";
        Date initialDateProject = new Date();
        Date finalDateProject = new Date();
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idStep);
                ResultSet res = pQuery.executeQuery();
                int stateValue = 0;
                while (res.next()) {
                    nameStep = res.getString(2);
                    initialDateProject = res.getDate(3);
                    finalDateProject = res.getDate(4);
                }
                return new Step(nameStep, initialDateProject, finalDateProject);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    @Override
    public ResultSet readAllSteps() {
        String query = "select * from step";
        if(sql.connect()) {
            try {
                Statement st = sql.getDbConnect().createStatement();
                return st.executeQuery(query);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    public ArrayList<Step> getAllStepsByBoard(int boardID) {
        if (boardID != 0) {
            String query = "select * from step where idBoard = ?";

            String nameStep = "";
            int stateStep;
            int difficultyStep;
            java.sql.Date initialDateStep;
            java.sql.Date finalDateStep;
            List<Step> steps = new ArrayList<Step>();
            if(sql.connect()) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setInt(1, boardID);
                    ResultSet res = pQuery.executeQuery();
                    while (res.next()) {
                        nameStep = res.getString(2);
                        initialDateStep = res.getDate(3);
                        finalDateStep = res.getDate(4);
                        stateStep = res.getInt(6);
                        difficultyStep = res.getInt(7);
                        steps.add(new Step(nameStep, initialDateStep, finalDateStep, stateStep, difficultyStep));
                    }
                    return (ArrayList) steps;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            sql.close();
        }
        return null;
    }
}
