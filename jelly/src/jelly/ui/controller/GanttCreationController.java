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
    protected TextField subjectGantt;

    @FXML
    private Button createProjectButton;

    @FXML
    private Button cancelButton;
    
    @FXML protected Label notificationNumber;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

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
    
    public void showGanttEdit() throws IOException, ParseException {
        if (this.projectNameInput.getText().isEmpty() || this.projectNameInput.getText().length() > 55)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the name of the project (length < 55 characters)");
        else if(this.projectDescriptionInput.getText().isEmpty() || this.projectDescriptionInput.getText().length() > 55)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the description of the project (length < 55 characters)");
        else if(this.ganttDiagramStartingDate.getValue() == null)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please choose a starting date");
        else if(this.ganttDiagramEndingDate.getValue() == null)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please choose an ending date");
        else if (this.ganttNameInput.getText().isEmpty() || this.ganttNameInput.getText().length() > 55)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the name of the Board (length < 55 characters)");
        else if (this.subjectGantt.getText().isEmpty() || this.subjectGantt.getText().length() > 55)
            showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please enter the subject of the Board (length < 55 characters)");
        else {
            Date startingDate = Date.from(ganttDiagramStartingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endingDate = Date.from(ganttDiagramEndingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            long dateDiff = endingDate.getTime() - startingDate.getTime();
            if (dateDiff < 0)
                showAlert(Alert.AlertType.ERROR, projectNameInput.getScene().getWindow(), "Error", "Please select appropriate dates.");
            else {
                Project project = new Project(projectNameInput.getText(), projectDescriptionInput.getText(), startingDate, endingDate);
                project.addBoard(new Board(ganttNameInput.getText(), subjectGantt.getText(), "default board"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/gantt/GanttEdit.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((GanttEditController) loader.getController()).connectedUser = connectedUser;
                ((GanttEditController) loader.getController()).jellyFacade = jellyFacade;
                ((GanttEditController) loader.getController()).project = project;
                ((GanttEditController) loader.getController()).setScene(scene);
                ((GanttEditController) loader.getController()).notificationNumber.setText("" + jellyFacade.getUnreadNotificationList(connectedUser).size());
            }
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
		((ProjectCreationController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());

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