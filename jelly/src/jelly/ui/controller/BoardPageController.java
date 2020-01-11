package jelly.ui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class BoardPageController {

    protected JellyFacade jellyFacade;
    protected Board board;

    @FXML
    private Label boardLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private VBox boardDescriptionVBox;

    @FXML
    private Button addNewStepButton;

    @FXML
    private CheckBox allStepsCheckBox;

    @FXML
    private CheckBox toDoCheckBox;

    @FXML
    private CheckBox inProgressCheckBox;

    @FXML
    private CheckBox redoCheckBox;

    @FXML
    private CheckBox doneCheckBox;

    @FXML
    private MenuButton sortByMenuButton;

    @FXML
    private GridPane stepGripPane;

    private Scene scene;

    public void setScene(Scene scene) {
        List<Step> steps = jellyFacade.getAllStepsByBoard(5);
        boardLabel.setText("Board 1");
        boardDescriptionVBox.getChildren().add(new Label("Description of the board"));
        stepGripPane.setPrefHeight(460);
        stepGripPane.setPrefWidth(600);
        int j = 0;
        int k = 0;
        for (int i = 0; i < steps.size(); i++) {
            if (j == 2) {
                j = 0;
                k++;
            }
            VBox vbox = new VBox();
            vbox.getChildren().add(new Label(steps.get(i).getStepName()));
            switch(steps.get(i).getStepState()) {
                case 1:
                    vbox.getChildren().add(new Label("To do"));
                    break;
                case 2:
                    vbox.getChildren().add(new Label("In progress"));
                    break;
                case 3:
                    vbox.getChildren().add(new Label("Finished"));
                    break;
                case 4:
                    vbox.getChildren().add(new Label("Re do"));
                    break;
            }
            switch(steps.get(i).getStepDifficulty()) {
                case 1:
                    vbox.getChildren().add(new Label("Easy"));
                    break;
                case 2:
                    vbox.getChildren().add(new Label("Medium"));
                    break;
                case 3:
                    vbox.getChildren().add(new Label("Hard"));
                    break;
            }
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            vbox.getChildren().add(new Label(df.format(steps.get(i).getInitialDate())));
            vbox.getChildren().add(new Label(df.format(steps.get(i).getFinalDate())));
            vbox.getChildren().add(new Button("Consulter"));
            stepGripPane.add(vbox, j, k);
            j++;
        }
        this.scene = scene;
    }

}
