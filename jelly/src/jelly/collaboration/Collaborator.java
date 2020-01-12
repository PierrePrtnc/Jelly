package jelly.collaboration;

import jelly.User;
import jelly.project.*;

import java.util.*;

/**
 * @author Weslie
 */
public class Collaborator {

    /**
     * The collaborator ID (Database management)
     */
    private int idCollaborator;

    /**
     * Builds a Collaborator with a given project and user
     * Pre : Project and User must exist before calling the constructor
     * @param Project p
     * @param User u
     */
    public Collaborator(Project p, User u) {
    	this.project = p;
    	this.user = u;
    }

    /**
     *
     * @param idCollaborator
     * @param p
     * @param u
     */
    public Collaborator(int idCollaborator, Project p, User u) {
        this.idCollaborator = idCollaborator;
        this.project = p;
        this.user = user;
    }

    /**
     * The project associated to the collaboration
     */
    public Project project;

    /**
     * The user associated to the collaboration
     */
    public User user;

    /**
     * adds a Board to the collection of boards of the current project
     * with a name given in argument
     * @param String 
     */
    public void addBoard(String boardName) {
        // TODO implement here
    }

    public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
     * removes a given Board from the collection of boards of the current project
     * if the removal was successful, the Board to remove is returned
     * @param Board 
     * @return Board 
     */
    public Board removeBoard(Board boardToRemove) {
        // TODO implement here
        return null;
    }

    /**
     * adds a step to a Board from the collection of boards of the current project
     * with the name given in parameter
     * @param Board 
     * @param String 
     */
    public void addStep(Board boardToAffect, String stepName) {
        // TODO implement here
    }

    /**
     * removes a given step from the board given in parameter
     * returns the removed Step upon removal
     * @param Step 
     * @return Step
     */
    public Step removeStep(Step stepToRemove, Board boardToAffect) {
        // TODO implement here
        return null;
    }

    /**
     * adds a Task to the collection of a given Step 
     * from the collection of a given Board 
     * in the collection of the current project
     * with a given name
     * @param Board
     * @param Step 
     * @param String 
     */
    public void addTask(Board boardToAffect, Step stepToAffect, String taskName) {
        // TODO implement here
    }

    /**
     * removes a Task from the collection of a given Step 
     * from the collection of a given Board 
     * in the collection of the current project
     * @param Board
     * @param Step 
     * @param Task 
     * @return Task
     */
    public Task removeTask(Board boardToAffect, Step stepToAffect, Task taskToRemove) {
        // TODO implement here
        return null;
    }

    /**
     * updates the state of a Task from the collection of a given Step 
     * from the collection of a given Board 
     * in the collection of the current project
     * @param Board
     * @param Step 
     * @param String 
     */
    public void updateStateTask(Board boardToAffect, Step stepToAffect, String taskToUpdate) {
        // TODO implement here
    }

}