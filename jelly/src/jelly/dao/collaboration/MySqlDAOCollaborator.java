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
     *
     * @param p
     * @param user
     * @return
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
     *
     * @param idCollaborator
     * @param P
     * @param user
     * @return
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
     *
     * @param idCollaborator
     * @return
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
     *
     * @param idCollaborator
     * @return
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

    //TODO actully implement
    public ArrayList<Collaborator> getAllCollaboratorsByProject(int idProject) {
        if (idProject != 0) {
            String query = "select * from board where idProject = ?";

            int idBoard;
            String nameBoard;
            String descriptionBoard;
            String subjectBoard;
            List<Board> boards = new ArrayList<Board>();
            if(sql.connect()) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setInt(1, idProject);
                    ResultSet res = pQuery.executeQuery();
                    while (res.next()) {
                        idBoard = res.getInt(1);
                        nameBoard = res.getString(2);
                        descriptionBoard = res.getString(4);
                        subjectBoard = res.getString(5);
                        boards.add(new Board(idBoard, idProject, nameBoard, descriptionBoard, subjectBoard));
                    }
                    return (ArrayList) boards;
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
     *
     * @return
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
