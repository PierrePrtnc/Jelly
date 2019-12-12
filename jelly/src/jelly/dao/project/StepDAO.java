package jelly.dao.project;

import jelly.User;
import jelly.project.Board;
import jelly.project.Task;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;

public interface StepDAO {

    public boolean insertStep(String name, Collection<Task> tasks, Date initialDate, Date finalDate);
    public boolean updateStep(int idStep, String name, Collection<Task> tasks, Date initialDate, Date finalDate);
    public boolean deleteStep(int idStep);
    public User readStep(int idStep);
    public ResultSet readAllSteps();
}
