package jelly.dao.project;

import jelly.User;
import jelly.dao.MySqlDAOFactory;
import jelly.dao.MySqlDAOUser;
import jelly.database.MySqlClient;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.State;
import jelly.project.Step;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MySqlDAOBoard implements BoardDAO {

    MySqlClient sql = MySqlDAOFactory.getConnection();

    /**
     *  inserts a board into the database
     * @param nameBoard             the name of the board to insert
     * @param descriptionBoard      the description of the board to insert
     * @param idProject             the ID of the project to which the board belongs to
     * @return  the board inserted into the database
     */
    @Override
    public Board insertBoard(String nameBoard, String subjectBoard, String descriptionBoard, int idProject) {
        System.out.println("DEBUT INSERT BOARD");
        String query = "insert into board (nameBoard, idProject, descriptionBoard, subjectBoard) values(?,?,?,?)";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pQuery.setString(1, nameBoard);
                pQuery.setInt(2, idProject);
                pQuery.setString(3, descriptionBoard);
                pQuery.setString(4, subjectBoard);
                pQuery.executeUpdate();
                ResultSet res = pQuery.getGeneratedKeys();
                if (res.next()) {
                    return new Board(res.getInt(1));
                }
                pQuery.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("1");
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    /**
     *      updates a board from the database
     * @param idBoard               the ID of the board to update
     * @param idProject             the new value for the project to which the board is associated to
     * @param nameBoard             the new value for the name of the board to update
     * @param subjectBoard          the new value for the subject of the board to update
     * @param descriptionBoard      the new value for the description of the board to update
     * @return  true if the board was successfully updated
     */
    @Override
    public boolean updateBoard(int idBoard, int idProject, String nameBoard, String subjectBoard, String descriptionBoard) {
        String query = "update board set idBoard = ?, nameBoard = ?, idProject = ?, descriptionBoard = ?, subjectBoard = ? where idBoard = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idBoard);
                pQuery.setString(2, nameBoard);
                pQuery.setInt(3, idProject);
                pQuery.setString(4, descriptionBoard);
                pQuery.setString(5, subjectBoard);
                pQuery.setInt(6, idBoard);
                pQuery.executeUpdate();
                pQuery.close();
                return true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return false;
    }

    /**
     *  deletes a board from the database
     * @param idBoard       the ID of the board to delete
     * @return  true if the board was successfully deleted
     */
    @Override
    public boolean deleteBoard(int idBoard) {
        String query = "delete from board where idBoard = ?";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idBoard);
                pQuery.executeUpdate();
                return true;
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        sql.close();
        return false;
    }

    /**
     *  reads a board from the database
     * @param idBoard   the ID of the board to display
     * @return  the board read from the database
     */
    @Override
    public Board readBoard(int idBoard) {
        String query = "select * from board where idBoard = ?";
        String nameBoard = "";
        if(sql.connect()) {
            try {
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setInt(1, idBoard);
                ResultSet res = pQuery.executeQuery();
                int stateValue = 0;
                while (res.next()) {
                    nameBoard = res.getString(2);
                }
                return new Board(nameBoard);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    /**
     *      reads all the board from the database
     * @return  a resultset describing all the boards from the database
     */
    @Override
    public ResultSet readAllBoards() {
        String query = "select * from board";
        if(sql.connect()) {
            try {
                Statement st = sql.getDbConnect().createStatement();
                return st.executeQuery(query);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sql.close();
        return null;
    }

    /**
     *  reads all the boards for a given project
     * @param idProject         the ID of the project to read the boards associated to it
     * @return  an arraylist of boards
     */
    @Override
    public ArrayList<Board> getAllBoardsByProject(int idProject) {
        if (idProject != 0) {
            String query = "select * from board where idProject = ?";

            int idBoard;
            String nameBoard;
            String descriptionBoard;
            String subjectBoard;
            List<Board> boards = new ArrayList<Board>();
            if(sql.connect()) {
                try {
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setInt(1, idProject);
                    ResultSet res = pQuery.executeQuery();
                    while (res.next()) {
                        idBoard = res.getInt(1);
                        nameBoard = res.getString(2);
                        descriptionBoard = res.getString(4);
                        subjectBoard = res.getString(5);
                        boards.add(new Board(idBoard, idProject, nameBoard, descriptionBoard, subjectBoard));
                    }
                    return (ArrayList) boards;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            sql.close();
        }
        return null;
    }

}
