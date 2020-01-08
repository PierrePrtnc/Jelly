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

    @Override
    public boolean insertProject(int idProject, String name, String description, Date initialDate, Date finalDate, int idCreator) {
        String query = "insert into project (nameProject, descriptionProject, initialDateProject, finalDateProject, idCreator) values(?,?,?,?,?)";
        if(sql.connect()) {
            if(readProject(idProject) == null) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setString(1, name);
                    pQuery.setString(2, description);
                    pQuery.setDate(3, new java.sql.Date(initialDate.getTime()));
                    pQuery.setDate(4, new java.sql.Date(finalDate.getTime()));
                    pQuery.setInt(5, idCreator);
                    pQuery.executeUpdate();
                    pQuery.close();
                    return true;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    System.out.println("1");
                    e.printStackTrace();
                }
            }
        }
        sql.close();
        return false;
    }

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

    @Override
    public Project readProject(int idProject) {
        String query = "select * from project where idProject = ?";
        String nameProject = "";
        String descriptionProject = "";
        Date initialDateProject = new Date();
        Date finalDateProject = new Date();
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idProject);
                ResultSet res = pQuery.executeQuery();
                while (res.next()) {
                    nameProject = res.getString(2);
                    descriptionProject = res.getString(3);
                    initialDateProject = res.getDate(4);
                    finalDateProject = res.getDate(5);
                }
                Project p = new Project(nameProject, descriptionProject, initialDateProject, finalDateProject);
                p.setIdProject(res.getInt(1));
                return p;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

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

    public ArrayList<Project> getAllProjectsByUser(String mailUser) {
        MySqlDAOUser user = new MySqlDAOUser();
        int id = user.getIdByMailUser(mailUser);
        if (id != 0) {
            String query = "select * from project where idCreator = ?";

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
                        nameProject = res.getString(2);
                        descriptionProject = res.getString(3);
                        initialDateProject = res.getDate(4);
                        finalDateProject = res.getDate(5);
                        projects.add(new Project(nameProject, descriptionProject, new java.util.Date(initialDateProject.getTime()), new java.util.Date(finalDateProject.getTime())));
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
/*
    public static void main(String[] args) {
        MySqlDAOProject db = new MySqlDAOProject();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateInit = new Date();
        df.format(dateInit);
        Date dateFinal = new Date();
        try {
            dateInit = df.parse("2020-01-25");
            dateFinal = df.parse("2020-04-26");
            db.updateProject(2, "projet1", "premierProjet", dateInit, dateFinal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(db.readProject(2).getInitialDate());

    }*/
}
