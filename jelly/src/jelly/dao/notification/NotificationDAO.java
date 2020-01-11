package jelly.dao.notification;

import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.notification.Notification;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public interface NotificationDAO {

    public boolean insertNotification(Collaborator sender, Collection<User> users, String message, String action);
    public boolean deleteNotification(int idNotification, User user);
    public boolean updateNotification(int idNotification, User user);
    public Notification readNotification(int idNotification);
    public ArrayList<Notification> readAllNotifications(User user);
    public ArrayList<Notification> unreadNotifications(User user);
}
