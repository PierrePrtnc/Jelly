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
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateBoardController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    protected Board board;
    protected Project project;
    private Scene scene;

    @FXML
    private VBox window;

    @FXML
    protected Label boardNameLabel;

    @FXML
    protected TextField boardNameField;

    @FXML
    protected TextField subjectField;

    @FXML
    protected TextArea descriptionArea;

    public void returnToBoardPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/boardPage.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((BoardPageController)loader.getController()).project = project;
        ((BoardPageController)loader.getController()).connectedUser = connectedUser;
        ((BoardPageController)loader.getController()).board = board;
        ((BoardPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((BoardPageController)loader.getController()).jellyFacade = jellyFacade;
        ((BoardPageController)loader.getController()).setScene(scene);
    }

    /**
     * updates the current board in the database
     * @see jelly.dao.project.MySqlDAOBoard#updateBoard(int, int, String, String, String)
     * @throws IOException
     */
    public void updateBoard() throws IOException {
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
            if(jellyFacade.updateBoard(board.getIdBoard(), board.getIdProjectOfBoard(), boardNameField.getText(), subjectField.getText(), descriptionArea.getText())){
                showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your board has been updated");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/projectPage.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((ProjectPageController)loader.getController()).project = project;
                ((ProjectPageController)loader.getController()).connectedUser = connectedUser;
                ((ProjectPageController)loader.getController()).jellyFacade = jellyFacade;
                //((ProjectPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                ((ProjectPageController)loader.getController()).setScene(scene);
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
