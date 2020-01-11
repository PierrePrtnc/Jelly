package jelly;

import jelly.dao.DAOFactory;
import jelly.dao.UserDAO;
import jelly.dao.notification.NotificationDAO;
import jelly.dao.project.ProjectDAO;
import jelly.notification.Notification;
import jelly.project.Project;

import java.util.ArrayList;
import java.util.Date;

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


	/************************************ USER METHODS **********************************/
	
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

	/************************** PROJECT METHODS ********************************/

	public ArrayList<Project> getAllProjectsByUser(String mailUser) {
		ProjectDAO project = fact.getProjectDAO();
		return project.getAllProjectsByUser(mailUser);
	}

	public boolean insertProject(String name, String description, Date initialDate, Date finalDate, User creator) {
		ProjectDAO project = fact.getProjectDAO();
		UserDAO user = fact.getUserDAO();
		return project.insertProject(name, description, initialDate, finalDate, creator);
	}
	
	public ArrayList<Notification> getUnreadNotificationList(User user){
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.unreadNotifications(user);
	}
	
	public boolean deleteNotification(int idNotification, User user) {
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.deleteNotification(idNotification, user);
	}



}