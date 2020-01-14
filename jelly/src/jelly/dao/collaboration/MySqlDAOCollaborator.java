package jelly.dao.collaboration;

import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.dao.MySqlDAOFactory;
import jelly.dao.MySqlDAOUser;
import jelly.dao.project.MySqlDAOProject;
import jelly.database.MySqlClient;
import jelly.project.Board;
import jelly.project.Project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlDAOCollaborator implements CollaboratorDAO {

    MySqlClient sql = MySqlDAOFactory.getConnection();

    /**
     * inserts a Collaborator into the database
     * @param p         the project associated to the collaborator
     * @param user      the user associated to the collaborator
     * @return      the Collaborator inserted in the database
     */
    @Override
    public Collaborator insertCollaborator(Project p, User user) {
        MySqlDAOUser userDB = new MySqlDAOUser();
        int idUser = userDB.getIdUser(user.getMailUser());
        String query = "insert into collaborator (idProject, idUser) values (?,?)";
        if (sql.connect()) {
            if(sql.connect()) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pQuery.setInt(2, p.getIdProject());
                    pQuery.setInt(3, idUser);
                    pQuery.executeUpdate();
                    ResultSet res = pQuery.getGeneratedKeys();
                    if (res.next()) {
                        return new Collaborator(res.getInt(1), p, user);
                    }
                    pQuery.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            sql.close();
        }
        return null;
    }

    /**
     *  updates a collaborator from the database
     * @param idCollaborator        the ID of the collaborator to update
     * @param P                     the project to modify for the given collaborator
     * @param user                  the user to modify for the given collaborator
     * @return  true if the collaborator was successfully updated
     */
    @Override
    public boolean updateCollaborator(int idCollaborator, Project P, User user) {
        MySqlDAOUser userDB = new MySqlDAOUser();
        int idUser = userDB.getIdUser(user.getMailUser());
        String query = "update collaborator set idProject = ?, idUser = ? where idCollaborator = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, P.getIdProject());
                pQuery.setInt(2, idUser);
                pQuery.setInt(5, idCollaborator);
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
     *  deletes a collaborator from the database
     * @param idCollaborator    ID of the collaborator to delete
     * @return   true if the collaborator was successfully deleted
     */
    @Override
    public boolean deleteCollaborator(int idCollaborator) {
        String query = "delete from collaborator where idCollaborator = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idCollaborator);
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
     *  reads a collaborator from the database
     * @param idCollaborator    ID of the collaborator to display
     * @return  the collaborator to display
     */
    @Override
    public Collaborator readCollaborator(int idCollaborator) {
        String query = "select * from collaborator where idCollaborator = ?";
        int idProject = 0;
        int idUser = 0;
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idCollaborator);
                ResultSet res = pQuery.executeQuery();
                int stateValue = 0;
                while (res.next()) {
                    idProject = res.getInt(2);
                    idUser = res.getInt(3);
                }
                MySqlDAOProject project = new MySqlDAOProject();
                MySqlDAOUser user = new MySqlDAOUser();
                Project p = project.readProject(idProject);
                User u = user.readUser(user.getEmailUser(idUser));
                return new Collaborator(p, u);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    /**
     *  reads all the collaborator associated to a given project
     * @param idProject     ID of the project to which the collaborator is associated to
     * @return  an arraylist of collaborators
     */
    public ArrayList<Collaborator> getAllCollaboratorsByProject(int idProject) {
        MySqlDAOProject project = new MySqlDAOProject();
        MySqlDAOUser user = new MySqlDAOUser();
        if (idProject != 0) {
            String query = "select * from collaborator where idProject = ?";

            int idCollaborator;
            int idUser;
            List<Collaborator> collaborators = new ArrayList<Collaborator>();
            if(sql.connect()) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setInt(1, idProject);
                    ResultSet res = pQuery.executeQuery();
                    while (res.next()) {
                        idCollaborator = res.getInt(1);
                        idUser = res.getInt(3);
                        collaborators.add(new Collaborator(idCollaborator, project.readProject(idProject), user.readUser(user.getEmailUser(idUser))));
                    }
                    return (ArrayList) collaborators;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            sql.close();
        }
        return null;
    }

    /**
     *  reads all the collaborators from the database
     * @return  a resultset describing the collaborator table
     */
    @Override
    public ResultSet readAllCollaborators() {
        String query = "select * from collaborator";
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

}
