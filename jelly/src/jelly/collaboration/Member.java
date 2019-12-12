package jelly.collaboration;

import jelly.User;
import jelly.project.Project;

import java.util.*;

/**
 * @author Weslie
 */
public class Member extends Collaborator {

    /**
     * Builds a Member with a given project and user calling the super-constructor
     * Pre : Project and User must exist before calling the constructor
     * @param Project 
     * @param User
     */
    public Member(Project p, User u) {
        // TODO implement here
        super(p, u);
    }

    /**
     * removes the current member from the super.project
     * @see removeMember(User)
     * @return
     */
    public void leaveProject() {
        // TODO implement here
    }

}