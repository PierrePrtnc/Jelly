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
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;
import jelly.project.Task;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class StepPageController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    protected Step step;
    protected Board board;
    protected Project project;
    private Scene scene;

    @FXML
    private Label stepLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private VBox stepDescriptionVBox;

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
        stepLabel.setText("Step : " + step.getStepName());
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
}