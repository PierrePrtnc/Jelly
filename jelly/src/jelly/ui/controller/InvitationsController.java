package jelly.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.project.Project;

import java.io.IOException;

public class InvitationsController {
    public User connectedUser;
    public JellyFacade jellyFacade;
    public Project project;
    public Scene scene;

    @FXML
    public TextField userMailField;


    public void setScene(Scene scene) {
    }

    public void addCollaborator() {
        Collaborator c = jellyFacade.insertCollaborator(project, jellyFacade.getUser(userMailField.getText()));
        if (c != null) {
            try {
                showAlert(Alert.AlertType.INFORMATION, userMailField.getScene().getWindow(), "Success", "Invitation sent.");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/projectPage.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((ProjectPageController) loader.getController()).connectedUser = connectedUser;
                ((ProjectPageController) loader.getController()).jellyFacade = jellyFacade;
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
}
