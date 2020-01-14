package jelly;

import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jelly.collaboration.Collaborator;
import jelly.dao.DAOFactory;
import jelly.dao.UserDAO;
import jelly.dao.collaboration.CollaboratorDAO;
import jelly.dao.notification.NotificationDAO;
import jelly.dao.project.BoardDAO;
import jelly.dao.project.ProjectDAO;
import jelly.dao.project.StepDAO;
import jelly.dao.project.TaskDAO;
import jelly.notification.Notification;
import jelly.project.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Class JellyFacade.
 * @author Arthur Leblanc, Weslie Rabeson
 *
 */
public class JellyFacade {

	public DAOFactory fact;

	/**
	 * JellyFacade constructor. Instanciate the DAOfactory working with mySQL.
	 */
	public JellyFacade() {
		this.fact = DAOFactory.getDAOFactory();
	}


	/************************************ USER METHODS **********************************/

	/**
	 * Logs the user into the database.
	 *
	 * @param mail     The e-mail of the User.
	 * @param password The password of the User.
	 * @return boolean
	 * True if the login is successful, false otherwise.
	 */
	public User login(String mail, String password) {
		UserDAO user = fact.getUserDAO();
		if (user.checkUserInfo(mail, password))
			return user.readUser(mail);
		return null;
	}

	/**
	 * Adds a user to the database
	 * @param firstName
	 * @param lastName
	 * @param pseudo
	 * @param email
	 * @param password
	 * @return
	 */
	public User addUser(String firstName, String lastName, String pseudo, String email, String password) {
		UserDAO user = fact.getUserDAO();
		if (user.insertUser(firstName, lastName, email, pseudo, password)) {
			return user.readUser(email);
		}
		return null;
	}

	/**
	 * Reads a user from the database with a given email
	 * @param email
	 * @return
	 */
	public User getUser(String email) {
		UserDAO user = fact.getUserDAO();
		return user.readUser(email);
	}

	/**
	 * Updates a user of the database
	 * @param firstNameUser
	 * @param lastNameUser
	 * @param mailUser
	 * @param pseudoUser
	 * @param passwordUser
	 * @return
	 */
	public boolean updateUser(String firstNameUser, String lastNameUser, String mailUser, String pseudoUser, String passwordUser) {
		UserDAO user = fact.getUserDAO();
		return user.updateUser(firstNameUser, lastNameUser, mailUser, pseudoUser, passwordUser);
	}

	/**
	 * Deletes a user from the databse
	 * @param mailUser
	 * @return
	 */
	public boolean deleteUser(String mailUser) {
		UserDAO user = fact.getUserDAO();
		return user.deleteUser(mailUser);
	}

	/************************** PROJECT METHODS ********************************/

	/**
	 * Reads all projects by user
	 * @param mailUser the email of the user to display projects for
	 * @return list of projects
	 */
	public ArrayList<Project> getAllProjectsByUser(String mailUser) {
		ProjectDAO project = fact.getProjectDAO();
		return project.getAllProjectsByUser(mailUser);
	}

	/**
	 * Adds a project to the database
	 * @param name 			the name of the project to create
	 * @param description	the description of the project to create
	 * @param initialDate	the initial date of the project to create
	 * @param finalDate		the final date of the project to create
	 * @param creator		the id of the user creator of the project to create
	 * @return the Project with an ID determined by the database
	 */
	public Project insertProject(String name, String description, Date initialDate, Date finalDate, User creator) {
		ProjectDAO project = fact.getProjectDAO();
		return project.insertProject(name, description, initialDate, finalDate, creator);
	}

	/**
	 * Updates a project of the database
	 * @param idProject 	the ID of the project to update
	 * @param name			the name of the project to update
	 * @param description	the description of the project to update
	 * @param initialDate	the initial date of the project to update
	 * @param finalDate		the final date of the project to update
	 * @return	true if the project was successfully updated
	 */
	public boolean updateProject(int idProject, String name, String description, Date initialDate, Date finalDate) {
		ProjectDAO project = fact.getProjectDAO();
		return project.updateProject(idProject, name, description, initialDate, finalDate);
	}

	/**
	 * 	Deletes a project from the database
	 * @param idProject 	the ID of the project to delete
	 * @return  true if the project was successfully delete
	 */
	public boolean deleteProject(int idProject) {
		ProjectDAO project = fact.getProjectDAO();
		return project.deleteProject(idProject);
	}

	/**
	 * 	Reads a project from the database
	 * @param idProject		the ID of the project to display
	 * @return  the project
	 */
	public Project readProject(int idProject) {
		ProjectDAO project = fact.getProjectDAO();
		return project.readProject(idProject);
	}

	/************************** BOARD METHODS ********************************/

	/**
	 * 	Reads all boards by project
	 * @param idProject		the ID of the project to display the boards of
	 * @return a list of boards corresponding to the given project
	 */
	public ArrayList<Board> getAllBoardsByProject(int idProject) {
		BoardDAO board = fact.getBoardDAO();
		return board.getAllBoardsByProject(idProject);
	}

	/**
	 * 	Adds a board to the database
	 * @param nameBoard		the name of the board to create
	 * @param descriptionBoard the description of the board to create
	 * @param idProject		the ID of the project which to board is associated to
	 * @return the created board
	 */
	public Board insertBoard(String nameBoard, String subjectBoard, String descriptionBoard, int idProject) {
		BoardDAO board = fact.getBoardDAO();
		return board.insertBoard(nameBoard, subjectBoard, descriptionBoard, idProject);
	}

	/**
	 * 	Updates a board from the database
	 * @param idBoard		the ID of the board to update
	 * @param nameBoard		the name of the board to update
	 * @param subjectBoard		the subject of the board to update
	 * @param descriptionBoard	the description of the board to update
	 * @return true if the board was successfully updated
	 */
	public boolean updateBoard(int idBoard, int idProject, String nameBoard, String subjectBoard, String descriptionBoard) {
		BoardDAO board = fact.getBoardDAO();
		return board.updateBoard(idBoard, idProject, nameBoard, subjectBoard, descriptionBoard);
	}

	/**
	 * 	Deletes a board from the database
	 * @param idBoard		the ID of the board to delete
	 * @return true if the board was successfully deleted
	 */
	public boolean deleteBoard(int idBoard) {
		BoardDAO board = fact.getBoardDAO();
		return board.deleteBoard(idBoard);
	}

	/**
	 * 	Reads a board from the database
	 * @param idBoard		the ID of the board to display
	 * @return the board to read
	 */
	public Board readBoard(int idBoard) {
		BoardDAO board = fact.getBoardDAO();
		return board.readBoard(idBoard);
	}

	/************************** STEP METHODS ********************************/

	/**
	 * 	Reads all the steps by board
	 * @param idBoard 	the ID of the board of which the steps to display belong to
	 * @return a list of steps that belong to the given board
	 */
	public ArrayList<Step> getAllStepsByBoard(int idBoard) {
		StepDAO step = fact.getStepDAO();
		return step.getAllStepsByBoard(idBoard);
	}

	/**
	 * 	Adds a step to the database
	 * @param name			the name of the step to insert
	 * @param initialDate	the initial date of the step to insert
	 * @param finalDate 	the final date of the step to insert
	 * @param state			the state of the step to insert
	 * @param idBoard		the ID of the board of which the step belongs to
	 * @return the step created
	 */
	public Step insertStep(String name, Date initialDate, Date finalDate, int idBoard, int state, int difficulty, String description) {
		StepDAO step = fact.getStepDAO();
		return step.insertStep(name, initialDate, finalDate, idBoard, state, difficulty, description);
	}

	/**
	 * 	Updates a step of the database
	 * @param idStep		the ID of the step to update
	 * @param stepName		the name of the step to update
	 * @param startingDate	the initial date of the step to update
	 * @param endingDate 	the final date of the step to update
	 * @param state			the state of the step to update
	 * @param difficulty	the difficulty of the step to update
	 * @param description	the description of the step to update
	 * @return	true if the step was successfully updated
	 */
	public boolean updateStep(int idStep, String stepName, Date startingDate, Date endingDate, int idBoard, int state, int difficulty, String description) {
		StepDAO step = fact.getStepDAO();
		return step.updateStep(idStep, stepName, startingDate, endingDate, idBoard, state, difficulty, description);
	}

	/**
	 * 	Deletes a step from the database
	 * @param idStep		the ID of the step to delete
	 * @return	true if the step was successfully deleted
	 */
	public boolean deleteStep(int idStep) {
		StepDAO step = fact.getStepDAO();
		return step.deleteStep(idStep);
	}

	/**
	 * 	Reads a step from the database
	 * @param idStep		the ID of the step to display
	 * @return	the step read from the database
	 */
	public Step readStep(int idStep) {
		StepDAO step = fact.getStepDAO();
		return step.readStep(idStep);
	}

	/************************** TASK METHODS ********************************/

	/**
	 * 	Reads all the tasks by step
	 * @param idStep	the ID of the step of which the tasks to display belong to
	 * @return a list of tasks that belong to the given step
	 */
	public ArrayList<Task> getAllTasksByStep(int idStep) {
		TaskDAO task = fact.getTaskDAO();
		return task.getAllTasksByStep(idStep);
	}

	/**
	 * 	Adds a task to the database
	 * @param desc		the description of the task to insert
	 * @param state		the state of the task to insert
	 * @param idStep	the ID of the step to which the task is associated to
	 * @return	the task created
	 */
	public Task insertTask(String desc, int state, int idStep) throws SQLException {
		TaskDAO task = fact.getTaskDAO();
		return task.insertTask(desc, state, idStep);
	}

	/**
	 * 	Updates a task of the database
	 * @param idTask	the ID of the task to update
	 * @param desc		the description of the task to update
	 * @param state		the state of the task to update
	 * @return	true if the task was update successfully
	 */
	public boolean updateTask(int idTask, String desc, int state, int idStep) {
		TaskDAO task = fact.getTaskDAO();
		return task.updateTask(idTask, desc, state, idStep);
	}

	/**
	 * 	Deletes a task from the database
	 * @param idTask	the ID of the task to delete
	 * @return	true if the task was deleted successfully
	 */
	public boolean deleteTask(int idTask) {
		TaskDAO task = fact.getTaskDAO();
		return task.deleteTask(idTask);
	}

		/************************** NOTIFICATIONS METHODS ********************************/

	/**
	 * Reads unread notifications for a user
	 * @param user 		the user to display notifications for
	 * @return  Arraylist of notifications
	 */
	public ArrayList<Notification> getUnreadNotificationList(User user){
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.unreadNotifications(user);
	}

	/**
	 *  Adds a notification to the database
	 * @param sender 		the Collaborator who sent the notification
	 * @param users 		the collection of users who will receive the notification
	 * @param message 		the message of the notification
	 * @param action 		the action of the notification
	 * @return true if the insertion was successful
	 */
	public boolean insertNotification(Collaborator sender, Collection<User> users, String message, String action) {
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.insertNotification(sender, users, message, action);
	}

	/**
	 *  Deletes a notification from the database
	 * @param idNotification 	ID of the notification to delete
	 * @param user				the user who will get this notification deleted
	 * @return true if the deletion was successful
	 */
	public boolean deleteNotification(int idNotification, User user) {
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.deleteNotification(idNotification, user);
	}

	/**
	 * Changes the state of the notification
	 * @param idNotification 	ID of the notification to update
	 * @param user				the user who will get this notification update
	 * @return 	true if the update was successful
	 */
	public boolean changeStateNotification(int idNotification, User user) {
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.updateNotification(idNotification, user);
	}

	/**
	 *  Reads all notifications of a user
	 * @param user 		the user to read the notifications for
	 * @return 	an Arraylist of notifications
	 */
	public ArrayList<Notification> getAllNotificationList(User user) {
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.readAllNotifications(user);
	}

	/************************** COLLABORATION METHODS ********************************/

	/**
	 * 	inserts a collaborator into the database
	 * @param p
	 * @param u
	 * @return
	 */
	public Collaborator insertCollaborator(Project p, User u) {
		CollaboratorDAO collaborator = fact.getCollaboratorDAO();
		return collaborator.insertCollaborator(p, u);
	}

	/**
	 *  updates a collaborator from the database
	 * @param idCollaborator
	 * @param p
	 * @param u
	 * @return
	 */
	public boolean updateCollaborator(int idCollaborator, Project p, User u) {
		CollaboratorDAO collaborator = fact.getCollaboratorDAO();
		return collaborator.updateCollaborator(idCollaborator, p, u);
	}

	/**
	 *  deletes a collaborator from the database
	 * @param idCollaborator
	 * @return
	 */
	public boolean deleteCollaborator(int idCollaborator) {
		CollaboratorDAO collaborator = fact.getCollaboratorDAO();
		return collaborator.deleteCollaborator(idCollaborator);
	}

	/**
	 *  reads a collaborator from the database
	 * @param idCollaborator
	 * @return
	 */
	public Collaborator readCollaborator(int idCollaborator) {
		CollaboratorDAO collaborator = fact.getCollaboratorDAO();
		return collaborator.readCollaborator(idCollaborator);
	}

}