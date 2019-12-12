package jelly.dao.notification;

import jelly.User;
import jelly.collaboration.Collaborator;

import java.sql.ResultSet;
import java.util.Collection;

public class MySqlDAONotification implements NotificationDAO {
    @Override
    public boolean insertNotification(Collaborator sender, Collection<User> users, String message, String[] action) {
        return false;
    }

    @Override
    public boolean updateNotification(int idNotification, Collaborator sender, Collection<User> users, String message, String[] action) {
        return false;
    }

    @Override
    public boolean deleteNotification(int idNotification) {
        return false;
    }

    @Override
    public User readNotification(int idNotification) {
        return null;
    }

    @Override
    public ResultSet readAllNotifications() {
        return null;
    }
}
