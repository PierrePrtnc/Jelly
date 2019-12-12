package jelly.notification;

import jelly.User;
import jelly.collaboration.Collaborator;

import java.util.*;

/**
 * Notification management class
 * @author Anthony Dupré
 * @version 0.1
 */
public class Notification {

    /**
     * message of the notification
     */
    public String message;

    /**
     * destination users of the notification
     */
    public Collection<User> destination;

    /**
     * originator user of the notification
     */
    public Collaborator originator;

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
    public void Notification(Collaborator sender, Collection<User> users, String message, String[] action) {
        // TODO implement here
    }

}