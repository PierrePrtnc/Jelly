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
     *      inserts a task into the database
     * @param desc          the description of the task to insert
     * @param state         the state of the task to insert
     * @param idStep        the ID of the step to which the task belongs to
     * @return      the task inserted into the database
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
     *      updates a task from the database
     * @param idTask        the ID of the task to update
     * @param desc          the new value of the description of the task to update
     * @param state         the new value of the state of the task to update
     * @return      true if the task was successfully updated
     */
    @Override
    public boolean updateTask(int idTask, String desc, int state, int idStep) {
        String query = "update task set idTask = ?, description = ?, stateTask = ?, idStep = ? where idTask = ?";
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
     *      deletes a task from the database
     * @param idTask    the ID of the task to delete
     * @return  true if the task was successfully deleted
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
     *      reads all the tasks from the database
     * @return  a resultset that describes the tasks of the database
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
     *      reads all tasks associated to a given step
     * @param idStep    the ID of the given step
     * @return  an arraylist of tasks
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
