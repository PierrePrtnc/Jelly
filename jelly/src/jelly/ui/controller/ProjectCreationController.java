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
import java.util.concurrent.TimeUnit;

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
	protected Label notificationNumber;

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
		((HomeController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((HomeController)loader.getController()).setScene(scene);
    }

    public void createGanttDiagram() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/gantt/GanttCreation.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((GanttCreationController)loader.getController()).setScene(scene);
        ((GanttCreationController)loader.getController()).connectedUser = connectedUser;
        ((GanttCreationController)loader.getController()).jellyFacade = jellyFacade;
		((GanttCreationController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
    }

    public void createProject() throws IOException {
        if (this.projectNameInput.getText().isEmpty() || this.projectNameInput.getText().length() > 55)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the name of the project (length < 55 characters)");
        else if(this.projectDescriptionInput.getText().isEmpty())
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the description of the project (length < 55 characters)");
        else if(this.projectStartingDateDP.getValue() == null)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please choose a starting date");
        else if(this.projectEndingDateDP.getValue() == null)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please choose an ending date");
        else {
            Date startingDate = Date.from(projectStartingDateDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endingDate = Date.from(projectEndingDateDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            long dateDiff = endingDate.getTime() - startingDate.getTime();
            if (dateDiff < 0)
                showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please select appropriate dates.");
            else {
                if (jellyFacade.insertProject(projectNameInput.getText(), projectDescriptionInput.getText(), startingDate, endingDate, connectedUser) != null) {
                    showAlert(Alert.AlertType.INFORMATION, projectNameInput.getScene().getWindow(), "Success", "Project created !");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/home.fxml"));
                    Parent root;
                    root = loader.load();
                    this.scene.setRoot(root);
                    ((HomeController) loader.getController()).connectedUser = connectedUser;
                    ((HomeController) loader.getController()).jellyFacade = jellyFacade;
                    ((HomeController) loader.getController()).setScene(scene);
                } else
                    showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "An error occured, please try again.");
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
