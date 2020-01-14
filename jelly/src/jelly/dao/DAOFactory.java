package jelly.dao;

import jelly.dao.collaboration.CollaboratorDAO;
import jelly.dao.notification.NotificationDAO;
import jelly.dao.project.BoardDAO;
import jelly.dao.project.ProjectDAO;
import jelly.dao.project.StepDAO;
import jelly.dao.project.TaskDAO;

/**
 * Abstract class DAOFactory
 * @author Arthur Leblanc, Weslie Rabeson
 *
 */
public abstract class DAOFactory {
	
	/**
	 * Returns an instance of the class UserDAO.
	 * @return UserDAO
	 */
	public abstract UserDAO getUserDAO();

	/**
	 * Returns an instance of the class ProjectDAO.
	 * @return ProjectDAO
	 */
	public abstract ProjectDAO getProjectDAO();

	/**
	 * Returns an instance of the class BoardDAO.
	 * @return BoardDAO
	 */
	public abstract BoardDAO getBoardDAO();

	/**
	 * Returns an instance of the class StepDAO.
	 * @return StepDAO
	 */
	public abstract StepDAO getStepDAO();

	/**
	 * Returns an instance of the class TaskDAO.
	 * @return TaskDAO
	 */
	public abstract TaskDAO getTaskDAO();

	/**
	 * Returns an instance of the class NotificationDAO.
	 * @return NotificationDAO
	 */
	public abstract NotificationDAO getNotificationDAO();

	/**
	 * Returns an instance of the class CollaboratorDAO.
	 * @return CollaboratorDAO
	 */
	public abstract  CollaboratorDAO getCollaboratorDAO();
	
	/**
	 * Returns an instance of one of the subclasses of DAOFactory, depending on the database chosen as parameter.
	 * @return DAOFactory
	 */
	public static DAOFactory getDAOFactory() {
		return MySqlDAOFactory.getInstance();
	}

}
