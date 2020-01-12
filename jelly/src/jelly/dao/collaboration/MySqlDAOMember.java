package jelly.dao.collaboration;

import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.project.Project;

import java.sql.ResultSet;

public class MySqlDAOMember implements MemberDAO {


    @Override
    public User readMember(int idMember) {
        return null;
    }

    @Override
    public ResultSet readAllMembers() {
        return null;
    }

    @Override
    public Collaborator insertCollaborator(Project p, User user) {
        return null;
    }

    @Override
    public boolean updateCollaborator(int idCollaborator, Project P, User user) {
        return false;
    }

    @Override
    public boolean deleteCollaborator(int idCollaborator) {
        return false;
    }

    @Override
    public Collaborator readCollaborator(int idCollaborator) {
        return null;
    }

    @Override
    public ResultSet readAllCollaborators() {
        return null;
    }
}
