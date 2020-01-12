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

    /**
     *
     * @param name
     * @param initialDate
     * @param finalDate
     * @param state
     * @param idBoard
     * @return
     */
    @Override
    public Step insertStep(String name, Date initialDate, Date finalDate, int idBoard, int state, int difficulty, String description) {
        String query = "insert into step (nameStep, initialDateStep, finalDateStep, idBoard, stateStep, difficultyStep, descriptionStep) values(?,?,?,?,?,?,?)";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pQuery.setString(1, name);
                pQuery.setDate(2, new java.sql.Date(initialDate.getTime()));
                pQuery.setDate(3, new java.sql.Date(finalDate.getTime()));
                pQuery.setInt(4, idBoard);
                pQuery.setInt(5, state);
                pQuery.setInt(6, difficulty);
                pQuery.setString(7, description);
                pQuery.executeUpdate();
                ResultSet res = pQuery.getGeneratedKeys();
                if (res.next()) {
                    return new Step(res.getInt(1));
                }
                pQuery.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("1");
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    /**
     *
     * @param idStep
     * @param nameStep
     * @param initialDate
     * @param finalDate
     * @param idBoard
     * @param stateStep
     * @param difficultyStep
     * @param description
     * @return
     */
    @Override
    public boolean updateStep(int idStep, String nameStep, Date initialDate, Date finalDate, int idBoard, int stateStep, int difficultyStep, String description) {
        String query = "update step set idStep = ?, nameStep = ?, initialDateStep = ?, finalDateStep = ?, idBoard =?, stateStep = ?, difficultyStep = ?, descriptionStep = ? where idStep = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idStep);
                pQuery.setString(2, nameStep);
                pQuery.setDate(3, new java.sql.Date(initialDate.getTime()));
                pQuery.setDate(4, new java.sql.Date(finalDate.getTime()));
                pQuery.setInt(5, idBoard);
                pQuery.setInt(6, stateStep);
                pQuery.setInt(7, difficultyStep);
                pQuery.setString(8, description);
                pQuery.setInt(9, idStep);
                pQuery.executeUpdate();
                pQuery.close();
                return true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("1");
                e.printStackTrace();
            }
        }
        sql.close();
        return false;
    }

    /**
     *
     * @param idStep
     * @return
     */
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

    /**
     *
     * @param idStep
     * @return
     */
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

    /**
     *
     * @return
     */
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

    public ArrayList<Step> getAllStepsByBoard(int idBoard) {
        if (idBoard != 0) {
            String query = "select * from step where idBoard = ?";

            int idStep;
            String nameStep = "";
            int stateStep;
            int difficultyStep;
            java.sql.Date initialDateStep;
            java.sql.Date finalDateStep;
            List<Step> steps = new ArrayList<Step>();
            if(sql.connect()) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setInt(1, idBoard);
                    ResultSet res = pQuery.executeQuery();
                    while (res.next()) {
                        idStep = res.getInt(1);
                        nameStep = res.getString(2);
                        initialDateStep = res.getDate(3);
                        finalDateStep = res.getDate(4);
                        stateStep = res.getInt(6);
                        difficultyStep = res.getInt(7);
                        steps.add(new Step(idStep, idBoard, nameStep, initialDateStep, finalDateStep, stateStep, difficultyStep));
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
