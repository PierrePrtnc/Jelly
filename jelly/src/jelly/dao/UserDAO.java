package jelly.dao;

import java.sql.ResultSet;

/**
 * Interface UserDAO
 * @author Arthur Leblanc, Weslie Rabeson
 *
 */
public interface UserDAO {
	
	public boolean insertUser(String firstNameUser, String lastNameUser, String mailUser, String pseudoUser, String passwordUser);
	public boolean updateUser(String firstNameUser, String lastNameUser, String mailUser, String pseudoUser, String passwordUser);
	public boolean deleteUser(String mailUser);
	public ResultSet readUser(String mailUser);
	public ResultSet readAllUsers();
	public boolean checkUserInfo(String mailUser, String password);
	
}
