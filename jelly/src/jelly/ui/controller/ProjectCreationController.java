package jelly.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

public class ProjectCreationController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    private TextField projectNameInput;

    @FXML
    private DatePicker projectStartingDateDP;

    @FXML
    private DatePicker projectEndingDateDP;

    @FXML
    private Hyperlink projectGanttHyperLink;

    @FXML
    private Button createProjectButton;

    @FXML
    private Hyperlink homeHyperLink;

    @FXML
    private TextArea projectDescriptionInput;

    public void showHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/home.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((HomeController)loader.getController()).connectedUser = connectedUser;
        ((HomeController)loader.getController()).jellyFacade = jellyFacade;
        ((HomeController)loader.getController()).setScene(scene);
    }

    public void createGanttDiagram() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/gantt/GanttCreation.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((GanttCreationController)loader.getController()).connectedUser = connectedUser;
        ((GanttCreationController)loader.getController()).jellyFacade = jellyFacade;
        ((GanttCreationController)loader.getController()).setScene(scene);
    }

    //TODO Améliorer la sécurité : restreindre le nombre de caractères pour le nom / description
    // Check si les dates sont cohérentes
    public void createProject() throws IOException {
        if (this.projectNameInput.getText().isEmpty())
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the name of the project");
        else if(this.projectDescriptionInput.getText().isEmpty())
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the description of the project");
        else if(this.projectStartingDateDP.getValue() == null)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please choose a starting date");
        else if(this.projectEndingDateDP.getValue() == null)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please choose an ending date");
        else {
            Date startingDate = Date.from(projectStartingDateDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endingDate = Date.from(projectEndingDateDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (jellyFacade.insertProject(projectNameInput.getText(), projectDescriptionInput.getText(), startingDate, endingDate, connectedUser)) {
                showAlert(Alert.AlertType.INFORMATION, projectNameInput.getScene().getWindow(), "Success", "Insertion effectuée !");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/home.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((HomeController)loader.getController()).connectedUser = connectedUser;
                ((HomeController)loader.getController()).jellyFacade = jellyFacade;
                ((HomeController)loader.getController()).setScene(scene);
            }
            else
                showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "An error occured, please try again.");
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
