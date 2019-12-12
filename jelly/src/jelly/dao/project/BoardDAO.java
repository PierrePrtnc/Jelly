package jelly.dao.project;

import jelly.User;
import jelly.project.Project;
import jelly.project.Step;

import java.sql.ResultSet;
import java.util.Collection;

public interface BoardDAO {

    public boolean insertBoard(String nameBoard, Collection<Step> steps);
    public boolean updateBoard(int idBoard, String nameBoard, Collection<Step> steps);
    public boolean deleteBoard(int idBoard);
    public User readBoard(int idBoard);
    public ResultSet readAllBoards();

}
