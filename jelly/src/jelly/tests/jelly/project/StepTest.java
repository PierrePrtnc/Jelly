package jelly.project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class StepTest {

    private Step step;
    private Task task;

    @Before
    public void setUp() throws Exception {
        step = new Step("Step", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"), new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2020"));
        task = new Task("Task");
    }

    @After
    public void tearDown() throws Exception {
        step = null;
    }

    @Test
    public void addTask() throws ParseException {
        step.addTask(task);
        assertEquals("Insert failed", 1, step.getTasks().size());
    }

    @Test
    public void removeTask() {
        step.removeTask(task);
        assertEquals("Deletion failed", 0, step.getTasks().size());
    }

    @Test
    public void removeAllTasks() {
        step.removeAllTasks();
        assertEquals("Deletion failed", 0, step.getTasks().size());
    }
}