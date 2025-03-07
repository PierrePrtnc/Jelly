package jelly.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import jelly.User;
import jelly.project.Project;

/**
 * Interface UserDAO
 * @author Arthur Leblanc, Weslie Rabeson
 *
 */
public interface UserDAO {
	
	public boolean insertUser(String firstNameUser, String lastNameUser, String mailUser, String pseudoUser, String passwordUser);
	public boolean updateUser(String firstNameUser, String lastNameUser, String mailUser, String pseudoUser, String passwordUser);
	public boolean deleteUser(String mailUser);
	public User readUser(String mailUser);
	public ResultSet readAllUsers();
	public boolean checkUserInfo(String mailUser, String password);
	public int getIdByMailUser(String mailUser);
	
}