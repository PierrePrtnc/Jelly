package jelly.dao.project;

import jelly.User;
import jelly.project.Task;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;

public class MySqlDAOStep implements StepDAO {
    @Override
    public boolean insertStep(String name, Collection<Task> tasks, Date initialDate, Date finalDate) {
        return false;
    }

    @Override
    public boolean updateStep(int idStep, String name, Collection<Task> tasks, Date initialDate, Date finalDate) {
        return false;
    }

    @Override
    public boolean deleteStep(int idStep) {
        return false;
    }

    @Override
    public User readStep(int idStep) {
        return null;
    }

    @Override
    public ResultSet readAllSteps() {
        return null;
    }
}
