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

	/**
	 * Returns an instance of one of the subclasses of the class ProjectDAO.
	 * @return ProjectDAO
	 */
	public ProjectDAO getProjectDAO() {
		return new MySqlDAOProject();
	}

	/**
	 * Returns an instance of one of the subclasses of the class BoardDAO.
	 * @return BoardDAO
	 */
	public BoardDAO getBoardDAO() {
		return new MySqlDAOBoard();
	}

	/**
	 * Returns an instance of one of the subclasses of the class StepDAO.
	 * @return StepDAO
	 */
	public StepDAO getStepDAO() {
		return new MySqlDAOStep();
	}

	/**
	 * Returns an instance of one of the subclasses of the class TaskDAO.
	 * @return TaskDAO
	 */
	public TaskDAO getTaskDAO() { return new MySqlDAOTask(); }

	/**
	 * Returns an instance of one of the subclasses of the class CollaboratorDAO.
	 * @return CollaboratorDAO
	 */
	public CollaboratorDAO getCollaboratorDAO () { return new MySqlDAOCollaborator();}

	/**
	 * Returns an instance of one of the subclasses of the class NotificationDAO.
	 * @return NotificationDAO
	 */
	public NotificationDAO getNotificationDAO() { return new MySqlDAONotification();}

	/**
	 * returns the singleton MySqlDAOFactory
	 * @return MySqlDAOFactory
	 */
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