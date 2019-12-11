package jelly;

import jelly.dao.DAOFactory;
import jelly.dao.UserDAO;

/**
 * Class JellyFacade.
 * @author Arthur Leblanc, Weslie Rabeson
 *
 */
public class JellyFacade {
	
	public DAOFactory fact;
	
	/**
	 * JellyFacade constructor. Instanciate the DAOfactory working with mySQL.
	 */
	public JellyFacade() {
		this.fact = DAOFactory.getDAOFactory("mySQL");
	}
	
	/**
	 * Logs the user into the database. 
	 * @param mail
	 * 				The e-mail of the User.
	 * @param password
	 * 				The password of the User.
	 * @return boolean
	 * 				True if the login is successful, false otherwise.
	 */
	public boolean login(String mail, String password) {
		UserDAO user = fact.getUserDAO();
		return user.checkUserInfo(mail, password);
	}
}
