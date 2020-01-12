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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class StepPageController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    protected Step step;
    protected Board board;
    protected Project project;
    private Scene scene;

    @FXML
    private VBox window;

    @FXML
    private Label stepLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addNewTaskButton;

    @FXML
    private CheckBox allTasksCheckBox;

    @FXML
    private CheckBox undoneCheckBox;

    @FXML
    private CheckBox doneCheckBox;

    @FXML
    private MenuButton sortByMenuButton;

    @FXML
    private VBox taskVBox;

    public void editStep(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/updateStep.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((UpdateStepController)loader.getController()).board = board;
        ((UpdateStepController)loader.getController()).step = step;
        ((UpdateStepController)loader.getController()).connectedUser = connectedUser;
        ((UpdateStepController)loader.getController()).project = project;
        ((UpdateStepController)loader.getController()).jellyFacade = jellyFacade;
        ((UpdateStepController)loader.getController()).stepNameLabel.setText(step.getStepName());
        ((UpdateStepController)loader.getController()).stepNameField.setText(step.getStepName());
        String state = "";
        switch(step.getStepState()) {
            case 1:
                state = "To do";
                break;
            case 2:
                state = "In progress";
                break;
            case 3:
                state = "Finished";
                break;
            case 4:
                state = "Re do";
                break;
        }
        ((UpdateStepController)loader.getController()).stateMenuButton.setText(state);
        String difficulty = "";
        switch(step.getStepDifficulty()) {
            case 1:
                difficulty = "Easy";
                break;
            case 2:
                difficulty = "Medium";
                break;
            case 3:
                difficulty = "Hard";
                break;
        }
        ((UpdateStepController)loader.getController()).difficultyMenuButton.setText(difficulty);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        ((UpdateStepController)loader.getController()).startingDatePicker.setPromptText(df.format(step.getInitialDate()));
        ((UpdateStepController)loader.getController()).endingDatePicker.setPromptText(df.format(step.getFinalDate()));
        ((UpdateStepController)loader.getController()).descriptionArea.setText(step.getStepDesc());
        ((UpdateStepController)loader.getController()).setScene(scene);
    }

    public void deleteStep(ActionEvent actionEvent) {
        showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your board has been deleted");
        if(jellyFacade.deleteStep(step.getIdStep())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/boardPage.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((BoardPageController)loader.getController()).connectedUser = connectedUser;
                ((BoardPageController)loader.getController()).project = project;
                ((BoardPageController)loader.getController()).board = board;
                ((BoardPageController)loader.getController()).jellyFacade = jellyFacade;
                ((BoardPageController)loader.getController()).setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void returnToBoard(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/boardPage.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((BoardPageController)loader.getController()).project = project;
        ((BoardPageController)loader.getController()).board = board;
        ((BoardPageController)loader.getController()).connectedUser = connectedUser;
        ((BoardPageController)loader.getController()).jellyFacade = jellyFacade;
        //((ProjectPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((BoardPageController)loader.getController()).setScene(scene);
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

    public void setScene(Scene scene) {
        List<Task> tasks = jellyFacade.getAllTasksByStep(step.idStep);
        String state = "";
        switch(step.getStepState()) {
            case 1:
                state = "To do";
                break;
            case 2:
                state = "In progress";
                break;
            case 3:
                state = "Finished";
                break;
            case 4:
                state = "Re do";
                break;
        }
        String difficulty = "";
        switch(step.getStepDifficulty()) {
            case 1:
                difficulty = "Easy";
                break;
            case 2:
                difficulty = "Medium";
                break;
            case 3:
                difficulty = "Hard";
                break;
        }
        stepLabel.setText("Step : " + step.getStepName() + " [" + state + "]" + " [" + difficulty + "]");
        for (int i = 0; i < tasks.size(); i++) {
            GridPane taskGP = new GridPane();
            taskGP.add(new CheckBox("Task " + (i+1) + " : "),0, i);
            taskGP.add((new Label (tasks.get(i).getTaskDesc())), 1, i);
            taskGP.add(new Button("Edit"), 2, i);
            taskGP.add(new Button("Delete"), 3, i);
            taskVBox.getChildren().add(taskGP);
        }
        this.scene = scene;
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