package jelly.project;

import jelly.User;
import jelly.collaboration.Collaborator;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class ProjectTest {

    private Project project;
    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board("Board 1");
        Collection<Board> boards = new ArrayList<Board>();
        boards.add(board);
        project = new Project("Project name", "Description", boards);
    }

    @After
    public void tearDown() throws Exception {
        project = null;
    }

    @Test
    public void addBoard() {
        project.addBoard(new Board("Board 2"));
        assertEquals("Insert failed", 2, project.getBoards().size());
    }

    @Test
    public void removeBoard() {
        project.removeBoard(board);
        assertEquals("Deletion failed", 0, project.getBoards().size());
    }

    @Test
    public void addCollaborator() {
        project.addCollaborator(new User("userFirstName", "userLastName", "userMail", "userPseudo"));
        assertEquals("Insert failed", 1, project.getCollaborators().size());
    }

    @Test
    public void removeCollaborator() {
        project.removeCollaborator(new Collaborator(project,new User("userFirstName", "userLastName", "userMail", "userPseudo")));
        assertEquals("Deletion failed", 0, project.getCollaborators().size());
    }
}