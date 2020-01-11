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

    public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




	public User[] getDestination() {
		return destination;
	}




	public void setDestination(User[] destination) {
		this.destination = destination;
	}




	public Collaborator getOriginator() {
		return originator;
	}




	public void setOriginator(Collaborator originator) {
		this.originator = originator;
	}




	public String getAction() {
		return action;
	}




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




	public int getIdNotification() {
		return idNotification;
	}




	public void setIdNotification(int idNotification) {
		this.idNotification = idNotification;
	}

}