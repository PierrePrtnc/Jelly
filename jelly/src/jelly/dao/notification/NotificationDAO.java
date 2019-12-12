package jelly.dao.notification;

import jelly.User;
import jelly.collaboration.Collaborator;

import java.sql.ResultSet;
import java.util.Collection;

public interface NotificationDAO {

    public boolean insertNotification(Collaborator sender, Collection<User> users, String message, String[] action);
    public boolean updateNotification(int idNotification, Collaborator sender, Collection<User> users, String message, String[] action);
    public boolean deleteNotification(int idNotification);
    public User readNotification(int idNotification);
    public ResultSet readAllNotifications();
}
