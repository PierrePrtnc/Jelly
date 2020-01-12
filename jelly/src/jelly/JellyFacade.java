package jelly;

import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jelly.dao.DAOFactory;
import jelly.dao.UserDAO;
import jelly.dao.notification.NotificationDAO;
import jelly.dao.project.BoardDAO;
import jelly.dao.project.ProjectDAO;
import jelly.dao.project.StepDAO;
import jelly.dao.project.TaskDAO;
import jelly.notification.Notification;
import jelly.project.*;

import java.util.ArrayList;
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

	public User addUser(String firstName, String lastName, String pseudo, String email, String password) {
		UserDAO user = fact.getUserDAO();
		if (user.insertUser(firstName, lastName, email, pseudo, password)) {
			return user.readUser(email);
		}
		return null;
	}

	public User getUser(String email) {
		UserDAO user = fact.getUserDAO();
		return user.readUser(email);
	}

	public boolean updateUser(String firstNameUser, String lastNameUser, String mailUser, String pseudoUser, String passwordUser) {
		UserDAO user = fact.getUserDAO();
		return user.updateUser(firstNameUser, lastNameUser, mailUser, pseudoUser, passwordUser);
	}

	public boolean deleteUser(String mailUser) {
		UserDAO user = fact.getUserDAO();
		return user.deleteUser(mailUser);
	}

	/************************** PROJECT METHODS ********************************/

	/**
	 *
	 * @param mailUser the email of the user to display projects for
	 * @return list of projects
	 */
	public ArrayList<Project> getAllProjectsByUser(String mailUser) {
		ProjectDAO project = fact.getProjectDAO();
		return project.getAllProjectsByUser(mailUser);
	}

	/**
	 *
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
	 *
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
	 *
	 * @param idProject 	the ID of the project to delete
	 * @return  true if the project was successfully delete
	 */
	public boolean deleteProject(int idProject) {
		ProjectDAO project = fact.getProjectDAO();
		return project.deleteProject(idProject);
	}

	/**
	 *
	 * @param idProject		the ID of the project to display
	 * @return  the project
	 */
	public Project readProject(int idProject) {
		ProjectDAO project = fact.getProjectDAO();
		return project.readProject(idProject);
	}

	/************************** BOARD METHODS ********************************/

	/**
	 *
	 * @param idProject		the ID of the project to display the boards of
	 * @return a list of boards corresponding to the given project
	 */
	public ArrayList<Board> getAllBoardsByProject(int idProject) {
		BoardDAO board = fact.getBoardDAO();
		return board.getAllBoardsByProject(idProject);
	}

	/**
	 *
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
	 *
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
	 *
	 * @param idBoard		the ID of the board to delete
	 * @return true if the board was successfully deleted
	 */
	public boolean deleteBoard(int idBoard) {
		BoardDAO board = fact.getBoardDAO();
		return board.deleteBoard(idBoard);
	}

	/**
	 *
	 * @param idBoard		the ID of the board to display
	 * @return the board to read
	 */
	public Board readBoard(int idBoard) {
		BoardDAO board = fact.getBoardDAO();
		return board.readBoard(idBoard);
	}

	/************************** STEP METHODS ********************************/

	/**
	 *
	 * @param idBoard 	the ID of the board of which the steps to display belong to
	 * @return a list of steps that belong to the given board
	 */
	public ArrayList<Step> getAllStepsByBoard(int idBoard) {
		StepDAO step = fact.getStepDAO();
		return step.getAllStepsByBoard(idBoard);
	}

	/**
	 *
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
	 *
	 * @param idStep		the ID of the step to update
	 * @param name			the name of the step to update
	 * @param initialDate	the initial date of the step to update
	 * @param finalDate 	the final date of the step to update
	 * @param state			the state of the step to update
	 * @return	true if the step was successfully updated
	 */
	public boolean updateStep(int idStep, String name, Date initialDate, Date finalDate, State state) {
		StepDAO step = fact.getStepDAO();
		return step.updateStep(idStep, name, initialDate, finalDate, state);
	}

	/**
	 *
	 * @param idStep		the ID of the step to delete
	 * @return	true if the step was successfully deleted
	 */
	public boolean deleteStep(int idStep) {
		StepDAO step = fact.getStepDAO();
		return step.deleteStep(idStep);
	}

	/**
	 *
	 * @param idStep		the ID of the step to display
	 * @return	the step read from the database
	 */
	public Step readStep(int idStep) {
		StepDAO step = fact.getStepDAO();
		return step.readStep(idStep);
	}

	/************************** TASK METHODS ********************************/

	/**
	 *
	 * @param idStep	the ID of the step of which the tasks to display belong to
	 * @return a list of tasks that belong to the given step
	 */
	public ArrayList<Task> getAllTasksByStep(int idStep) {
		TaskDAO task = fact.getTaskDAO();
		return task.getAllTasksByStep(idStep);
	}

	/**
	 *
	 * @param desc		the description of the task to insert
	 * @param state		the state of the task to insert
	 * @param idStep	the ID of the step to which the task is associated to
	 * @return	the task created
	 */
	public Task insertTask(String desc, State state, int idStep) {
		TaskDAO task = fact.getTaskDAO();
		return task.insertTask(desc, state, idStep);
	}

	/**
	 *
	 * @param idTask	the ID of the task to update
	 * @param desc		the description of the task to update
	 * @param state		the state of the task to update
	 * @return	true if the task was update successfully
	 */
	public boolean updateTask(int idTask, String desc, State state) {
		TaskDAO task = fact.getTaskDAO();
		return task.updateTask(idTask, desc, state);
	}

	/**
	 *
	 * @param idTask	the ID of the task to delete
	 * @return	true if the task was deleted successfully
	 */
	public boolean deleteTask(int idTask) {
		TaskDAO task = fact.getTaskDAO();
		return task.deleteTask(idTask);
	}

	/**
	 *
	 * @param idTask 	the ID of the task to display
	 * @return	the task to display
	 */
	public Task readTask(int idTask) {
		TaskDAO task = fact.getTaskDAO();
		return task.readTask(idTask);
	}


		/************************** NOTIFICATIONS METHODS ********************************/

	
	public ArrayList<Notification> getUnreadNotificationList(User user){
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.unreadNotifications(user);
	}
	
	public boolean deleteNotification(int idNotification, User user) {
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.deleteNotification(idNotification, user);
	}

	public boolean changeStateNotification(int idNotification, User user) {
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.updateNotification(idNotification, user);
	}


	public ArrayList<Notification> getAllNotificationList(User user) {
		NotificationDAO notification = fact.getNotificationDAO();
		return notification.readAllNotifications(user);
	}

}