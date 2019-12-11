package jelly.dao;

import jelly.database.MySqlClient;

/**
 * Class MySqlDAOFactory
 * @author Arthur Leblanc, Weslie Rabeson
 *
 */
public class MySqlDAOFactory extends DAOFactory {
	
	/**
	 * Returns an instance of one of the subclasses of the class UserDAO.
	 * @return UserDAO
	 */
	public UserDAO getUserDAO() {
		return new MySqlDAOUser();
	}
	
	/**
	 * Returns an instance of the class MySqlClient, taking care of the connection to the database.
	 * @return MySqlClient
	 */
	public static MySqlClient getConnection() {
		return new MySqlClient("remotemysql.com:3306", "EvR1zSObCT", "eMA8QUCWIG", "EvR1zSObCT");
	}
	

}
