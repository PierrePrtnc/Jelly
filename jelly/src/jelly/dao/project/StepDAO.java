package jelly.dao.project;

import jelly.User;
import jelly.project.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public interface StepDAO {

    public Step insertStep(String name, Date initialDate, Date finalDate, int Board, int step, int difficulty, String description);
    public boolean updateStep(int idStep, String name, Date initialDate, Date finalDate, State step);
    public boolean deleteStep(int idStep);
    public Step readStep(int idStep);
    public ResultSet readAllSteps();
    public ArrayList<Step> getAllStepsByBoard(int boardID);
}
