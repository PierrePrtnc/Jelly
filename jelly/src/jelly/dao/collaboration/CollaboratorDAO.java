package jelly.dao.collaboration;

import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.project.Project;

import java.sql.ResultSet;

public interface CollaboratorDAO {

    public Collaborator insertCollaborator(Project p, User user);
    public boolean updateCollaborator(int idCollaborator, Project P, User user);
    public boolean deleteCollaborator(int idCollaborator);
    public Collaborator readCollaborator(int idCollaborator);
    public ResultSet readAllCollaborators();

}
