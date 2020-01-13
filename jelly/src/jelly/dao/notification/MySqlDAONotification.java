package jelly.dao.notification;

import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.dao.MySqlDAOFactory;
import jelly.dao.MySqlDAOUser;
import jelly.dao.UserDAO;
import jelly.database.MySqlClient;
import jelly.notification.Notification;
import jelly.project.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlDAONotification implements NotificationDAO {
	
	MySqlClient sql = MySqlDAOFactory.getConnection();
	
	@Override
	public boolean insertNotification(Collaborator sender, Collection<User> users, String message, String action) {
		String query = "insert into notification (messageNotification, actionNotification, idOriginator) values(?,?,?)";
		System.out.println("SENDER ID aka idOriginator: "+sender.user.getMailUser());
		for(User u : users) {
			System.out.println("USER ID aka idUser: "+u.getMailUser());
		}
		boolean insertSuccess = false;
		if(sql.connect()) {
			MySqlDAOUser userDAO = new MySqlDAOUser();
			int senderID = userDAO.getIdByMailUser(sender.user.getMailUser());
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pQuery.setString(1, message);
				pQuery.setString(2, action);
				pQuery.setInt(3, senderID);
				pQuery.executeUpdate();
				ResultSet res = pQuery.getGeneratedKeys();
				int idNotif = 0;
				while (res.next()) {
					 idNotif = res.getInt(1);
					 System.out.println("NOTIFICATION ID CREATED : "+idNotif);
				}
				for (User u : users) {
					if (insertJournal(idNotif, userDAO.getIdUser(u.getMailUser()), 0)) {
						insertSuccess = true;
					} else {
						insertSuccess = false;
					}
				}
				System.out.println("ok");
				pQuery.close();
				return insertSuccess;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("1");
				e.printStackTrace();
			}	
		} 
		sql.close();
		return false;
	}

	public boolean insertJournal(int idNotification, int idUser, int isRead) {
		String query =  "insert into notification_journal (idNotification, idUser, isRead) values(?,?,?)";
		System.out.println("NOTIFICATION ID TO INSERT IN JOURNAL : "+idNotification);
		System.out.println("USER ID TO INSERT IN JOURNAL : "+idUser);
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setInt(1, idNotification);
				pQuery.setInt(2, idUser);
				pQuery.setInt(3, isRead);
				pQuery.executeUpdate();
				pQuery.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

    @Override
    public boolean deleteNotification(int idNotification, User user) {
    	String query = "select idUser from user where mailUser = ?";
    	String query2 = "delete from notification_journal where idUser = ? and idNotification = ?";
    	int idUser = 0;
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, user.getMailUser());
				ResultSet res = pQuery.executeQuery();
				while (res.next()) {
					 idUser = res.getInt(1);
				}
				PreparedStatement pQuery2 = sql.getDbConnect().prepareStatement(query2);
				pQuery2.setInt(1, idUser);
				pQuery2.setInt(2, idNotification);
				pQuery2.executeUpdate();			
				return true;
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		sql.close();
		return false;
    }
    
    @Override
    public boolean updateNotification(int idNotification, User user) {
    	String queryInt = "Select idUser from user where mailUser = ?";
		String query = "update notification_journal set  isRead = 1 where idNotification = ? and idUser = ?";
		if(sql.connect()) {
			try {
				PreparedStatement pQueryInt = sql.getDbConnect().prepareStatement(queryInt);
				pQueryInt.setString(1,  user.getMailUser());
				ResultSet res = pQueryInt.executeQuery();
				int idUser = 0;
				while(res.next()) {
					idUser = res.getInt(1);
				}
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setInt(1, idNotification);
				pQuery.setInt(2, idUser);
				pQuery.executeUpdate();
				pQuery.close();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return false;
    }

    @Override
    public Notification readNotification(int idNotification) {
    	String query = "select * from notification where idNotification = ?";
    	String query2 = "select idUser from notification_journal where idNotification = ?";
		String messageNotification = "";
		String actionNotification = "";
		int idOriginator = 0;
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setInt(1, idNotification);
				ResultSet res = pQuery.executeQuery();
				while (res.next()) {
					 messageNotification = res.getString(2);
					 actionNotification = res.getString(3);
					 idOriginator = res.getInt(4);
				}
				PreparedStatement pQuery2 = sql.getDbConnect().prepareStatement(query2);
				pQuery2.setInt(1, idNotification);
				ResultSet res2 = pQuery2.executeQuery();
				ArrayList<Integer> idusers = new ArrayList<Integer>();
				while (res2.next()) {
					idusers.add(res2.getInt(1));
				}
				User[] receivers = new User[idusers.size()];
				String query3 = "select * from user where idUser = ?";
				for(int i = 0; i<idusers.size(); i++) {			
					PreparedStatement pQuery3 = sql.getDbConnect().prepareStatement(query3);
					String firstName = "";
					String lastName = "";
					String mail = "";
					String pseudo = "";
					pQuery3.setInt(1, idusers.get(i));
					ResultSet res3 = pQuery3.executeQuery();
					while (res3.next()) {
						 firstName = res3.getString(2);
						 lastName = res3.getString(3);
						 mail = res3.getString(4);
						 pseudo = res3.getString(5);
					}
					receivers[i] = new User(firstName, lastName, mail, pseudo);
				}
				
		    	String query4 = "select * from collaborator where idCollaborator = ?";
				PreparedStatement pQuery4 = sql.getDbConnect().prepareStatement(query4);
				pQuery4.setInt(1, idOriginator);
				ResultSet res4 = pQuery4.executeQuery();
				int idSender = 0;
				int idProject = 0;
				while (res4.next()) {
					 idSender = res4.getInt(3);
					 idProject = res4.getInt(2);
				}
				
		    	String query5 = "select * from project where idProject = ?";	
				PreparedStatement pQuery5 = sql.getDbConnect().prepareStatement(query5);
				pQuery5.setInt(1, idProject);
				ResultSet res5 = pQuery5.executeQuery();
				String nameProject = "";
				String descProject = "";
				Date initialDate = new Date(0,0,0);
				Date finalDate = new Date(0,0,0);
				while (res5.next()) {
					 nameProject = res5.getString(2);
					 descProject = res5.getString(3);
					 initialDate = res5.getDate(4);
					 finalDate = res5.getDate(5);
				}

				Project project = new Project(nameProject, descProject, initialDate, finalDate);
				PreparedStatement pQuery6 = sql.getDbConnect().prepareStatement(query3);
				pQuery6.setInt(1, idSender);
				ResultSet res6 = pQuery6.executeQuery();
				String firstNameS = "";
				String lastNameS = "";
				String mailS = "";
				String pseudoS = "";
				while (res6.next()) {
					 firstNameS = res6.getString(2);
					 lastNameS = res6.getString(3);
					 mailS = res6.getString(4);
					 pseudoS = res6.getString(5);
				}
				User senderuser = new User(firstNameS, lastNameS, mailS, pseudoS);
				Collaborator sender = new Collaborator(project, senderuser);
				return new Notification(sender, receivers, messageNotification, actionNotification);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return null;
    }

    @Override
    public ArrayList<Notification> readAllNotifications(User user) {
    	String query = "select idUser from user where mailUser = ?";
    	String query2 = "select idNotification from notification_journal where idUser = ?";
    	int idUser = 0;
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, user.getMailUser());
				ResultSet res = pQuery.executeQuery();
				while (res.next()) {
					 idUser = res.getInt(1);
				}
				PreparedStatement pQuery2 = sql.getDbConnect().prepareStatement(query2);
				pQuery2.setInt(1, idUser);
				ResultSet res2 = pQuery2.executeQuery();
				ArrayList<Notification> result = new ArrayList<Notification>();
			    while (res2.next()) {
			         result.add(readNotification(res2.getInt(1)));
			    }
			    return result;
			}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			sql.close();
			return null;
    }
    
    @Override
    public ArrayList<Notification> unreadNotifications(User user) {
    	String query = "select idUser from user where mailUser = ?";
    	String query2 = "select idNotification from notification_journal where idUser = ? and isRead = 0";
    	int idUser = 0;
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, user.getMailUser());
				ResultSet res = pQuery.executeQuery();
				while (res.next()) {
					 idUser = res.getInt(1);
				}
				PreparedStatement pQuery2 = sql.getDbConnect().prepareStatement(query2);
				pQuery2.setInt(1, idUser);
				ResultSet res2 = pQuery2.executeQuery();
				ArrayList<Notification> result = new ArrayList<Notification>();
			    while (res2.next()) {
			        result.add(readNotification(res2.getInt(1)));
			    }
			    return result;
			}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			sql.close();
			return null;
    }
    
    public User readUser(String mailUser) {
		String query = "select * from user where mailUser = ?";
		String firstName = "";
		String lastName = "";
		String mail = "";
		String pseudo = "";
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, mailUser);
				ResultSet res = pQuery.executeQuery();
				while (res.next()) {
					 firstName = res.getString(2);
					 lastName = res.getString(3);
					 mail = res.getString(4);
					 pseudo = res.getString(5);
				}
				return new User(firstName, lastName, mail, pseudo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return null;
	}

}
