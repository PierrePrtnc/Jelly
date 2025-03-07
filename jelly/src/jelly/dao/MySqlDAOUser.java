package jelly.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import jelly.User;
/**
 * Class MySqlDAOUser
 * @author Weslie Rabeson, Arthur Leblanc
 */
import jelly.database.MySqlClient;
import jelly.project.Project;

public class MySqlDAOUser implements UserDAO {
	/**
	 * Variable to access the database
	 */
	MySqlClient sql = MySqlDAOFactory.getConnection();

	/**
	 * inserts parameters into the database with a statement like
	 *
	 * 		insert into table (columns) values (values)
	 *
	 * @param firstNameUser
	 * @param lastNameUser
	 * @param mailUser
	 * @param pseudoUser
	 * @param passwordUser
	 * @return
	 */
	public boolean insertUser(String firstNameUser, String lastNameUser, String mailUser, String pseudoUser, String passwordUser) {
		String query = "insert into user (firstNameUser, lastNameUser, mailUser, pseudoUser, passwordUser) values(?,?,?,?,?)";
		if(sql.connect()) {
			if(readUser(mailUser) == null) {
				try {
					PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
					pQuery.setString(1, firstNameUser);
					pQuery.setString(2, lastNameUser);
					pQuery.setString(3, mailUser);
					pQuery.setString(4, pseudoUser);
					pQuery.setString(5, passwordUser);
					pQuery.executeUpdate();
					pQuery.close();
					return true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		sql.close();
		return false;
	}

	/**
	 * updates values from the database with given parameters with a statement like
	 *
	 * 		update table set column = newValue
	 *
	 * @param firstNameUser
	 * @param lastNameUser
	 * @param mailUser
	 * @param pseudoUser
	 * @param passwordUser
	 * @return
	 */
	public boolean updateUser(String firstNameUser, String lastNameUser, String mailUser, String pseudoUser, String passwordUser) {
		String query = "update user set firstNameUser = ?, lastNameUser = ?, mailUser = ?, pseudoUser = ?, passwordUser = ? where mailUser = ?";
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, firstNameUser);
				pQuery.setString(2, lastNameUser);
				pQuery.setString(3, mailUser);
				pQuery.setString(4, pseudoUser);
				pQuery.setString(5, passwordUser);
				pQuery.setString(6, mailUser);
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

	/**
	 * deletes a row from the database with given parameter with a statement like
	 *
	 * 		delete from table where column = parameter
	 *
	 * @param mailUser
	 * @return
	 */
	public boolean deleteUser(String mailUser) {
		String query = "delete from user where mailUser = ?";
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, mailUser);
				pQuery.executeUpdate();
				return true;
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		sql.close();
		return false;
	}

	/**
	 * reads a row from the database with the given parameter, with a statement like
	 *
	 * 		select * from table where column = parameter
	 *
	 * @param mailUser
	 * @return User
	 */
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
				if (firstName != "")
					return new User(firstName, lastName, mail, pseudo);
				else
					return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return null;
	}

	/**
	 * 	fetches the mail address of a given user
	 * @param idUser 		the ID of the user to fetch the mail address for
	 * @return 		the mail address
	 */
	public String getEmailUser(int idUser) {
		String query = "select * from user where idUser = ?";
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setInt(1, idUser);
				ResultSet res = pQuery.executeQuery();
				if (res.next()) {
					return res.getString(4);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return "";
	}

	/**
	 *   fetches the ID of the user with its email
	 * @param mailUser			the mail address of the user
	 * @return 		the ID of the user
	 */
	public int getIdUser(String mailUser) {
		String query = "select * from user where mailUser = ?";
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, mailUser);
				ResultSet res = pQuery.executeQuery();
				if (res.next()) {
					return res.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return -1;
	}

	/**
	 * reads several rows from the database with a statement like
	 *
	 * 		select * from user
	 *
	 * @return
	 */
	public ResultSet readAllUsers() {
		String query = "select * from user";
		if(sql.connect()) {
			try {
				Statement st = sql.getDbConnect().createStatement();
				return st.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return null;
	}



	/**
	 * Returns a boolean indicating the truthfulness of the user information.
	 * @param mailUser 		the mail address to check
	 * @param password		the password to check
	 * @return boolean
	 * 					True if the information of the user is found in the database, false otherwise.
	 */
	public boolean checkUserInfo(String mailUser, String password) {
		String query = "select mailUser, passwordUser from user where mailUser = ? and passwordUser = ?";
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, mailUser);
				pQuery.setString(2, password);
				ResultSet res = pQuery.executeQuery();
				return res.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return false;
	}

	/**
	 *   fetches the ID of the user with its email
	 * @param mailUser			the mail address of the user
	 * @return 		the ID of the user
	 */
	public int getIdByMailUser(String mailUser) {
		String query = "select idUser from user where mailUser = ?";
		int id = 0;
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, mailUser);
				ResultSet res = pQuery.executeQuery();
				while (res.next()) {
					id = res.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return id;
	}

}
