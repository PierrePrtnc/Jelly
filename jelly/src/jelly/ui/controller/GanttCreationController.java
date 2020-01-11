package jelly.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Board;
import jelly.project.Project;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class GanttCreationController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    private Scene scene;

    @FXML
    private TextField ganttNameInput;

    @FXML
    private TextArea projectDescriptionInput;

    @FXML
    private DatePicker ganttDiagramStartingDate;

    @FXML
    private DatePicker ganttDiagramEndingDate;

    @FXML
    protected TextField projectNameInput;

    @FXML
    private Button createProjectButton;

    @FXML
    private Button cancelButton;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void showGanttEdit() throws IOException, ParseException {
        if (this.projectNameInput.getText().isEmpty())
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the name of the project");
        else if(this.projectDescriptionInput.getText().isEmpty())
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the description of the project");
        else if(this.ganttDiagramStartingDate.getValue() == null)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please choose a starting date");
        else if(this.ganttDiagramEndingDate.getValue() == null)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please choose an ending date");
        else if (this.ganttNameInput.getText().isEmpty())
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the name of the Board");
        else {
            Date startingDate = Date.from(ganttDiagramStartingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endingDate = Date.from(ganttDiagramEndingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (jellyFacade.insertProject(projectNameInput.getText(), projectDescriptionInput.getText(), startingDate, endingDate, connectedUser)) {
                Project project = new Project(projectNameInput.getText(), projectDescriptionInput.getText(), startingDate, endingDate);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/gantt/GanttEdit.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((GanttEditController)loader.getController()).connectedUser = connectedUser;
                ((GanttEditController)loader.getController()).jellyFacade = jellyFacade;
                ((GanttEditController)loader.getController()).project = project;
                ((GanttEditController)loader.getController()).setScene(scene);

            }
            else
                showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "An error occured, please try again.");
        }
    }

    public void cancelCreation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/ProjectCreation.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((ProjectCreationController)loader.getController()).connectedUser = connectedUser;
        ((ProjectCreationController)loader.getController()).jellyFacade = jellyFacade;
        ((ProjectCreationController)loader.getController()).setScene(scene);
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
