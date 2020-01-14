package jelly.notification;

import jelly.User;
import jelly.collaboration.Collaborator;

import java.util.*;

/**
 * Notification management class
 * @author Anthony Dupré, Pierre Partinico
 * @version 0.1
 */
public class Notification {
	
	public static int idNotificationMax;
	
	private int idNotification = 1;

    /**
     * message of the notification
     */
    public String message;

    /**
     * destination users of the notification
     */
    public User[] destination;

    /**
     * originator user of the notification
     */
    public Collaborator originator;

	/**
	 * getter for the attribute message
	 * @return the attribute message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * setter for the attribute message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * getter for the attribute destination
	 * @return the attribute destination
	 */
	public User[] getDestination() {
		return destination;
	}

	/**
	 * setter for the attribute destination
	 * @param destination
	 */
	public void setDestination(User[] destination) {
		this.destination = destination;
	}

	/**
	 * getter for the attribute originator
	 * @return the attribute originator
	 */
	public Collaborator getOriginator() {
		return originator;
	}

	/**
	 * setter for the attribute originator
	 * @param originator
	 */
	public void setOriginator(Collaborator originator) {
		this.originator = originator;
	}

	/**
	 * getter for the attribute action
	 * @return the attribute action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * setter for the attribute action
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
     * actions that you can do with the notification
     */
    public String action;


    /**
     * @param sender originator of the notification
     * @param users destination users of the notification
     * @param message message of the notification
     * @param action actions you can do with the notification
     */
    public Notification(Collaborator sender, User[] users, String message, String action) {
    	this.setIdNotification(Notification.idNotificationMax + 1);
    	Notification.idNotificationMax = Notification.idNotificationMax + 1;
        this.originator = sender;
        this.destination = users;
        this.message = message;
        this.action = action;
    }

	/**
	 * getter for the attribute idNotification
	 * @return the attribute idNotification
	 */
	public int getIdNotification() {
		return idNotification;
	}

	/**
	 * setter for the attribute idNotification
	 * @param idNotification
	 */
	public void setIdNotification(int idNotification) {
		this.idNotification = idNotification;
	}

}