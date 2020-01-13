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
     * The state of the Step. The state can either be  1 = "to do", 2 = "in progress", 3 = "finished" or 4 = "re do".
     *
     * @see Step#getStepState()
     * @see Step#setStepState(int)
     */
    public int state;

    /**
     * The difficulty of the Step. The state can either be 1 = "Easy", 2 = "Medium" or 3 = "Hard".
     *
     * @see Step#getStepDifficulty()
     * @see Step#setStepDifficulty(int)
     */
    public int difficulty;

    /**
     * The Board in which the Step was created.
     *
     * @see Board
     *
     * @see Step#getBoard()
     */
    public Board board;

    /**
     * @see Step#getIdStep()
     */
    public int idStep;

    /**
     * @see Step#getIdBoardOfStep()
     */
    public int idBoard;

    /**
     *
     * @param idStep
     */
    public Step(int idStep) {
        this.idStep = idStep;
    }

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
        this.nameStep = name;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.tasks = new ArrayList<>();
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
        this.nameStep = name;
        this.tasks = tasks;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }
    /**
     * Constructor.
     * @param nameStep name of the step
     * @param descStep description of the step
     * @param initialDateStep date of the step beginning
     * @param finalDateStep date of the step end
     * @param difficulty difficulty of the step
     */
    public Step(String nameStep, String descStep, Date initialDateStep, Date finalDateStep, int difficultyStep) {
        this.nameStep = nameStep;
        this.descStep = descStep;
        this.initialDate = initialDateStep;
        this.finalDate = finalDateStep;
        this.difficulty = difficultyStep;
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor.
     * @param idStep id of the step
     * @param idBoard id of the board containing step
     * @param nameStep name of the step
     * @param initialDateStep date of the step beginning
     * @param finalDateStep date of the step end
     * @param stateStep state of the step
     * @param difficultyStep difficulty of the step
     */
    public Step(int idStep, int idBoard, String nameStep, java.sql.Date initialDateStep, java.sql.Date finalDateStep, int stateStep, int difficultyStep) {
        this.idStep = idStep;
        this.idBoard = idBoard;
        this.nameStep = nameStep;
        this.state = stateStep;
        this.initialDate = initialDateStep;
        this.finalDate = finalDateStep;
        this.difficulty = difficultyStep;
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor.
     * @param idStep id of the step
     * @param idBoard id of the board containing step
     * @param nameStep name of the step
     * @param initialDateStep date of the step beginning
     * @param finalDateStep date of the step end
     * @param stateStep state of the step
     * @param difficultyStep difficulty of the step
     * @param descriptionStep descriptionS of the step
     */
    public Step(int idStep, String nameStep, java.sql.Date initialDateStep, java.sql.Date finalDateStep, int idBoard,  int stateStep, int difficultyStep, String descriptionStep) {
        this.idStep = idStep;
        this.idBoard = idBoard;
        this.nameStep = nameStep;
        this.state = stateStep;
        this.initialDate = initialDateStep;
        this.finalDate = finalDateStep;
        this.difficulty = difficultyStep;
        this.descStep = descriptionStep;
        this.tasks = new ArrayList<>();
    }

    public Step() {

    }

    /**
     * Returns the name of the Step.
     *
     * @return the name of the Step, as a String.
     */
    public String getStepName() {
        return this.nameStep;
    }

    /**
     * Replaces the current name of the Step with the new name specified as argument.
     *
     * @param name
     *              The name of the Step
     */
    public void setStepName(String name) {
        this.nameStep = name;
    }

    /**
     *
     * @return
     */
    public int getIdStep() {
        return idStep;
    }

    /**
     *
     * @param idStep
     */
    public void setIdStep(int idStep) {
        this.idStep = idStep;
    }

    /**
     * Returns the description of the Step.
     *
     * @return the description of the Step, as a String.
     */
    public String getStepDesc() {
        return this.descStep;
    }

    /**
     * Replaces the current description of the Step with the new description specified as argument.
     *
     * @param description
     *              The description of the Step
     */
    public void setStepDescription(String description) {
        this.descStep = description;
    }

    /**
     * Returns the initial date of the Step.
     *
     * @return the initial date of the Step, as a Date.
     */
    public Date getInitialDate() {
        return this.initialDate;
    }

    /**
     * Replaces the current initial date of the Step with the new date specified as argument.
     *
     * @param initialDate
     *                      The initial date of the Step.
     */
    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * Returns the final date of the Step.
     *
     * @return the final date of the Step, as a Date.
     */
    public Date getFinalDate() {
        return this.finalDate;
    }

    /**
     * Replaces the current final date of the Step with the new date specified as argument.
     *
     * @param finalDate
     *                  The final date of the Step.
     */
    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    /**
     * Returns the state of the Step.
     *
     * @return the state of the Step, as a int.
     */
    public int getStepState() {
        return this.state;
    }

    /**
     * Replaces the current state of the Step with the new state specified as argument.
     *
     * @param state
     *              A State.
     */
    public void setStepState(int state) {
        this.state = state;
    }

    /**
     * Returns the difficulty of the Step.
     *
     * @return the difficulty of the Step, as a int.
     */
    public int getStepDifficulty() {
        return this.difficulty;
    }

    /**
     * Replaces the current difficulty of the Step with the new difficulty specified as argument.
     *
     * @param difficulty
     *              A difficulty.
     */
    public void setStepDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Returns all the tasks stored in the Task collection of the Step.
     *
     * @return The tasks stored in the Task collection of the Step, as a Collection of Task.
     *
     * @see Task
     */
    public Collection<Task> getTasks() {
        return this.tasks;
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
        for (Task t : this.tasks) {
            if (t.equals((Task) task)) {
                return t;
            }
        }
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
        this.tasks.add(task);
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
        this.tasks.remove((Task) task);
    }

    /**
     * Removes all the tasks from the Task collection of the Step.
     *
     * @see Task
     */
    public void removeAllTasks() {
        this.tasks.clear();
    }

    /**
     * Returns the Board in which the Step was created.
     *
     * @return The Board in which the Step was created, as a Board.
     *
     * @see Board
     */
    public Board getBoard() {
        return this.board;
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

    /**
     *
     * @return the id of the board containing the step
     */
    public int getIdBoardOfStep() { return idBoard; }

}