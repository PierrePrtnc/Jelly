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
		this.fact = DAOFactory.getDAOFactory();
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
	public User login(String mail, String password) {
		UserDAO user = fact.getUserDAO();
		if (user.checkUserInfo(mail, password))
			return user.readUser(mail);
		return null;
	}
	
	public User addUser(String firstName, String lastName, String pseudo, String email, String password) {
		UserDAO user = fact.getUserDAO();
		if (user.insertUser(firstName, lastName, email, pseudo, password)) {
			return user.readUser(email);
		}
		return null;
	}
	
	public User getUser(String email) {
		UserDAO user = fact.getUserDAO();
		return user.readUser(email);
	}
	
	public boolean updateUser(String firstNameUser, String lastNameUser, String mailUser, String pseudoUser, String passwordUser) {
		UserDAO user = fact.getUserDAO();
		return user.updateUser(firstNameUser, lastNameUser, mailUser, pseudoUser, passwordUser);
	}
	public boolean deleteUser(String mailUser) {
		UserDAO user = fact.getUserDAO();
		return user.deleteUser(mailUser);
	}

}
