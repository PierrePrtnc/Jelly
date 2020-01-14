package jelly.dao.collaboration;

import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.project.Project;

import java.sql.ResultSet;

public interface CollaboratorDAO {

    /**
     *
     * @param p
     * @param user
     * @return
     */
    public Collaborator insertCollaborator(Project p, User user);

    /**
     *
     * @param idCollaborator
     * @param P
     * @param user
     * @return
     */
    public boolean updateCollaborator(int idCollaborator, Project P, User user);

    /**
     *
     * @param idCollaborator
     * @return
     */
    public boolean deleteCollaborator(int idCollaborator);

    /**
     *
     * @param idCollaborator
     * @return
     */
    public Collaborator readCollaborator(int idCollaborator);

    /**
     *
     * @return
     */
    public ResultSet readAllCollaborators();

}
