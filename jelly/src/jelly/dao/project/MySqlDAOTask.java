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
    public Task insertTask(String desc, State state, int idStep) {
        String query = "insert into task (description, stateTask, idStep) values(?,?,?)";
        if(sql.connect()) {
            try {
                int stateId = 0;
                switch (state) {
                    case ToDo:
                        stateId = 0;
                    case InProgress:
                        stateId = 1;
                    case Finished:
                        stateId = 2;
                    case ReDo:
                        stateId = 3;
                }
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pQuery.setString(1, desc);
                pQuery.setInt(2, stateId);
                pQuery.setInt(3, idStep);
                pQuery.executeUpdate();
                ResultSet res = pQuery.getGeneratedKeys();
                if (res.next()) {
                    return new Task(res.getInt(1));
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
     * @param idTask
     * @param desc
     * @param state
     * @return
     */
    @Override
    public boolean updateTask(int idTask, String desc, State state) {
        String query = "update task set description = ?, stateTask = ? where idTask = ?";
        if(sql.connect()) {
            if(readTask(idTask) == null) {
                try {
                    int stateId = 0;
                    switch (state) {
                        case ToDo:
                            stateId = 0;
                        case InProgress:
                            stateId = 1;
                        case Finished:
                            stateId = 2;
                        case ReDo:
                            stateId = 3;
                    }
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setString(1, desc);
                    pQuery.setInt(2, stateId);
                    pQuery.setInt(4, idTask);
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
     * @param idTask
     * @return
     */
    @Override
    public Task readTask(int idTask) {
        String query = "select * from task where idTask = ?";
        String description = "";
        int stateTask;
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idTask);
                ResultSet res = pQuery.executeQuery();
                int stateValue = 0;
                State state;
                description = res.getString(2);
                stateTask = res.getInt(3);
                Task task = new Task(description);
                switch(stateTask) {
                    case 0:
                        state = State.ToDo;
                        task.setTaskState(state);
                    case 1:
                        state = State.InProgress;
                        task.setTaskState(state);
                    case 2:
                        state = State.Finished;
                        task.setTaskState(state);
                    case 3:
                        state = State.ReDo;
                        task.setTaskState(state);
                }
                return task;
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
        String descriptionTask = "";
        int stateTask;
        List<Task> tasks = new ArrayList<Task>();
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idStep);
                ResultSet res = pQuery.executeQuery();
                State state;
                while (res.next()) {
                    descriptionTask = res.getString(2);
                    stateTask = res.getInt(3);
                    Task task = new Task(descriptionTask);
                    switch(stateTask) {
                        case 0:
                            state = State.ToDo;
                            task.setTaskState(state);
                        case 1:
                            state = State.InProgress;
                            task.setTaskState(state);
                        case 2:
                            state = State.Finished;
                            task.setTaskState(state);
                        case 3:
                            state = State.ReDo;
                            task.setTaskState(state);
                    }
                    tasks.add(task);
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
