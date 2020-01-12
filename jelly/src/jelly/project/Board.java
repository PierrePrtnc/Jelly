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
     * The subject of the board
     *
     * @see Board#getSubjectBoard()
     */
    public String subjectBoard;

    /**
     * The description of the board
     *
     * @see Board#getDescriptionBoard()
     */
    public String descriptionBoard;

    /**
     * The project in which the Board was created.
     *
     * @see Project
     *
     * @see Board#getProject()
     */
    public Project project;

    /**
     * Number ID for a board
     */
    private int idBoard;

    /**
     * Number ID of the project containing the board
     */
    private int idProject;

    /**
     * Default constructor of Board.
     * <p>
     * At the construction of a Board object, the state of the board is set to "to do" and
     * an empty collection of Steps is created.
     * </p>
     *
     * @param boardName
     *                  The name of the Board.
     * @see Board#nameBoard
     * @see Board#steps
     */
    public Board(String boardName) {
        this.nameBoard = boardName;
        this.steps = new ArrayList<>();
    }
    /**
     * Default constructor of Board.
     * <p>
     * At the construction of a Board object, the state of the board is set to "to do" and
     * an empty collection of Steps is created.
     * </p>
     *
     * @param boardName
     *                  The name of the Board.
     * @param subjectBoard
     *                  The subject of the Board.
     * @param descriptionBoard
     *                  The description of the Board.
     * @see Board#nameBoard
     * @see Board#steps
     */
    public Board(String boardName, String subjectBoard, String descriptionBoard) {
        this.nameBoard = boardName;
        this.subjectBoard = subjectBoard;
        this.descriptionBoard =descriptionBoard;
        this.steps = new ArrayList<>();
    }

    /**
     * Default constructor of Board.
     * <p>
     * At the construction of a Board object, the state of the board is set to "to do" and
     * an empty collection of Steps is created.
     * </p>
     *
     * @param idBoard
     *                  The id of the Board.
     * @see Board#nameBoard
     * @see Board#steps
     */
    public Board(int idBoard) {
        this.idBoard = idBoard;
    }

    public Board(String nameBoard, String description) {
        this.nameBoard = nameBoard;
        this.descriptionBoard = description;
    }

    /**
     * <p>
     * At the construction of a Board object, the state of the board is set to "to do" and
     * an empty collection of Steps is created.
     * </p>
     *
     * @param boardName
     *                  The name of the Board.
     * @param project
     *                  The project to which the Board is linked to
     *
     * @see Board#nameBoard
     * @see Board#steps
     */
    public Board(String boardName, Project project) {
        this.nameBoard = boardName;
        this.project = project;
    }

    /**
     * Constructor of Board.
     *
     * At the construction of a Board object, the state of the board is set to "to do".
     *
     * @param nameBoard
     *                  The name of the Board.
     * @param steps
     *                  A collection of Step.
     *
     * @see Board#nameBoard
     * @see Board#steps
     */
    public Board(String nameBoard, Collection<Step> steps, Project project) {
        this.nameBoard = nameBoard;
        this.steps = steps;
        this.project = project;
    }

    /**
     * Constructor of Board.
     *
     * At the construction of a Board object, the state of the board is set to "to do".
     *
     * @param idBoard
     *                  The id of the Board.
     * @param idProject
     *                  The id of the Project containing the Board.
     * @param nameBoard
     *                  The name of  the Board.
     * @param descriptionBoard
     *                  The description of the Board.
     * @param subjectBoard
     *                  The subject of  the Board.
     *
     * @see Board#nameBoard
     * @see Board#steps
     */
    public Board(int idBoard, int idProject, String nameBoard, String descriptionBoard, String subjectBoard) {
        this.idBoard = idBoard;
        this.idProject = idProject;
        this.nameBoard = nameBoard;
        this.descriptionBoard = descriptionBoard;
        this.subjectBoard = subjectBoard;
    }

    public Board() {

    }

    /**
     * Returns the name of the Board.
     *
     * @return the name of the Board, as a String.
     */
    public String getBoardName() {
        return this.nameBoard;
    }

    /**
     * Replaces the current name of the Board with the new name specified as argument.
     *
     * @param name
     *              The name of the Board.
     */
    public void setBoardName(String name) {
        this.nameBoard = nameBoard;
    }

    /**
     * Returns all the steps stored in the Step collection of the Board.
     *
     * @return The steps stored in the Step collection of the Board, as a Collection of Step.
     *
     * @see Step
     */
    public Collection<Step> getSteps() {
        return this.steps;
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
        for (Step s : this.steps) {
            if (s.equals((Step) step)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Adds a Step to the Step collection of the Board.
     *
     * @param step
     *              A Step
     *
     * @see Step
     */
    public void addStep(Step step) {
        ((ArrayList) this.steps).add(step);
    }

    /**
     * Removes a Step from the Step collection of the Board.
     *
     * @param step
     *              A Step, stored in the Step collection of the Board.
     *
     * @see Step
     */
    public void removeStep(Step step) {
        this.steps.remove((Step) step);

    }

    /**
     * Removes all the steps from the Step collection of the Board.
     *
     * @see Step
     */
    public void removeAllSteps() {
        this.steps.clear();

    }

    /**
     *
     * @return the subject of the current board
     */
    public String getSubjectBoard() {
        return subjectBoard;
    }

    /**
     * sets the subject of the current board with
     * @param subjectBoard
     */
    public void setSubjectBoard(String subjectBoard) {
        this.subjectBoard = subjectBoard;
    }

    /**
     *
     * @return the description of the current board
     */
    public String getDescriptionBoard() {
        return descriptionBoard;
    }

    /**
     * sets the description of the current board with
     * @param descriptionBoard
     */
    public void setDescriptionBoard(String descriptionBoard) {
        this.descriptionBoard = descriptionBoard;
    }

    /**
     * Returns the Project in which the Board was created.
     *
     * @return The Project in which the Board was created, as a Project.
     *
     * @see Project
     */
    public Project getProject() {
        return this.project;
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

    /**
     *
     * @return the id of the last board created
     */
    public int getIdBoard() {
        return idBoard;
    }

    /**
     * sets the value for the id of the latest board
     *
     * @param idBoard
     */
    public  void setIdBoard(int idBoard) {
        this.idBoard = idBoard;
    }

    /**
     *
     * @return the id of the project containing the board
     */
    public int getIdProjectOfBoard() { return idProject; }
}