package jelly.collaboration;

import jelly.User;
import jelly.project.Project;

import java.util.*;

/**
 * @author Weslie
 */
public class Creator extends Collaborator {


    /**
     * Builds a Creator with a given project and user calling the super-constructor
     * Pre : Project and User must exist before calling the constructor
     * @param p
     * @param u
     */
    public Creator(Project p, User u) {
        // TODO implement here
        super(p, u);
    }

    /**
     * Adds a User to the collection of Collaborators from the current (super)project
     * as a new Member
     * @param userToAdd
     * @return
     */
    public void addMember(User userToAdd) {
        // TODO implement here
    }

    /**
     * removes a member from the collection of Collaborators from the current (super)project
     * @param memberToRemove
     * @return User
     */
    public User removeMember(User memberToRemove) {
        // TODO implement here
        return null;
    }

    /**
     * replaces the current creator (aka super.user) of the current super.project
     * with a new collaborator who will be the creator
     * @param newCollaborator
     */
    public void setCreator(Collaborator newCollaborator) {
        // TODO implement here
    }

    /**
     * changes the name and description of the current super.project
     * @param projectName
     * @param projectDescription
     * @return
     */
    public void updateProject(String projectName, String projectDescription) {
        // TODO implement here
    }

}