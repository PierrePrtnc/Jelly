package jelly.dao;

import jelly.collaboration.Collaborator;
import jelly.dao.collaboration.CollaboratorDAO;
import jelly.dao.collaboration.MySqlDAOCollaborator;
import jelly.dao.notification.MySqlDAONotification;
import jelly.dao.notification.NotificationDAO;
import jelly.dao.project.*;
import jelly.database.MySqlClient;
import jelly.project.Board;

/**
 * Class MySqlDAOFactory
 * @author Arthur Leblanc, Weslie Rabeson
 *
 */
public class MySqlDAOFactory extends DAOFactory {
	
	private static MySqlDAOFactory fact;
	/**
	 * Returns an instance of one of the subclasses of the class UserDAO.
	 * @return UserDAO
	 */
	public UserDAO getUserDAO() {
		return new MySqlDAOUser();
	}

	public ProjectDAO getProjectDAO() {
		return new MySqlDAOProject();
	}

	public BoardDAO getBoardDAO() {
		return new MySqlDAOBoard();
	}

	public StepDAO getStepDAO() {
		return new MySqlDAOStep();
	}

	public TaskDAO getTaskDAO() { return new MySqlDAOTask(); }

	public CollaboratorDAO getCollaboratorDAO () { return new MySqlDAOCollaborator();}

	@Override
	public NotificationDAO getNotificationDAO() {
		// TODO Auto-generated method stub
		return new MySqlDAONotification();
	}
	
	public static MySqlDAOFactory getInstance() {
		if (fact == null) {
			fact = new MySqlDAOFactory();
		}
		return fact;
	}
	/**
	 * Returns an instance of the class MySqlClient, taking care of the connection to the database.
	 * @return MySqlClient
	 */
	public static MySqlClient getConnection() {
		return new MySqlClient("mysql-aleblanc.alwaysdata.net:3306", "aleblanc_jelly", "adminJelly", "aleblanc_jelly");
	}

	

}