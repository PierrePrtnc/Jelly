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
import jelly.project.Task;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

public class NewTaskController {

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
    private TextArea descriptionArea;

    @FXML
    private MenuButton stateMenuButton;

    /**
     * setters for the value of stateMenuButton
     * @param actionEvent
     */
    public void done(javafx.event.ActionEvent actionEvent) {
        stateMenuButton.setText("Done");
    }

    public void undone(javafx.event.ActionEvent actionEvent) {
        stateMenuButton.setText("Undone");
    }

    /**
     * calls the JavaFX page "stepPage" to go back to display the step display page
     */
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

    /**
     * creates and inserts a new task into the database with the provided fields
     * @throws SQLException
     */
    public void createTask() throws SQLException {
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
            if(jellyFacade.insertTask(descriptionArea.getText(), state, step.getIdStep()) != null){
                showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your task has been created");
                returnToStepPage();
            }
            else {
                showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "The creation has failed, please try again");

            }
        }
    }

    /**
     * calls the JavaFX component "NotificationUI" to display unread notifications
     * @see NotificationsController#emailUser
     * @see NotificationsController#currentUser
     * @see NotificationsController#setScene(Scene)
     */
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

    /**
     * sets the attribute scene
     * @param scene
     */
    public void setScene(Scene scene) { this.scene = scene; }

    /**
     * displays an alert
     * @param alertType     the type of alert to display
     * @param owner         the window part that owns the alert (where the alert should be displayed)
     * @param title         the title of the alert
     * @param message       the message of the alert
     */
    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
