package jelly;


import java.util.*;
import jelly.project.*;

import jelly.notification.*;

/**
 * User management class
 * @author Anthony Dupré
 * @version 0.1
 */
public class User {

    /**
     * First name of the user
     */
    public String firstNameUser;

    /**
     * Last name of the user
     */
    public String lastNameUser;

    /**
     * Mail of the user
     */
    public String mailUser;

    /**
     * Pseudo of the user
     */
    public String pseudoUser;

	/**
	 * Collection of all the projects of the user
	 */
	public Collection<Project> projects;

	/**
	 * Collection of all the notifications of the user
	 */
	public Collection<Notification> notifications;

    /**
    	*Constructor.
    	*
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param mail mail of the user 
     * @param pseudo pseudo of the user 
     * @param password password of the user
     */
    public User(String firstName, String lastName, String mail, String pseudo) {
        this.firstNameUser = firstName;
        this.lastNameUser = lastName;
        this.mailUser = mail;
        this.pseudoUser = pseudo;
    }
    

    /**
     * @return the first name of the user
     */
    public String getFirstNameUser() {
		return firstNameUser;
	}
    
    /**
	 * Changes the first name of the user
	 *
	 * @param firstName first name to be set
	 */
	public void setFirstNameUser(String firstNameUser) {
		this.firstNameUser = firstNameUser;
	}

	/**
     * @return the last name of the user
     */
	public String getLastNameUser() {
		return lastNameUser;
	}

    /**
     * @param lastName last name to be set
     */
	public void setLastNameUser(String lastNameUser) {
		this.lastNameUser = lastNameUser;
	}

    /**
     * @return the mail of the user
     */
	public String getMailUser() {
		return mailUser;
	}

    /**
     * @param mail the mail to be set
     */
	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

    /**
     * @return the pseudo of the user
     */
	public String getPseudoUser() {
		return pseudoUser;
	}

    /**
     * @param pseudoUser pseudo to be set
     */
	public void setPseudoUser(String pseudoUser) {
		this.pseudoUser = pseudoUser;
	}

	/**
	 * @return the collection of the projects of the user
	 */
	public Collection<Project> getProjects() {
		// TODO implement here
		return null;
	}

	/**
	 * @return a project of the user
	 */
	public Project getProject() {
		// TODO implement here
		return null;
	}

	/**
	 * @return the collection of the notifications of the user
	 */
	public Collection<Notification> getNotifications() {
		// TODO implement here
		return null;
	}

	/**
	 * @return a notification of the user
	 */
	public Notification getNotification() {
		// TODO implement here
		return null;
	}

	/**
	 * @param name name of the project
	 * @param description description of the project
	 * @param initialDate date of the project beginning
	 * @param finalDate date of the project end
	 * @return
	 */
	public void createProject(String name, String description, Date initialDate, Date finalDate) {
		// TODO implement here
	}

	/**
	 * @param name name of the project
	 * @param desciption description of the project
	 * @param boards initial boards of the project
	 * @return
	 */
	public void createProject(String name, String desciption, Collection<Board> boards) {
		// TODO implement here
	}

}