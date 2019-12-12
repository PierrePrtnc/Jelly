package jelly.dao.project;

import jelly.User;
import jelly.project.Step;

import java.sql.ResultSet;
import java.util.Collection;

public class MySqlDAOBoard implements BoardDAO {
    @Override
    public boolean insertBoard(String nameBoard, Collection<Step> steps) {
        return false;
    }

    @Override
    public boolean updateBoard(int idBoard, String nameBoard, Collection<Step> steps) {
        return false;
    }

    @Override
    public boolean deleteBoard(int idBoard) {
        return false;
    }

    @Override
    public User readBoard(int idBoard) {
        return null;
    }

    @Override
    public ResultSet readAllBoards() {
        return null;
    }
}
