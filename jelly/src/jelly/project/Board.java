package jelly.project;

import java.util.*;

/**
 * <b> Board is the class representing the different "phases" of a project. A Board is a composant of a Project.</b>
 * <p>
 * A board is caracterized by the following information :
 * <ul>
 * <li> A name, that can be changed in the future. </li>
 * <li> A state, that can either be "to do", "in progress", "finished" or "re do". The state is directly calculated with the states of the steps of the Board.</li>
 * <li> A project, corresponding to the project in which the board was created. </li>
 * </p>
 * <p>
 * Furthermore, a Board contains a collection of Steps. A member of the project can add or delete a Step from the collection.
 * </p>
 * 
 * @see Project
 * @see Step
 * 
 * @author Arthur Leblanc
 * @version 0.1
 */
public class Board {

    /**
     * The name of the Board. This name can be changed. 
     * 
     * @see Board#getBoardName()
     * @see Board#setBoardName(String)
     */
    public String nameBoard;

    /**
     * The Collection of Steps in the Board.
     * <p>
     * It is possible to add or delete Steps from the collection.
     * </p>
     * 
     * @see Step
     * 
     * @see Board#getStep(Step)
     * @see Board#addStep(Step)
     * @see Board#removeStep(Step)
     * @see Board#removeAllSteps()
     */
    public Collection<Step> steps;

    /**
     * The state of the Board. The state can either be "to do", "in progress", "finished" or "re do".
     * 
     * @see State
     * 
     * @see Board#getState()
     */
    public State state;

    /**
     * The project in which the Board was created.
     * 
     * @see Project
     * 
     * @see Board#getProject()
     */
    public Project project;

    /**
     * Default constructor of Board.
     * <p>
     * At the construction of a Board object, the state of the board is set to "to do" and
     * an empty collection of Steps is created.
     * </p>
     * 
     * @param boardName
     *                  The name of the Board.
     * 
     * @see Board#nameBoard
     * @see Board#state
     * @see Board#steps
     */
    public void Board(String boardName) {
        // TODO implement here
    }

    /**
     * Constructor of Board.
     * 
     * At the construction of a Board object, the state of the board is set to "to do".
     * 
     * @param boardName
     *                  The name of the Board.
     * @param steps
     *                  A collection of Step.
     * 
     * @see Board#nameBoard
     * @see Board#state
     * @see Board#steps
     */
    public void Board(String nameBoard, Collection<Step> steps) {
        // TODO implement here
    }

    /**
     * Returns the name of the Board.
     * 
     * @return the name of the Board, as a String.
     */
    public String getBoardName() {
        // TODO implement here
        return "";
    }

    /**
     * Replaces the current name of the Board with the new name specified as argument.
     * 
     * @param name
     *              The name of the Board. 
     */
    public void setBoardName(String name) {
        // TODO implement here
    }

    /**
     * Returns all the steps stored in the Step collection of the Board.
     * 
     * @return The steps stored in the Step collection of the Board, as a Collection of Step.
     * 
     * @see Step
     */
    public Collection<Step> getSteps() {
        // TODO implement here
        return null;
    }

    /**
     * Returns a step, stored in the Step collection of the Board.
     * 
     * @param step
     *              A Step, stored in the Step collection of the Board.  
     * 
     * @return The Step, stored in the Step collection of the Board.
     * 
     * @see Step
     */
    public Step getStep(Step step) {
        // TODO implement here
        return null;
    }

    /**
     * Adds a Step to the Step collection of the Board.
     * 
     * @param Step
     *              A Step
     * 
     * @see Step 
     */
    public void addStep(Step step) {
        // TODO implement here
    }

    /**
     * Removes a Step from the Step collection of the Board.
     * 
     * @param Step
     *              A Step, stored in the Step collection of the Board.
     * 
     * @see Step
     */
    public void removeStep(Step step) {
        // TODO implement here

    }

    /**
     * Removes all the steps from the Step collection of the Board.
     * 
     * @see Step
     */
    public void removeAllSteps() {
        // TODO implement here

    }

    /**
     * Returns the current State of the Board.
     * 
     * @return The current State of the Board, as a State.
     * 
     * @see State
     */
    public State getState() {
        // TODO implement here
        return null;
    }

    /**
     * Returns the Project in which the Board was created.
     * 
     * @return The Project in which the Board was created, as a Project. 
     * 
     * @see Project
     */
    public Project getProject() {
        // TODO implement here
        return null;
    }

    /**
     * Returns the information of the Board.
     * 
     * @return The information of the Board, as a String.
     */
    @Override
    public String toString() {
        return "";
    }

}