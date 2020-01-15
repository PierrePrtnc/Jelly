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

    /**
     * creates a new board and inserts it into the database
     */
    public void createBoard() {
        if(boardNameField.getText().isEmpty() || subjectField.getText().isEmpty() || descriptionArea.getText().isEmpty()) {
            String emptyFields = "Please enter :\n";
            if(boardNameField.getText().isEmpty() || boardNameField.getText().length() > 55) {
                emptyFields += "board name (length < 55 characters)\n";
            }
            if(subjectField.getText().isEmpty() || subjectField.getText().length() > 55) {
                emptyFields += "board subject (length < 55 characters)\n";
            }
            if(descriptionArea.getText().isEmpty() || descriptionArea.getText().length() > 255) {
                emptyFields += "board description (length < 255 characters)\n";
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

    /**
     * calls the JavaFX component "NotificationUI" to display unread notifications
     * @see NotificationsController#emailUser
     * @see NotificationsController#currentUser
     * @see NotificationsController#setScene(Scene)
     */
    public void showUnreadNotifications() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/NotificationsUI.fxml"));
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
     * calls the JavaFX page "projectPage" to go back to the project page
     */
    public void returnToProjectPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/projectPage.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((ProjectPageController)loader.getController()).jellyFacade = jellyFacade;
        ((ProjectPageController)loader.getController()).connectedUser = connectedUser;
        ((ProjectPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((ProjectPageController)loader.getController()).project = project;
        ((ProjectPageController)loader.getController()).setScene(scene);
    }

    /**
     * sets the scene attribute
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


