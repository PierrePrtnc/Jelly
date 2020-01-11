package jelly.dao.project;

import jelly.User;
import jelly.project.State;
import jelly.project.Task;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface TaskDAO {
    public Task insertTask(String desc, State state, int idStep);
    public boolean updateTask(int idTask, String desc, State state);
    public boolean deleteTask(int idTask);
    public Task readTask(int idTask);
    public ResultSet readAllTasks();
    public ArrayList<Task> getAllTasksByStep(int idStep);
}
