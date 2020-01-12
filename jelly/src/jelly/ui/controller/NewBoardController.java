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

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

public class NewBoardController {

    protected JellyFacade jellyFacade;
    protected Project project;
    protected User connectedUser;
    protected Board board;
    private Scene scene;

    @FXML
    private VBox window;

    @FXML
    private TextField boardNameField;

    @FXML
    private TextField subjectField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button createButton;

    public void createBoard() {
        if(boardNameField.getText().isEmpty() || subjectField.getText().isEmpty() || descriptionArea.getText().isEmpty()) {
            String emptyFields = "Please enter :\n";
            if(boardNameField.getText().isEmpty()) {
                emptyFields += "board name\n";
            }
            if(subjectField.getText().isEmpty()) {
                emptyFields += "board subjct\n";
            }
            if(descriptionArea.getText().isEmpty()) {
                emptyFields += "board description\n";
            }
            showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", emptyFields);
        }
        else {
            if(jellyFacade.insertBoard(boardNameField.getText(), subjectField.getText(), descriptionArea.getText(), project.getIdProject()) != null){
                showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your board has been created");
                returnToProjectPage();
            }
            else {
                showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "The creation has failed, please try again");

            }
        }
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

    public void returnToProjectPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/projectPage.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((ProjectPageController)loader.getController()).jellyFacade = jellyFacade;
        ((ProjectPageController)loader.getController()).connectedUser = connectedUser;
        ((ProjectPageController)loader.getController()).project = project;
        ((ProjectPageController)loader.getController()).setScene(scene);
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


