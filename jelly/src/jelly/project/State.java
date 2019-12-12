package jelly.project;

/**
 * <b> Enumeration State, representing the state of a Project, Board, Step and Task.</b>
 *
 * A State can either be "To do", "In progress", "Finished" or "Re do".
 *
 * @see Project
 * @see Board
 * @see Step
 * @see Task
 *
 * @author Arthur Leblanc
 * @version 0.1
 */
public enum State extends Board {
	ToDo,
	InProgress,
	Finished,
	ReDo
}