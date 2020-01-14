package jelly.dao.project;

import jelly.User;
import jelly.dao.MySqlDAOFactory;
import jelly.database.MySqlClient;
import jelly.project.State;
import jelly.project.Step;
import jelly.project.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlDAOTask implements TaskDAO {

    MySqlClient sql = MySqlDAOFactory.getConnection();

    /**
     *
     * @param desc
     * @param state
     * @param idStep
     * @return
     */
    @Override
    public Task insertTask(String desc, int state, int idStep) throws SQLException {
        String query = "insert into task (description, stateTask, idStep) values(?,?,?)";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pQuery.setString(1, desc);
                pQuery.setInt(2, state);
                pQuery.setInt(3, idStep);
                pQuery.executeUpdate();
                ResultSet res = pQuery.getGeneratedKeys();
                if (res.next()) {
                    return new Task(res.getInt(1));
                }
                pQuery.close();
            }catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    /**
     *
     * @param idTask
     * @param desc
     * @param state
     * @return
     */
    @Override
    public boolean updateTask(int idTask, String desc, int state, int idStep) {
        String query = "update task set idTask = ?, description = ?, stateTask = ?, int idStep = ? where idTask = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idTask);
                pQuery.setString(2, desc);
                pQuery.setInt(3, state);
                pQuery.setInt(4, idStep);
                pQuery.setInt(5, idTask);
                pQuery.executeUpdate();
                pQuery.close();
                return true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return false;
    }

    /**
     *
     * @param idTask
     * @return
     */
    @Override
    public boolean deleteTask(int idTask) {
        String query = "delete from task where idTask = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idTask);
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
     * @return
     */
    @Override
    public ResultSet readAllTasks() {
        String query = "select * from task";
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

    /**
     *
     * @param idStep
     * @return
     */
    @Override
    public ArrayList<Task> getAllTasksByStep(int idStep) {
        String query = "select * from task where idStep = ?";

        int idTask;
        String descriptionTask = "";
        int stateTask;
        List<Task> tasks = new ArrayList<Task>();
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idStep);
                ResultSet res = pQuery.executeQuery();
                while (res.next()) {
                    idTask = res.getInt(1);
                    descriptionTask = res.getString(2);
                    stateTask = res.getInt(3);
                    tasks.add(new Task(idTask, descriptionTask, stateTask, idStep));
                }
                return (ArrayList) tasks;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }
}
