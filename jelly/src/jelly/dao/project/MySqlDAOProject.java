package jelly.dao.project;

import jelly.User;
import jelly.dao.MySqlDAOFactory;
import jelly.dao.MySqlDAOUser;
import jelly.dao.UserDAO;
import jelly.database.MySqlClient;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MySqlDAOProject implements ProjectDAO {

    MySqlClient sql = MySqlDAOFactory.getConnection();

    /**
     *  inserts a project into the database
     * @param name              the name of the project to insert
     * @param description       the description of the project to insert
     * @param initialDate       the initial date of the project to insert
     * @param finalDate         the final date of the project to insert
     * @param creator           the creator of the project to insert
     * @return  the project inserted into the database
     */
    @Override
    public Project insertProject(String name, String description, Date initialDate, Date finalDate, User creator) {
        MySqlDAOUser user = new MySqlDAOUser();
        int id = user.getIdByMailUser(creator.getMailUser());
        String query = "insert into project (nameProject, descriptionProject, initialDateProject, finalDateProject, idCreator) values(?,?,?,?,?)";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pQuery.setString(1, name);
                pQuery.setString(2, description);
                pQuery.setDate(3, new java.sql.Date(initialDate.getTime()));
                pQuery.setDate(4, new java.sql.Date(finalDate.getTime()));
                pQuery.setInt(5, id);
                pQuery.executeUpdate();
                ResultSet res = pQuery.getGeneratedKeys();
                if (res.next()) {
                    return new Project(res.getInt(1));
                }
                pQuery.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("1");
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    /**
     *  updates a project from the database
     * @param idProject         the ID of the project to update
     * @param name              the new value for the name of the project
     * @param description       the new value for the description of the project
     * @param initialDate       the new value for the initial date of the project
     * @param finalDate         the new value for the final date of the project
     * @return  true if project was successfully updated
     */
    @Override
    public boolean updateProject(int idProject, String name, String description, Date initialDate, Date finalDate) {
        String query = "update project set nameProject = ?, descriptionProject = ?, initialDateProject = ?, finalDateProject = ? where idProject = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setString(1, name);
                pQuery.setString(2, description);
                pQuery.setDate(3, new java.sql.Date(initialDate.getTime()));
                pQuery.setDate(4, new java.sql.Date(finalDate.getTime()));
                pQuery.setInt(5, idProject);
                pQuery.executeUpdate();
                pQuery.close();
                return true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return false;
    }

    /**
     *  deletes a project from the database
     * @param idProject     the ID of the project to delete
     * @return  true if the project was successfully deleted
     */
    @Override
    public boolean deleteProject(int idProject) {
        String query = "delete from project where idProject = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idProject);
                pQuery.executeUpdate();
                return true;
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        sql.close();
        return false;
    }

    /**
     *  reads a project from the database
     * @param idProject     the ID of the project to display
     * @return  the project read from the database
     */
    @Override
    public Project readProject(int idProject) {
        String query = "select * from project where idProject = ?";
        String nameProject = "";
        String descriptionProject = "";
        Date initialDateProject = new Date();
        Date finalDateProject = new Date();
        int idP = 0;
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idProject);
                ResultSet res = pQuery.executeQuery();
                while (res.next()) {
                    idP = res.getInt(1);
                    nameProject = res.getString(2);
                    descriptionProject = res.getString(3);
                    initialDateProject = res.getDate(4);
                    finalDateProject = res.getDate(5);
                }
                Project p = new Project(nameProject, descriptionProject, initialDateProject, finalDateProject);
                p.setIdProject(idP);
                return p;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    /**
     *  reads all projects from the database
     * @return  a resultset that describes the table project
     */
    @Override
    public ResultSet readAllProjects() {
        String query = "select * from project";
        if(sql.connect()) {
            try {
                Statement st = sql.getDbConnect().createStatement();
                return st.executeQuery(query);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    /**
     *  reads all projects associated to a given user
     * @param mailUser      the mail address of the user to display projects for
     * @return  an arraylist of projects
     */
    @Override
    public ArrayList<Project> getAllProjectsByUser(String mailUser) {
        MySqlDAOUser user = new MySqlDAOUser();
        int id = user.getIdByMailUser(mailUser);
        if (id != 0) {
            String query = "select * from project where idCreator = ?";

            int idProject = 0;
            String nameProject = "";
            String descriptionProject = "";
            java.sql.Date initialDateProject;
            java.sql.Date finalDateProject;
            List<Project> projects = new ArrayList<Project>();
            if(sql.connect()) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setInt(1, id);
                    ResultSet res = pQuery.executeQuery();
                    while (res.next()) {
                        idProject = res.getInt(1);
                        nameProject = res.getString(2);
                        descriptionProject = res.getString(3);
                        initialDateProject = res.getDate(4);
                        finalDateProject = res.getDate(5);
                        projects.add(new Project(idProject, nameProject, descriptionProject, new java.util.Date(initialDateProject.getTime()), new java.util.Date(finalDateProject.getTime())));
                    }
                    return (ArrayList) projects;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            sql.close();
        }
        return null;
    }

}