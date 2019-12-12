package jelly.dao.project;

import jelly.User;
import jelly.project.Board;
import jelly.project.Step;

import java.sql.ResultSet;
import java.util.Collection;

public interface ProjectDAO {

    public boolean insertProject(String name, String description, Collection<Board> boards);
    public boolean updateProject(int idProject, String name, String description, Collection<Board> boards);
    public boolean deleteProject(int idProject);
    public User readProject(int idProject);
    public ResultSet readAllProjects();
}
