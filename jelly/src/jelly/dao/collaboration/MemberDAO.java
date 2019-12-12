package jelly.dao.collaboration;

import jelly.User;
import jelly.project.Project;

import java.sql.ResultSet;

public interface MemberDAO extends CollaboratorDAO {

    public User readMember(int idMember);
    public ResultSet readAllMembers();
}
