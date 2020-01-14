package jelly.ui.controller;

import javafx.event.ActionEvent;
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
import jelly.project.Task;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

public class UpdateTaskController {

    protected JellyFacade jellyFacade;
    protected Project project;
    protected User connectedUser;
    protected Board board;
    protected Step step;
    protected Task task;
    private Scene scene;

    @FXML
    private VBox window;

    @FXML
    protected TextArea descriptionArea;

    @FXML
    protected MenuButton stateMenuButton;

    public void done(javafx.event.ActionEvent actionEvent) {
        stateMenuButton.setText("Done");
    }

    public void undone(javafx.event.ActionEvent actionEvent) {
        stateMenuButton.setText("Undone");
    }

    public void returnToStepPage(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/stepPage.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((StepPageController)loader.getController()).jellyFacade = jellyFacade;
        ((StepPageController)loader.getController()).project = project;
        ((StepPageController)loader.getController()).step = step;
        ((StepPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((StepPageController)loader.getController()).connectedUser = connectedUser;
        ((StepPageController)loader.getController()).board = board;
        ((StepPageController)loader.getController()).setScene(scene);
    }

    public void updateTask(ActionEvent actionEvent) {
        if(stateMenuButton.getText().equals("Choose state") || descriptionArea.getText().isEmpty()) {
            String emptyFields = "Please enter :\n";
            if(stateMenuButton.getText().equals("Choose state")) {
                emptyFields += "task state\n";
            }
            if(descriptionArea.getText().isEmpty() || descriptionArea.getText().length() > 55) {
                emptyFields += "task description (length < 55 characters)\n";
            }
            showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", emptyFields);
        }
        else {
            int state = 0;
            switch(stateMenuButton.getText()) {
                case "Done":
                    state = 1;
                    break;
                case "Undone":
                    state = 2;
                    break;
            }
            if(jellyFacade.updateTask(task.getIdTask(), descriptionArea.getText(), state, step.getIdStep())){
                showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your task has been created");
                returnToStepPage();
            }
            else {
                showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "The creation has failed, please try again");

            }
        }
    }

    public void returnToStepPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/stepPage.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((StepPageController)loader.getController()).jellyFacade = jellyFacade;
        ((StepPageController)loader.getController()).project = project;
        ((StepPageController)loader.getController()).step = step;
        ((StepPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((StepPageController)loader.getController()).connectedUser = connectedUser;
        ((StepPageController)loader.getController()).board = board;
        ((StepPageController)loader.getController()).setScene(scene);
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

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
