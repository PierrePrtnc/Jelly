package jelly.project;

import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.collaboration.Creator;
import jelly.collaboration.Member;

import java.util.*;

/**
 * Project management class
 * @author Pierre Partinico
 * @version 0.1
 */
public class Project {

    /**
     * Name of the project
     */
    private String nameProject;

    /**
     * Description of the project
     */
    private String descriptionProject;

    /**
     * Collection of all the steps of the project
     */
    private Collection<Step> ganttDiagramProject;

    /**
     * Starting date of the project
     */
    private Date initialDateProject;

    /**
     * Final date of the project
     */
    private Date finalDateProject;

    /**
     * Collection of all the boards of the project
     */
    private Collection<Board> boards;

    /**
     * Collection of all the collaborators of the project
     */
    private Collection<Collaborator> collaborators;





    /**
	 * Constructor.
	 *
     * @param name name of the project 
     * @param description description of the project 
     * @param boards initial boards of the project
     */
    public Project(String name, String description, Collection<Board> boards) {
        this.nameProject = name;
        this.descriptionProject = description;
        this.boards = boards;
    }

    /**
	 * Constructor.
	 *
     * @param name name of the project 
     * @param description description of the project 
     * @param initialDate date of the project beginning
     * @param finalDate date of the project end
     */
    public Project(String name, String description, Date initialDate, Date finalDate) {
        this.nameProject = name;
        this.descriptionProject = description;
        this.initialDateProject = initialDate;
        this.finalDateProject = finalDate;
    }

    /**
     * @return the name of the project
     */
    public String getProjectName() {
        // TODO implement here
        return this.nameProject;
    }

    /**
	 * Change the name of the project.
	 *
     * @param name to be set
     */
    public void setProjectName(String name) {
        this.nameProject = name;
    }

    /**
     * @return name of the project.
     */
    public String getProjectDescription() {
        // TODO implement here
        return this.descriptionProject;
    }

    /**
	 * Change the description of the project.
	 *
     * @param description to be set
     */
    public void setProjectDescription(String description) {
        this.descriptionProject = description;
    }

    /**
     * @return the collection of all the steps of the project. This allows to get the Gantt Diagram of the project
     */
    public Collection<Step> getGanttDiagram() {
        return ((Board) boards).getSteps();
    }

    /**
     * @return the initial date of the project
     */
    public Date getInitialDate() {
        // TODO implement here
        return this.initialDateProject;
    }

    /**
	 * Change the initial date of the project
     * @param initialDate date to be set as the initial date of the project
     */
    public void setInitialDate(Date initialDate) {
        this.initialDateProject = initialDate;
    }

    /**
     * @return the final date of the project
     */
    public Date getFinalDate() {
        // TODO implement here
        return this.finalDateProject;
    }

    /**
	 * Change the initial date of the project
     * @param finalDate date to be set as the final date of the project
     */
    public void setFinalDate(Date finalDate) {
        this.finalDateProject = finalDate;
    }

    /**
     * @return 	all the boards of the project
     */
    public Collection<Board> getBoards() {
        // TODO implement here
        return boards;
    }

    /**
     * @param board name of the board
     * @return the board corresponding to the name given in parameter
     */
    public Board getBoard(String board) {
        // TODO implement here
        return null;
    }

    /**
     * @return all the collaborators of the project
     */
    public Collection<Collaborator> getCollaborators() {
        // TODO implement here
        return null;
    }

    /**
     * @param email mail address of the collaborator looked for
     * @return the collaborator having the mail address corresponding to the one given in parameter. If there is no user with this mail address, it returns an exception.
     */
    public Collaborator getCollaborator(String email) {
        // TODO implement here
        return null;
    }

    /**
	 * Add a board to the project.
     * @param board board to be added to the project
     */
    public void addBoard(Board board) {
        // TODO implement here
    }

    /**
	 * Remove a board from the project.
     * @param board board to be removed from the project
     */
    public void removeBoard(Board board) {
        // TODO implement here
    }

    /**
	 * Add a user as a collaborator of the project.
     * @param user user to be added as a collaborator of the project 
     */
    public void addCollaborator(User user) {
        // TODO implement here
    }

    /**
	 * Remove a collaborator from the project.
     * @param collaborator to be removed from the project
     */
    public void removeCollaborator(Collaborator collaborator) {
        // TODO implement here
    }

    /**
     * @return the instance of the creator of the project
     */
    public Creator getCreator() {
        // TODO implement here
        return null;
    }

    /**
     * @return all the members of the project
     */
    public Collection<Member> getMembers() {
        // TODO implement here
        return null;
    }

    /**
     * @param mail mail address of the member looked for?
     * @return the member having the mail address given in parameter. IF there is not any member with this mail address in the project, it return an exception.
     */
    public Member getMember(String mail) {
        // TODO implement here
        return null;
    }

    /**
	 * Send a notification to all the collaborators once someone modifies the project.
     * @param sender collaborator who modified something in a project, and broadcasts a modifications to all the other collaborators
     * @param receivers All the collaborators who are going to receive the notification related to the project modification
     */
    public void broadcastToMembers(Collaborator sender, Collection<Collaborator> receivers) {
        // TODO implement here
    }

}