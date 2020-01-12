package jelly.dao.project;

import jelly.User;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.State;
import jelly.project.Step;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public interface BoardDAO {

    public Board insertBoard(String nameBoard, String subjectBoard, String descriptionBoard, int idProject);
    public boolean updateBoard(int idBoard, int idProject, String nameBoard, String subjectBoard, String descriptionBoard);
    public boolean deleteBoard(int idBoard);
    public Board readBoard(int idBoard);
    public ResultSet readAllBoards();
    public ArrayList<Board> getAllBoardsByProject(int idProject);

}
