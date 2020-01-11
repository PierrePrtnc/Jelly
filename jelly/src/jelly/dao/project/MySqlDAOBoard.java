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

    @Override
    public boolean insertBoard(int idBoard, String nameBoard, State state, int idProject) {
        System.out.println("DEBUT INSERT BOARD");
        String query = "insert into board (nameBoard, stateBoard, idProject) values(?,?,?)";
        if(sql.connect()) {
            System.out.println("CONNEXION INSERT BOARD");
            if(readBoard(idBoard) == null) {
                System.out.println("VERIF BOARD EXISTE : " + readBoard(idBoard));
                try {
                    System.out.println("TRY INSERT BOARD");
                    int stateId;
                    if (state.equals(State.ToDo)) {
                        stateId = 0;
                    } else if (state.equals(State.InProgress)) {
                        stateId = 1;
                    } else if (state.equals(State.Finished)) {
                        stateId = 2;
                    } else {
                        stateId = 3;
                    }
                    System.out.println("REPERE 2 stateId : "+ stateId);
                    PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                    pQuery.setString(1, nameBoard);
                    pQuery.setInt(2, stateId);
                    pQuery.setInt(3, idProject);
                    pQuery.executeUpdate();
                    pQuery.close();
                    return true;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    System.out.println("1");
                    e.printStackTrace();
                }
            }
        }
        sql.close();
        return false;
    }

    @Override
    public boolean updateBoard(int idBoard, String nameBoard, State state) {
        String query = "update board set nameBoard = ?, stateBoard = ? where idBoard = ?";
        if(sql.connect()) {
            try {
                int stateId;
                if (state.equals(State.ToDo)) {
                    stateId = 0;
                } else if (state.equals(State.InProgress)) {
                    stateId = 1;
                } else if (state.equals(State.Finished)) {
                    stateId = 2;
                } else {
                    stateId = 3;
                }
                PreparedStatement pQuery = sql.getDbConnect().prepareStatement(query);
                pQuery.setString(1, nameBoard);
                pQuery.setInt(2, stateId);
                pQuery.setInt(3, idBoard);
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
/*
    public static void main(String[] args) {
        MySqlDAOBoard db = new MySqlDAOBoard();
		if(db.insertBoard(12, "testJava2", State.ToDo, 2)) {
			System.out.println("Insert OK");
		}
        System.out.println(db.readBoard(1));
		if(db.updateBoard(1, "testJava", State.InProgress)) {
			System.out.println("Update OK");
		}
        System.out.println(db.readBoard(5).getBoardName());
		if(db.deleteBoard(1)) {
			System.out.println("Delete OK");
		}
		System.out.println("Check login : " + db.readAllBoards());

    }*/
}
