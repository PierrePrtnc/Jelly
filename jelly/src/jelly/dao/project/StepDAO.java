package jelly.dao.project;

import jelly.User;
import jelly.project.Board;
import jelly.project.Step;
import jelly.project.Task;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;

public interface StepDAO {

    public boolean insertStep(int idStep, String name, Date initialDate, Date finalDate, int Board);
    public boolean updateStep(int idStep, String name, Date initialDate, Date finalDate);
    public boolean deleteStep(int idStep);
    public Step readStep(int idStep);
    public ResultSet readAllSteps();
}
