package jelly.dao.collaboration;

import jelly.User;
import jelly.project.Project;

import java.sql.ResultSet;

public interface MemberDAO extends CollaboratorDAO {

    /**
     *
     * @param idMember
     * @return
     */
    public User readMember(int idMember);

    /**
     *
     * @return
     */
    public ResultSet readAllMembers();
}
