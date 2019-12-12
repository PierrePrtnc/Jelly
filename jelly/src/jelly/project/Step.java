package jelly.project;

import java.util.*;

/**
 * <b> Step is the class representing the different steps in a "phase". A Step is a composant of a Board.</b>
 * 
 * <p>
 * A Step is caracterized by the following information :
 * <ul>
 * <li> A name, that can be changed in the future.</li>
 * <li> A description, updatable.</li>
 * <li> An initial date, set by default to the date of the creation of the Step, and updatable.</li>
 * <li> A final date, updatable.</li>
 * <li> A state, that can either be "to do", "in progress", "finished" or "re do". The state is updatable.</li>
 * <li> A board, corresponding to the board in which the step was created.</li>
 * </ul>
 * </p>
 * <p>
 * Furthermore, a Step contains a collection of Tasks. A member of the project can add or delete a Task from the collection.
 * </p>
 * 
 * @see Board
 * @see Task
 * 
 * @author Arthur Leblanc
 * @version 0.1
 */
public class Step {

    /**
     * The name of the Step. This name can be changed.
     * 
     * @see Step#getStepName()
     * @see Step#setStepName(String)
     */
    public String nameStep;

    /**
     * The description of the Step. This description can be changed.
     * 
     * @see Step#getStepDesc()
     * @see Step#setStepDesc(String)
     */
    public String descStep;

    /**
     * The collection of Tasks in the Step.
     * It's possible to add or delete Tasks from the collection.
     * 
     * @see Task
     * 
     * @see Step#getTasks()
     * @see Step#addTask(Task)
     * @see Step#removeTask(Task)
     * @see Step#removeAllTasks()
     */
    public Collection<Task> tasks;

    /**
     * The date where the step's earlier task begins
     * 
     * @see Step#getInitialDate()
     * @see Step#setInitialDate(Date)
     */
    public Date initialDate;

    /**
     * The date where the step's latest task ends
     * 
     * @see Step#getFinalDate()
     * @see Step#setFinalDate(Date)
     */
    public Date finalDate;

    /**
     * The state of the Board. The state can either be "to do", "in progress", "finished" or "re do".
     * 
     * @see State
     * 
     * @see Step#getStepState()
     * @see Step#setStepState(State)
     */
    public State state;

    /**
     * The Board in which the Step was created.
     * 
     * @see Board
     * 
     * @see Step#getBoard()
     */
    public Board board;

    /**
     * Default constructor of Step.
     * 
     * <p>
     * At the construction of a Step object, the state is set to "to do". 
     * An empty collection of Steps is created.
     * </p>
     * 
     * @param name
     *              The name of the Step.
     * @param initialDate
     *              The initial date of the Step. 
     * @param finalDate
     *              The final date of the Step
     * 
     * @see Step#nameStep
     * @see Step#tasks
     * @see Step#initialDate
     * @see Step#finalDate
     * @see Step#state
     */
    public Step(String name, Date initialDate, Date finalDate) {
        // TODO implement here
    }

    /**
     * Constructor of Step.
     * 
     * At the construction of a Step object, the state is set to "to do".
     * 
     * @param name
     *              The name of the Step.
     * @param tasks 
     *              A collection of Tasks.
     * @param initialDate
     *              The initial date of the Step. 
     * @param finalDate
     *              The final date of the Step
     * 
     * @see Step#nameStep
     * @see Step#tasks
     * @see Step#initialDate
     * @see Step#finalDate
     * @see Step#state
     */
    public Step(String name, Collection<Task> tasks, Date initialDate, Date finalDate) {
        // TODO implement here
    }

    /**
     * Returns the name of the Step.
     * 
     * @return the name of the Step, as a String.
     */
    public String getStepName() {
        // TODO implement here
        return "";
    }

    /**
     * Replaces the current name of the Step with the new name specified as argument.
     * 
     * @param name
     *              The name of the Step 
     */
    public void setStepName(String name) {
        // TODO implement here
    }

    /**
     * Returns the description of the Step.
     * 
     * @return the description of the Step, as a String.
     */
    public String getStepDesc() {
        // TODO implement here
        return "";
    }
    
    /**
     * Replaces the current description of the Step with the new description specified as argument.
     * 
     * @param description
     *              The description of the Step 
     */
    public void setStepDescription(String description) {
        // TODO implement here
    }

    /**
     * Returns the initial date of the Step.
     * 
     * @return the initial date of the Step, as a Date.
     */
    public Date getInitialDate() {
        // TODO implement here
        return null;
    }

    /**
     * Replaces the current initial date of the Step with the new date specified as argument.
     * 
     * @param initialDate 
     *                      The initial date of the Step.
     */
    public void setInitialDate(Date initialDate) {
        // TODO implement here
    }

    /**
     * Returns the final date of the Step.
     * 
     * @return the final date of the Step, as a Date.
     */
    public Date getFinalDate() {
        // TODO implement here
        return null;
    }

    /**
     * Replaces the current final date of the Step with the new date specified as argument.
     * 
     * @param finalDate
     *                  The final date of the Step. 
     */
    public void setFinalDate(Date finalDate) {
        // TODO implement here
    }

    /**
     * Returns the state of the Step.
     * 
     * @return the state of the Step, as a State.
     */
    public State getStepState() {
        // TODO implement here
        return null;
    }

    /**
     * Replaces the current state of the Step with the new state specified as argument.
     * 
     * @param State
     *              A State.
     */
    public void setStepState(State state) {
        // TODO implement here
    }

    /**
     * Returns all the tasks stored in the Task collection of the Step.
     * 
     * @return The tasks stored in the Task collection of the Step, as a Collection of Task.
     * 
     * @see Task
     */
    public Collection<Task> getTasks() {
        // TODO implement here
        return null;
    }

    /**
     * Returns a task, stored in the Task collection of the Step.
     * 
     * @param task
     *              A Task, stored in the Task collection of the Step.  
     * 
     * @return The Task, stored in the Task collection of the Step.
     * 
     * @see Task
     */
    public Task getTask(Task task) {
        // TODO implement here
        return null;
    }

    /**
     * Adds a Task to the Task collection of the Step.
     * 
     * @param task
     *              A Task
     * 
     * @see Task
     */
    public void addTask(Task task) {
        // TODO implement here
    }

    /**
     * Removes a Task from the Task collection of the Step.
     * 
     * @param task
     *              A Task, stored in the Task collection of the Step.
     * 
     * @see Task
     */
    public void removeTask(Task task) {
        // TODO implement here
    }

    /**
     * Removes all the tasks from the Task collection of the Step.
     * 
     * @see Task
     */
    public void removeAllTasks() {
        // TODO implement here
    }

    /**
     * Returns the Board in which the Step was created.
     * 
     * @return The Board in which the Step was created, as a Board. 
     * 
     * @see Board
     */
    public Board getBoard() {
        // TODO implement here
        return null;
    }

    /**
     * Returns the information of the Step.
     * 
     * @return The information of the Step, as a String.
     */
    @Override
    public String toString() {
        return "";
    }

}