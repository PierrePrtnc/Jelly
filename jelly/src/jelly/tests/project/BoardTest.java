package jelly.tests.project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jelly.project.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private Step step;

    @Before
    public void setUp() throws Exception {
        board = new Board("Board");
        step = new Step("Step", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"), new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2020"));
    }

    @After
    public void tearDown() throws Exception {
        board = null;
    }

    @Test
    public void addStep() throws ParseException {
        board.addStep(step);
        assertEquals("Insert failed", 1, board.getSteps().size());
    }

    @Test
    public void removeStep() {
        board.removeStep(step);
        assertEquals("Deletion failed", 0, board.getSteps().size());
    }

    @Test
    public void removeAllSteps() {
        board.removeAllSteps();
        assertEquals("Deletion failed", 0, board.getSteps().size());
    }
}