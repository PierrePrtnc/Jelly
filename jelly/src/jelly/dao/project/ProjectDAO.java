package jelly.dao.project;

import jelly.User;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;

public interface ProjectDAO {

    public boolean insertProject(int idProject, String name, String description, Date initialDate, Date finalDate, int idCreator);
    public boolean updateProject(int idProject, String name, String description, Date initialDate, Date finalDate);
    public boolean deleteProject(int idProject);
    public Project readProject(int idProject);
    public ResultSet readAllProjects();
}
