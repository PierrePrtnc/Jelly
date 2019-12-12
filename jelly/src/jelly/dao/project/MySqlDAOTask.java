package jelly.dao.project;

import jelly.User;

import java.sql.ResultSet;

public class MySqlDAOTask implements TaskDAO {
    @Override
    public boolean insertTask(String desc) {
        return false;
    }

    @Override
    public boolean updateTask(int idTask, String desc) {
        return false;
    }

    @Override
    public boolean deleteTask(int idTask) {
        return false;
    }

    @Override
    public User readTask(int idTask) {
        return null;
    }

    @Override
    public ResultSet readAllTasks() {
        return null;
    }
}
