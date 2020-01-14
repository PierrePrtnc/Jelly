package jelly.notification;

import jelly.User;
import jelly.collaboration.Collaborator;

import java.util.*;

/**
 * Notification handler management class
 * @author Anthony Dupré
 * @version 0.1
 */
public class NotificationHandler {

    /**
     * a notification
     */
    public Notification notification;

    /**
     * @param notification a notification
     */
    public void NotificationHandler(Notification notification) {
        // TODO implement here
    }

    /**
        * Sends the notification to a collection of users. Creates the right buttons depending on the actions.
        *
     * @param sender originator of the notification
     * @param users destination users of the notification
     * @param notification notification to send
     * @return
     */
    public void sendNotification(Collaborator sender, Collection<User> users, Notification notification) {
        // TODO implement here
    }

}