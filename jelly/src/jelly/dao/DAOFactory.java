package jelly.dao;

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

	public abstract ProjectDAO getProjectDAO();

	public abstract BoardDAO getBoardDAO();

	public abstract StepDAO getStepDAO();

	public abstract TaskDAO getTaskDAO();
	
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
