package jelly;

import jelly.project.Project;

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
    	*Constructor.
    	*
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param mail mail of the user 
     * @param pseudo pseudo of the user
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
	 * @param firstNameUser first name to be set
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
     * @param lastNameUser last name to be set
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
     * @param mailUser the mail to be set
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


	public void joinProject(Project project) {
		// TODO Auto-generated method stub
		
	}

}