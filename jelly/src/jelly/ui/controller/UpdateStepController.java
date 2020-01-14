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
import jelly.project.Task;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class UpdateStepController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    protected Step step;
    protected Board board;
    protected Project project;
    private Scene scene;

    @FXML
    private VBox window;

    @FXML
    protected Label stepNameLabel;

    @FXML
    protected TextField stepNameField;

    @FXML
    private Button cancelButton;

    @FXML
    protected TextArea descriptionArea;

    @FXML
    private Button updateButton;

    @FXML
    protected MenuButton stateMenuButton;

    @FXML
    protected MenuButton difficultyMenuButton;

    @FXML
    protected DatePicker startingDatePicker;

    @FXML
    protected DatePicker endingDatePicker;

    /**
     * setters for the startMenuButton and difficultyMenuButton to take possible values of a step state
     * @param actionEvent
     */
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

    /**
     * calls the JavaFX page to cancel the step page update
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
        ((StepPageController)loader.getController()).project = project;
        ((StepPageController)loader.getController()).step = step;
        ((StepPageController)loader.getController()).connectedUser = connectedUser;
        ((StepPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((StepPageController)loader.getController()).board = board;
        ((StepPageController)loader.getController()).jellyFacade = jellyFacade;
        ((StepPageController)loader.getController()).setScene(scene);
    }

    /**
     * updates a step into the database
     */
    public void updateStep() {
        if(stepNameField.getText().isEmpty() ||  stateMenuButton.getText().equals("Choose state") || difficultyMenuButton.getText().equals("Choose difficulty") || startingDatePicker.getValue() == null || endingDatePicker.getValue() == null || descriptionArea.getText().isEmpty()) {
            String emptyFields = "Please enter :\n";
            if(stepNameField.getText().isEmpty() || stepNameField.getText().length() > 55) {
                emptyFields += "step name (length < 55 characters)\n";
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
            if(descriptionArea.getText().isEmpty() || descriptionArea.getText().length() > 255) {
                emptyFields += "step description (length < 255 characters)\n";
            }
            showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", emptyFields);
        }
        else {
            Date startingDate = Date.from(startingDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endingDate = Date.from(endingDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            int state = 0;
            int difficulty = 0;
            switch (stateMenuButton.getText()) {
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
            switch (difficultyMenuButton.getText()) {
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
            if(jellyFacade.updateStep(step.getIdStep(), stepNameField.getText(), startingDate, endingDate, board.getIdBoard(), state, difficulty, descriptionArea.getText())){
                showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your step has been updated");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/boardPage.fxml"));
                Parent root;
                try {
                    root = loader.load();
                    this.scene.setRoot(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((BoardPageController)loader.getController()).project = project;
                ((BoardPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                ((BoardPageController)loader.getController()).connectedUser = connectedUser;
                ((BoardPageController)loader.getController()).board = board;
                ((BoardPageController)loader.getController()).jellyFacade = jellyFacade;
                ((BoardPageController)loader.getController()).setScene(scene);
            }
            else {
                showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "The update has failed, please try again");

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
     * sets the scene attribute
     * @param scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

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
