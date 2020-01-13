package jelly.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.notification.Notification;
import jelly.project.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class InvitationsController {
    protected User connectedUser;
    protected JellyFacade jellyFacade = new JellyFacade();
    protected Project project;
    private Scene scene;
    public boolean invitation;

    @FXML
    private VBox window;

    @FXML
    public TextField userMailField;

    @FXML
    public Button addUserButton;

    @FXML
    public Hyperlink backToProjectHL;

    @FXML
    public GridPane mailGridPane;


    public void setScene(Scene scene) { this.scene = scene; }

    public void sendInvite() {
        System.out.println("START HANDLE INVITE FROM INVITATIONS CONTROLLER");
        System.out.println("USER MAIL FIELD 1"+userMailField.getText());
        if (userMailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "Please provide an mail address.");
        }
        Collaborator c = new Collaborator(project, connectedUser);
        Collection<User> users = new ArrayList<>();
        users.add(jellyFacade.getUser(userMailField.getText()));
        if (jellyFacade.insertNotification(c, users, "invitation", "invite")) {
            try {
                showAlert(Alert.AlertType.INFORMATION, userMailField.getScene().getWindow(), "Success", "User invited.");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/projectPage.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((ProjectPageController) loader.getController()).connectedUser = connectedUser;
                ((ProjectPageController) loader.getController()).jellyFacade = jellyFacade;
                ((ProjectPageController) loader.getController()).project = project;
                ((ProjectPageController) loader.getController()).setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void handleMouseEntered() {
        this.scene.setCursor(Cursor.HAND);
        addUserButton.setStyle("#ACD6FX");

    }

    public void handleMouseExited() {
        this.scene.setCursor(Cursor.DEFAULT);
        addUserButton.setStyle("#ACD6FA");
    }

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
        ((ProjectPageController)loader.getController()).connectedUser = connectedUser;
        ((ProjectPageController)loader.getController()).project = project;
        ((ProjectPageController)loader.getController()).setScene(scene);
    }

    public void addUserToInvite(ActionEvent actionEvent) {
    }
}
