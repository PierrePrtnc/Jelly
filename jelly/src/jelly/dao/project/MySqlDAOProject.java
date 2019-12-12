package jelly.dao.project;

import jelly.User;
import jelly.project.Board;

import java.sql.ResultSet;
import java.util.Collection;

public class MySqlDAOProject implements ProjectDAO {
    @Override
    public boolean insertProject(String name, String description, Collection<Board> boards) {
        return false;
    }

    @Override
    public boolean updateProject(int idProject, String name, String description, Collection<Board> boards) {
        return false;
    }

    @Override
    public boolean deleteProject(int idProject) {
        return false;
    }

    @Override
    public User readProject(int idProject) {
        return null;
    }

    @Override
    public ResultSet readAllProjects() {
        return null;
    }
}
