package jelly.dao.notification;

import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.notification.Notification;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public interface NotificationDAO {

    /**
     *
     * @param sender
     * @param users
     * @param message
     * @param action
     * @return
     */
    public boolean insertNotification(Collaborator sender, Collection<User> users, String message, String action);

    /**
     *
     * @param idNotification
     * @param user
     * @return
     */
    public boolean deleteNotification(int idNotification, User user);

    /**
     *
     * @param idNotification
     * @param user
     * @return
     */
    public boolean updateNotification(int idNotification, User user);

    /**
     *
     * @param idNotification
     * @return
     */
    public Notification readNotification(int idNotification);

    /**
     *
     * @param user
     * @return
     */
    public ArrayList<Notification> readAllNotifications(User user);

    /**
     *
     * @param user
     * @return
     */
    public ArrayList<Notification> unreadNotifications(User user);
}
