package jelly.dao.project;

import jelly.User;
import jelly.project.Task;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;

public interface TaskDAO {
    public boolean insertTask(String desc);
    public boolean updateTask(int idTask, String desc);
    public boolean deleteTask(int idTask);
    public User readTask(int idTask);
    public ResultSet readAllTasks();
}
