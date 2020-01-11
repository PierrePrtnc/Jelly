package jelly.dao;

import jelly.dao.notification.NotificationDAO;
import jelly.dao.project.ProjectDAO;

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

	public abstract ProjectDAO getProjectDAO();
	
	public abstract NotificationDAO getNotificationDAO();
	
	/**
	 * Returns an instance of one of the subclasses of DAOFactory, depending on the database chosen as parameter.
	 * @param dbName
	 * 				The name of the database (mySQL, Oracle ...)
	 * @return DAOFactory
	 */
	public static DAOFactory getDAOFactory() {
		return MySqlDAOFactory.getInstance();
	}
	
}
