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

    public boolean insertBoard(int idBoard, String nameBoard, State state, int idProject);
    public boolean updateBoard(int idBoard, String nameBoard, State state);
    public boolean deleteBoard(int idBoard);
    public Board readBoard(int idBoard);
    public ResultSet readAllBoards();

}
