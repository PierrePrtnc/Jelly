package jelly.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Project;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

public class UpdateProjectController {
    protected Project project;
    protected JellyFacade jellyFacade;
    protected User connectedUser;
    private Scene scene;

    @FXML
    public TextField projectNameField;

    @FXML
    public TextArea projectDescriptionText;

    @FXML
    public DatePicker initialDatePicker;

    @FXML
    public DatePicker finalDatePicker;

    @FXML
    public Button editProjectButton;

    /**
     * sets the scene attribute
     * @param scene
     */
    public void setScene(Scene scene) { this.scene = scene; }

    /**
     * calls the JavaFX page "projectPage"
     */
    public void returnToProject() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/projectPage.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((ProjectPageController)loader.getController()).jellyFacade = jellyFacade;
        ((ProjectPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((ProjectPageController)loader.getController()).connectedUser = connectedUser;
        ((ProjectPageController)loader.getController()).project = project;
        ((ProjectPageController)loader.getController()).setScene(scene);
    }

    /**
     * updates the current project in the database
     */
    public void handleEdition() {
        java.util.Date startingDate;
        java.util.Date endingDate;
        if (initialDatePicker.getValue() == null) {
            startingDate = project.getInitialDate();
        } else {
            startingDate = java.util.Date.from(initialDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (finalDatePicker.getValue() == null) {
            endingDate = project.getFinalDate();
        } else {
            endingDate = java.util.Date.from(finalDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (jellyFacade.updateProject(project.getIdProject(), projectNameField.getText(), projectDescriptionText.getText(), startingDate, endingDate)) {
            try {
                showAlert(Alert.AlertType.INFORMATION, editProjectButton.getScene().getWindow(), "Success", "Project updated.");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/projectPage.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((ProjectPageController) loader.getController()).project = project;
                ((ProjectPageController) loader.getController()).connectedUser = connectedUser;
                ((ProjectPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                ((ProjectPageController) loader.getController()).jellyFacade = jellyFacade;
                ((ProjectPageController) loader.getController()).setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    /**
     * modifies the display of a button when the mouse hovers it
     */
    public void handleMouseEntered() {
        this.scene.setCursor(Cursor.HAND);
        editProjectButton.setStyle("#ACD6FX");

    }

    public void handleMouseExited() {
        this.scene.setCursor(Cursor.DEFAULT);
        editProjectButton.setStyle("#ACD6FA");
    }
}
