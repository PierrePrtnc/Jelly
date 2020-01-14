import jelly.JellyFacade;
import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.notification.Notification;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;
import jelly.project.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Array;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class JellyFacadeTest {
    private JellyFacade jellyFacade;
    private int idProject;
    private int idBoard;
    private int idStep;
    private int idTask;

    @Before
    public void setUp() throws Exception {
        jellyFacade = new JellyFacade();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void login() {
        User connectedUser = jellyFacade.login("a@a.fr", "a");
        assertEquals("Login failed", "a@a.fr", connectedUser.getMailUser());
    }

    @Test
    public void addUser() {
        User user = jellyFacade.addUser("b", "b", "b", "c@c.fr", "b");
        assertEquals("Insert failed", "c@c.fr", user.getMailUser());
    }

    @Test
    public void getUser() {
        User user = jellyFacade.getUser("UnitTest@UnitTest.fr");
        assertEquals("GetUser failed", "UnitTest@UnitTest.fr", user.getMailUser());
    }

    @Test
    public void updateUser() {
        boolean edit = jellyFacade.updateUser("ba", "ba", "c@c.fr", "ba", "b");
        assertTrue(edit);
    }

    @Test
    public void deleteUser() {
        boolean delete = jellyFacade.deleteUser("c@c.fr");
        assertTrue(delete);
    }

    @Test
    public void insertProject() throws ParseException {
        Project p = jellyFacade.insertProject("projet 2", "desc", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"), new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2020"), jellyFacade.getUser("UnitTest@UnitTest.fr"));
        assertEquals("insertProject failed", "projet 2", jellyFacade.readProject(p.getIdProject()).getProjectName());
    }

    @Test
    public void updateProject() throws ParseException {
        boolean update = jellyFacade.updateProject(idProject, "projet 21", "desc", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"), new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2020"));
        assertTrue(update);
    }

    @Test
    public void deleteProject() {
        boolean delete = jellyFacade.deleteProject(idProject);
        assertTrue(delete);
    }

    @Test
    public void readProject() {
        Project p = jellyFacade.readProject(42);
        assertEquals("Get project failed", "ProjetTest", p.getProjectName());
    }

    @Test
    public void insertBoard() throws ParseException {
        Board b = jellyFacade.insertBoard("Board 2", "subject", "desc", 42);
        assertEquals("insertBoard failed", "Board 2", jellyFacade.readBoard(b.getIdBoard()).getBoardName());
    }

    @Test
    public void updateBoard() {
        boolean update = jellyFacade.updateBoard(idBoard, idProject, "Board 23", "subject", "desc");
        assertTrue(update);
    }

    @Test
    public void deleteBoard() {
        boolean delete = jellyFacade.deleteBoard(idBoard);
        assertTrue(delete);
    }

    @Test
    public void readBoard() {
        Board b = jellyFacade.readBoard(30);
        assertEquals("Get Board failed", "BoardTest", b.getBoardName());
    }

    @Test
    public void insertStep() throws ParseException {
        Step s = jellyFacade.insertStep("Step A", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"), new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2020"),30, 1, 1, "desc");
        assertEquals("insertStep failed", "Step A", jellyFacade.readStep(s.getIdStep()).getStepName());
    }

    @Test
    public void updateStep() throws ParseException {
        boolean update = jellyFacade.updateStep(idStep, "Step AB", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"), new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2020"),30, 1, 1, "desc");
        assertTrue(update);
    }

    @Test
    public void deleteStep() {
        boolean delete = jellyFacade.deleteStep(idStep);
        assertTrue(delete);
    }

    @Test
    public void readStep() {
        Step s = jellyFacade.readStep(40);
        assertEquals("Get Step failed", "StepTest", s.getStepName());
    }

    @Test
    public void getAllTasksByStep() {
        ArrayList<Task> tasks = jellyFacade.getAllTasksByStep(42);
        assertEquals("getAllTasksByStep failed", 1, tasks.size());
    }

    @Test
    public void changeStateNotification() {
        boolean change = jellyFacade.changeStateNotification(5, jellyFacade.getUser("UnitTest@UnitTest.fr"));
        assertTrue(change);
    }

    @Test
    public void getAllNotificationList() {
        ArrayList<Notification> notifs = jellyFacade.getAllNotificationList(jellyFacade.getUser("UnitTest@UnitTest.fr"));
        assertEquals("getAllNotificationList failed", 3, notifs.size());
    }

}