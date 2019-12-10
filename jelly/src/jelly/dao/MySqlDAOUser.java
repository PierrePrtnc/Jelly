package jelly.dao;

import java.sql.*;

import jelly.database.MySqlClient;

public class MySqlDAOUser {
	
	/**
	 * Variable to access the database
	 */
	MySqlClient sql = new MySqlClient("remotemysql.com:3306", "EvR1zSObCT", "eMA8QUCWIG", "EvR1zSObCT");

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
	 * @return
	 */
	public ResultSet readUser(String mailUser) {
		String query = "select * from user where mailUser = ?";
		if(sql.connect()) {
			try {
				PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
				pQuery.setString(1, mailUser);
				return pQuery.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.close();
		return null;
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
	
//	public static void main(String[] args) {
//		MySqlDAOUser db = new MySqlDAOUser();
//		if(db.insertUser("testJava2", "testJava2", "testJava2@mail.com", "testJava2", "testJava2")) {
//			System.out.println("Insert OK");
//		}
//		if(db.updateUser("testJava", "testJava", "weslie.rabeson@gmail.com", "testJava", "testJava")) {
//			System.out.println("Update OK");
//		}
//		if(db.deleteUser("testJava@mail.com")) {
//			System.out.println("Delete OK");
//		}
//		ResultSet res = db.readUser("weslie.rabeson@gmail.com");
//		try {
//			while(res.next()) {
//				System.out.println("Valeur: " + res.getString(2));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		res = db.readAllUsers();
//		try {
//			while(res.next()) {
//				System.out.println("Valeur: " + res.getString(2));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
