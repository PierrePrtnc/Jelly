package jelly.dao.collaboration;

import jelly.User;
import jelly.project.Project;

import java.sql.ResultSet;

public interface CreatorDAO extends CollaboratorDAO {

    /**
     *
     * @param idCreator
     * @return
     */
    public User readCreator(int idCreator);

    /**
     *
     * @return
     */
    public ResultSet readAllCreators();

}
