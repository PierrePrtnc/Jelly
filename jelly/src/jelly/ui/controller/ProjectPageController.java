package jelly.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProjectPageController {

    public JellyFacade jellyFacade;
    public Project project;
    public Board board;
    protected User connectedUser;

    private Scene scene;

    @FXML
    private VBox window;

    @FXML
    private Label projectLabel;

    @FXML
    private VBox boardDescriptionVBox;

    @FXML
    private CheckBox allBoardsCheckBox;

    @FXML
    private CheckBox currentCheckBox;

    @FXML
    private CheckBox finishedCheckBox;

    @FXML
    private MenuButton sortByMenuButton;

    @FXML
    private GridPane boardGripPane;

    public void showMembers(ActionEvent actionEvent){

    }

    public void showGantt(ActionEvent actionEvent){

    }

    public void leaveProject(ActionEvent actionEvent){

    }

    public void addNewBoard(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/newBoard.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((NewBoardController)loader.getController()).board = board;
        ((NewBoardController)loader.getController()).project = project;
        ((NewBoardController)loader.getController()).jellyFacade = jellyFacade;
        ((NewBoardController)loader.getController()).connectedUser = connectedUser;

        ((NewBoardController)loader.getController()).setScene(scene);
    }

    public void setScene(Scene scene) {
        List<Board> boards = jellyFacade.getAllBoardsByProject(project.getIdProject());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        projectLabel.setText("Project : "+ project.getProjectName());
        boardDescriptionVBox.getChildren().add(new Label("Description of the project :" + project.getProjectDescription()));
        boardDescriptionVBox.getChildren().add(new Label("Start date project : " + project.getInitialDate()));
        boardDescriptionVBox.getChildren().add(new Label("Expected end date project : " + project.getFinalDate()));
        boardGripPane.setPrefHeight(460);
        boardGripPane.setPrefWidth(600);
        int j = 0;
        int k = 0;
        for (int i = 0; i < boards.size(); i++) {
            if (j == 3) {
                j = 0;
                k++;
            }
            List<Step> steps = jellyFacade.getAllStepsByBoard(boards.get(i).getIdBoard());
            double accomplishmentPercent = 0;
            double stepFinished = 0;
            double totalStep = 0;
            VBox vbox = new VBox();
            vbox.getChildren().add(new Label("Board " + (i+1)));
            vbox.getChildren().add(new Label("Name : " + boards.get(i).getBoardName()));
            for (int l = 1; l < steps.size()+1; l++) {
                switch(steps.get(l-1).getStepState()) {
                    case 1:
                        vbox.getChildren().add(new Label("Step " + l + " - To do"));
                        totalStep = totalStep + 1;
                        break;
                    case 2:
                        vbox.getChildren().add(new Label("Step " + l + " - In progress"));
                        totalStep = totalStep + 1;
                        break;
                    case 3:
                        vbox.getChildren().add(new Label("Step " + l + " - Finished"));
                        stepFinished = stepFinished + 1;
                        totalStep = totalStep + 1;
                        break;
                    case 4:
                        vbox.getChildren().add(new Label("Step " + l + " - Re do"));
                        totalStep = totalStep + 1;
                        break;
                }
            }
            if (steps.isEmpty()){
                vbox.getChildren().add(new Label("This board has no steps "));
            }
            accomplishmentPercent = ((stepFinished / totalStep) * 100);
            if (!Double.isNaN(accomplishmentPercent)){
                vbox.getChildren().add(new Label("Accomplishment : " + round(accomplishmentPercent, 2)+"%"));
            }
            else {vbox.getChildren().add(new Label("Accomplishment can't be determined "));}
            vbox.getChildren().add(new Button("View"));
            boardGripPane.add(vbox, j, k);
            j++;
        }
        this.scene = scene;
    }
    
    public void showUnreadNotifications() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/NotificationsUI.fxml"));
            Parent root;
            root = loader.load();
            this.scene.setRoot(root);
			((NotificationsController)loader.getController()).emailUser = connectedUser.getMailUser();
			((NotificationsController)loader.getController()).currentUser = connectedUser;

            ((NotificationsController)loader.getController()).setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
