package jelly.dao.collaboration;

import jelly.User;
import jelly.project.Project;

import java.sql.ResultSet;

public interface CreatorDAO extends CollaboratorDAO {

    public User readCreator(int idCreator);
    public ResultSet readAllCreators();

}
