package jelly.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.dao.MySqlDAOFactory;
import jelly.database.MySqlClient;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;
import jelly.project.Task;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private VBox taskVBox;

    @FXML
    protected Label notificationNumber;

    public void addNewTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/newTask.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((NewTaskController)loader.getController()).task = new Task();
        ((NewTaskController)loader.getController()).step = step;
        ((NewTaskController)loader.getController()).connectedUser = connectedUser;
        ((NewTaskController)loader.getController()).project = project;
        ((NewTaskController)loader.getController()).board = board;
        ((NewTaskController)loader.getController()).jellyFacade = jellyFacade;
        ((NewTaskController)loader.getController()).setScene(scene);
    }

    public void showGantt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/gantt/GanttView.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((GanttViewController)loader.getController()).connectedUser = connectedUser;
        ((GanttViewController)loader.getController()).jellyFacade = jellyFacade;
        ((GanttViewController)loader.getController()).project = project;
        ((GanttViewController)loader.getController()).notificationNumber.getScene().getWindow().setHeight(780);
        ((GanttViewController)loader.getController()).notificationNumber.getScene().getWindow().setWidth(1200);
        ((GanttViewController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((GanttViewController)loader.getController()).setScene(scene);
    }

    /**
     * calls the JavaFX page to update the displayed step
     * @param actionEvent
     */
    public void editStep(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/updateStep.fxml"));
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

    /**
     * deletes the displayed step from the database
     * @param actionEvent
     */
    public void deleteStep(ActionEvent actionEvent) {
        showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your board has been deleted");
        if(jellyFacade.deleteStep(step.getIdStep())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/boardPage.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((BoardPageController)loader.getController()).connectedUser = connectedUser;
                ((BoardPageController)loader.getController()).project = project;
                ((BoardPageController)loader.getController()).board = board;
                ((BoardPageController)loader.getController()).jellyFacade = jellyFacade;
                ((BoardPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                ((BoardPageController)loader.getController()).setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * calls the JavaFX page "projectPage" to go back to the project page
     */
    public void returnToBoard(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/boardPage.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((BoardPageController)loader.getController()).project = project;
        ((BoardPageController)loader.getController()).board = board;
        ((BoardPageController)loader.getController()).connectedUser = connectedUser;
        ((BoardPageController)loader.getController()).jellyFacade = jellyFacade;
        ((BoardPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((BoardPageController)loader.getController()).setScene(scene);
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
     * builds the page to display a step with tasks
     * @param scene
     */
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
            GridPane.setMargin(taskGP, new Insets(10, 10, 10, 10));
            taskGP.setPadding(new Insets(5, 5, 5, 5));
            taskGP.setHgap(10);
            CheckBox cb = new CheckBox();
            cb.setText("Task " + (i+1) + " : ");
            if (tasks.get(i).getTaskState() == 1){ cb.setSelected(true); }
            else{ cb.setDisable(true); }
            taskGP.add(cb,0, i);
            cb.setSelected(true);
            taskGP.add((new Label (tasks.get(i).getTaskDesc())), 1, i);
            Button editButton = new Button("Edit");
            taskGP.add(editButton, 2, i);
            int finalI = i;
            editButton.setOnAction(new EventHandler<>() {
                public void handle(ActionEvent event) {
                    MySqlClient sql = MySqlDAOFactory.getConnection();
                    Task task = new Task();
                    if (sql.connect()) {
                        String query = "select * from task where idTask = ?";
                        PreparedStatement pQuery = null;
                        try {
                            pQuery = sql.getDbConnect().prepareStatement(query);
                            pQuery.setInt(1, tasks.get(finalI).getIdTask());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        ResultSet res = null;
                        try {
                            assert pQuery != null;
                            res = pQuery.executeQuery();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        int idTask = 0;
                        int idStep = 0;
                        int stateTask = 0;
                        String descriptionTask = "";
                        while (true) {
                            try {
                                assert res != null;
                                if (!res.next()) break;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {
                                idTask = res.getInt(1);
                                descriptionTask = res.getString(2);
                                stateTask = res.getInt(3);
                                idStep = res.getInt(4);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        task = new Task(idTask, descriptionTask, stateTask, idStep);
                    }
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/updateTask.fxml"));
                        Parent root;
                        root = loader.load();
                        scene.setRoot(root);
                        ((UpdateTaskController) loader.getController()).connectedUser = connectedUser;
                        ((UpdateTaskController) loader.getController()).task = task;
                        ((UpdateTaskController) loader.getController()).step = step;
                        ((UpdateTaskController) loader.getController()).board = board;
                        ((UpdateTaskController) loader.getController()).project = project;
                        ((UpdateTaskController) loader.getController()).jellyFacade = jellyFacade;
                        ((UpdateTaskController) loader.getController()).descriptionArea.setText(task.getTaskDesc());
                        String state = "";
                        switch (task.getTaskState()) {
                            case 1:
                                state = "Done";
                                break;
                            case 2:
                                state = "Undone";
                                break;
                        }
                        ((UpdateTaskController) loader.getController()).stateMenuButton.setText(state);
                        ((UpdateTaskController) loader.getController()).setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            Button deleteButton = new Button("Delete");
            taskGP.add(deleteButton, 3, i);
            deleteButton.setOnAction(new EventHandler<>() {
                public void handle(ActionEvent event) {
                    MySqlClient sql = MySqlDAOFactory.getConnection();
                    Task task = new Task();
                    if (sql.connect()) {
                        String query = "select * from task where idTask = ?";
                        PreparedStatement pQuery = null;
                        try {
                            pQuery = sql.getDbConnect().prepareStatement(query);
                            pQuery.setInt(1, tasks.get(finalI).getIdTask());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        ResultSet res = null;
                        try {
                            assert pQuery != null;
                            res = pQuery.executeQuery();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        int idTask = 0;
                        int idStep = 0;
                        int stateTask = 0;
                        String descriptionTask = "";
                        while (true) {
                            try {
                                assert res != null;
                                if (!res.next()) break;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {
                                idTask = res.getInt(1);
                                descriptionTask = res.getString(2);
                                stateTask = res.getInt(3);
                                idStep = res.getInt(4);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        task = new Task(idTask, descriptionTask, stateTask, idStep);
                    }
                    showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your task has been deleted");
                    if (jellyFacade.deleteTask(task.getIdTask())) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/boardPage.fxml"));
                            Parent root;
                            root = loader.load();
                            scene.setRoot(root);
                            ((BoardPageController) loader.getController()).connectedUser = connectedUser;
                            ((BoardPageController) loader.getController()).project = project;
                            ((BoardPageController) loader.getController()).board = board;
                            ((BoardPageController) loader.getController()).jellyFacade = jellyFacade;
                            ((BoardPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                            ((BoardPageController) loader.getController()).setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            taskVBox.setPadding(new Insets(30, 0, 0, 0));
            taskVBox.getChildren().add(taskGP);
        }
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