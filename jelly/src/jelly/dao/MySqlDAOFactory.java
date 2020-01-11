package jelly.dao;

import jelly.dao.notification.MySqlDAONotification;
import jelly.dao.notification.NotificationDAO;
import jelly.dao.project.MySqlDAOProject;
import jelly.dao.project.ProjectDAO;
import jelly.database.MySqlClient;

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
		return new MySqlClient("remotemysql.com:3306", "EvR1zSObCT", "eMA8QUCWIG", "EvR1zSObCT");
	}

	

}
