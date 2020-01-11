package jelly.project;

import java.util.*;

/**
 * <b> Task is the class representing the differents tasks to do in a Step. A task is a composant of a Step.</b>
 * <p>
 * A task is caracterized by the following information :
 * <ul>
 * <li> A description, that can be changed in the future.</li>
 * <li >A state, that can either be "to do", "in progress", "finished" or "re do". The state is updatable.</li>
 * <li> A step, corresponding to the step in which the task was created.</li>
 * </ul>
 * </p>
 *
 * @see Board
 *
 * @author Arthur Leblanc
 * @version 0.1
 */
public class Task {

    /**
     * The description of the Task. This description can be changed.
     *
     * @see Task#getTaskDesc()
     * @see Task#setTaskDesc(String)
     */
    public String description;

    /**
     * The state of the Task. The state can either be "to do", "in progress", "finished" or "re do".
     *
     * @see State
     *
     * @see Task#getTaskState()
     * @see Task#setTaskState(State)
     */
    public State state;

    /**
     * The step in which the Task was created.
     *
     * @see Step
     *
     * @see Task#getStep()
     */
    public Step step;

    /**
     * @see Task#getIdTask()
     */
    public int idTask;

    /**
     *
     * @param idTask
     */
    public Task(int idTask) {
        this.idTask = idTask;
    }

    /**
     * Default constructor of Task.
     *
     * At the construction of a Task object, the state is set to "to do". 
     *
     * @param desc
     *                  The description of the task.
     *
     * @see Task#description
     * @see Task#state
     */
    public Task(String desc) {
        this.description = desc;
    }

    /**
     * Returns the description of the task.
     *
     * @return the description of the task, as a String.
     */
    public String getTaskDesc() {
        return this.description;
    }

    /**
     * Replaces the current description of the task with the new description specified as argument.
     *
     * @param desc
     *              The description of the task.
     */
    public void setTaskDesc(String desc) {
        this.description = desc;
    }

    /**
     * Returns the state of the task.
     *
     * @return the state of the task, as a State.
     */
    public State getTaskState() {
        return this.state;
    }

    /**
     * Replaces the current state of the task with the new state specified as argument.
     *
     * @param state
     *              The state of the task.
     */
    public void setTaskState(State state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public int getIdTask() {
        return idTask;
    }

    /**
     *
     * @param idTask
     */
    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    /**
     * Returns the step in which the task was created.
     *
     * @return the step in which the task was created, as a Step.
     */
    public Step getStep() {
        return this.step;
    }

    /**
     * Returns the information of the Task.
     *
     * @return The information of the Task, as a String.
     */
    @Override
    public String toString() {
        return "";
    }

}