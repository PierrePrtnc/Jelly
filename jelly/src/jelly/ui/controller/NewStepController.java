package jelly.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

public class NewStepController {

    protected JellyFacade jellyFacade;
    protected Project project;
    protected User connectedUser;

    protected Board board;
    protected Step step;
    private Scene scene;

    @FXML
    private VBox window;

    @FXML
    private TextField stepNameField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button createButton;

    @FXML
    private MenuButton stateMenuButton;

    @FXML
    private MenuButton difficultyMenuButton;

    @FXML
    private DatePicker startingDatePicker;

    @FXML
    private DatePicker endingDatePicker;
    
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

    public void toDoMenu(javafx.event.ActionEvent actionEvent) {
        stateMenuButton.setText("To do");
    }

    public void inProgressMenu(javafx.event.ActionEvent actionEvent) {
        stateMenuButton.setText("In progress");
    }

    public void finishedMenu(javafx.event.ActionEvent actionEvent) {
        stateMenuButton.setText("Finished");
    }

    public void reDoMenu(javafx.event.ActionEvent actionEvent) {
        stateMenuButton.setText("Re do");
    }

    public void easyMenu(javafx.event.ActionEvent actionEvent) {
        difficultyMenuButton.setText("Easy");
    }

    public void mediumMenu(javafx.event.ActionEvent actionEvent) {
        difficultyMenuButton.setText("Medium");
    }

    public void hardMenu(javafx.event.ActionEvent actionEvent) {
        difficultyMenuButton.setText("Hard");
    }

    public void createStep() {
        if(stepNameField.getText().isEmpty() ||  stateMenuButton.getText().equals("Choose state") || difficultyMenuButton.getText().equals("Choose difficulty") || startingDatePicker.getValue() == null || endingDatePicker.getValue() == null || descriptionField.getText().isEmpty()) {
            String emptyFields = "Please enter :\n";
            if(stepNameField.getText().isEmpty()) {
                emptyFields += "step name\n";
            }
            if(stateMenuButton.getText().equals("Choose state")) {
                emptyFields += "step state\n";
            }
            if(difficultyMenuButton.getText().equals("Choose difficulty")) {
                emptyFields += "step difficulty\n";
            }
            if(startingDatePicker.getValue() == null) {
                emptyFields += "starting date\n";
            }
            if(endingDatePicker.getValue() == null) {
                emptyFields += "ending date\n";
            }
            if(descriptionField.getText().isEmpty()) {
                emptyFields += "step description\n";
            }
            showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", emptyFields);
        }
        else {
            Date startingDate = Date.from(startingDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endingDate = Date.from(endingDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            int state = 0;
            int difficulty =0;
            switch(stateMenuButton.getText()) {
                case "To do":
                    state = 1;
                    break;
                case "In progress":
                    state = 2;
                    break;
                case "Finished":
                    state = 3;
                    break;
                case "Re do":
                    state = 4;
                    break;
            }
            switch(difficultyMenuButton.getText()) {
                case "Easy":
                    difficulty = 1;
                    break;
                case "Medium":
                    difficulty = 2;
                    break;
                case "Hard":
                    difficulty = 3;
                    break;
            }
            if(jellyFacade.insertStep(stepNameField.getText(), startingDate, endingDate, board.getIdBoard(), state, difficulty, descriptionField.getText()) != null){
                showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your step has been created");
            }
            else {
                showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "The creation has failed, please try again");

            }
        }
    }

    public void returnToBoardPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/boardPage.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((BoardPageController)loader.getController()).jellyFacade = jellyFacade;
        ((BoardPageController)loader.getController()).project = project;
        ((BoardPageController)loader.getController()).connectedUser = connectedUser;

        ((BoardPageController)loader.getController()).board = board;
        ((BoardPageController)loader.getController()).setScene(scene);
    }

    public void setScene(Scene scene) { this.scene = scene; }

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
