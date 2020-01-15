package jelly.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import jelly.dao.MySqlDAOFactory;
import jelly.database.MySqlClient;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class BoardPageController {

    protected JellyFacade jellyFacade;
    protected Project project;
    protected User connectedUser;
    protected Board board;
    private Scene scene;

    @FXML
    private VBox window;

    @FXML
    private Label boardLabel;

    @FXML
    private VBox boardDescriptionVBox;

    @FXML
    private GridPane stepGripPane;

    @FXML
    protected Label notificationNumber;

    /**
     * Displays the Gantt JavaFX page
     * @throws IOException
     */
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
     * calls the JavaFX component to go back to the "projectPage"
     * @param actionEvent
     *
     * @see ProjectPageController#project
     * @see ProjectPageController#connectedUser
     * @see ProjectPageController#jellyFacade
     * @see ProjectPageController#setScene(Scene)
     *
     *
     * @throws IOException
     */
    public void returnToProject(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/projectPage.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((ProjectPageController)loader.getController()).project = project;
        ((ProjectPageController)loader.getController()).connectedUser = connectedUser;
        ((ProjectPageController)loader.getController()).jellyFacade = jellyFacade;
        ((ProjectPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((ProjectPageController)loader.getController()).setScene(scene);
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
     * calls the JavaFX component "updateBoard" to display the edit page for a board
     * @param actionEvent
     *
     * @see UpdateBoardController#board
     * @see UpdateBoardController#connectedUser
     * @see UpdateBoardController#project
     * @see UpdateBoardController#jellyFacade
     * @see UpdateBoardController#boardNameLabel
     * @see UpdateBoardController#boardNameField
     * @see UpdateBoardController#subjectField
     * @see UpdateBoardController#descriptionArea
     * @see UpdateBoardController#setScene(Scene) 
     */
    public void editBoard(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/updateBoard.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((UpdateBoardController)loader.getController()).board = board;
        ((UpdateBoardController)loader.getController()).connectedUser = connectedUser;
        ((UpdateBoardController)loader.getController()).project = project;
        ((UpdateBoardController)loader.getController()).jellyFacade = jellyFacade;
        ((UpdateBoardController)loader.getController()).boardNameLabel.setText(board.getBoardName());
        ((UpdateBoardController)loader.getController()).boardNameField.setText(board.getBoardName());
        ((UpdateBoardController)loader.getController()).subjectField.setText(board.getSubjectBoard());
        ((UpdateBoardController)loader.getController()).descriptionArea.setText(board.getDescriptionBoard());
        ((UpdateBoardController)loader.getController()).setScene(scene);
    }

    /**
     * activates the deletion of a given board and upon successful deletion, displays the projectPage
     * 
     * @see jelly.dao.project.MySqlDAOBoard#deleteBoard(int) 
     * 
     * @see ProjectPageController#connectedUser
     * @see ProjectPageController#project
     * @see ProjectPageController#jellyFacade
     * @see ProjectPageController#setScene(Scene)
     * 
     * @param actionEvent
     */
    public void deleteBoard(ActionEvent actionEvent) {
        showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your board has been deleted");
        if(jellyFacade.deleteBoard(board.getIdBoard())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/projectPage.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((ProjectPageController)loader.getController()).connectedUser = connectedUser;
                ((ProjectPageController)loader.getController()).project = project;
                ((ProjectPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                ((ProjectPageController)loader.getController()).jellyFacade = jellyFacade;
                ((ProjectPageController)loader.getController()).setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * activates the mechanism to add a step
     *
     * @see NewStepController#step
     * @see NewStepController#connectedUser
     * @see NewStepController#project
     * @see NewStepController#board
     * @see NewStepController#jellyFacade
     * @see NewStepController#setScene(Scene)
     *
     * @param actionEvent
     */
    public void addNewStep(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/newStep.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((NewStepController)loader.getController()).step = new Step();
        ((NewStepController)loader.getController()).connectedUser = connectedUser;
        ((NewStepController)loader.getController()).project = project;
        ((NewStepController)loader.getController()).board = board;
        ((NewStepController)loader.getController()).jellyFacade = jellyFacade;
        ((NewStepController)loader.getController()).setScene(scene);
    }

    /**
     * sets the scene of the board page
     * @param scene
     */
    public void setScene(Scene scene) {
        List<Step> steps = jellyFacade.getAllStepsByBoard(board.getIdBoard());
        boardLabel.setText("Board : " + board.getBoardName() + " : " + board.getSubjectBoard());
        boardDescriptionVBox.getChildren().add(new Label("Description of the board : " + board.getDescriptionBoard()));
        stepGripPane.setPrefHeight(100);
        stepGripPane.setPrefWidth(450);
        int j = 0;
        int k = 0;
        if (steps != null && !steps.isEmpty()){
            for (int i = 0; i < steps.size(); i++) {
                if (j == 3) {
                    j = 0;
                    k++;
                }
                VBox vbox = new VBox();
                vbox.getChildren().add(new Label(steps.get(i).getStepName()));
                switch(steps.get(i).getStepState()) {
                    case 1:
                        vbox.getChildren().add(new Label("To do"));
                        break;
                    case 2:
                        vbox.getChildren().add(new Label("In progress"));
                        break;
                    case 3:
                        vbox.getChildren().add(new Label("Finished"));
                        break;
                    case 4:
                        vbox.getChildren().add(new Label("Re do"));
                        break;
                }
                switch(steps.get(i).getStepDifficulty()) {
                    case 1:
                        vbox.getChildren().add(new Label("Easy"));
                        break;
                    case 2:
                        vbox.getChildren().add(new Label("Medium"));
                        break;
                    case 3:
                        vbox.getChildren().add(new Label("Hard"));
                        break;
                }
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                vbox.getChildren().add(new Label("Starting date : " + df.format(steps.get(i).getInitialDate())));
                vbox.getChildren().add(new Label("Expected ending date : " + df.format(steps.get(i).getFinalDate())));
                Button myButton = new Button("View");
                vbox.getChildren().add(myButton);
                int finalI = i;
                myButton.setOnAction(new EventHandler<>() {
                    public void handle(ActionEvent event) {
                        MySqlClient sql = MySqlDAOFactory.getConnection();
                        Step step = new Step();
                        if (sql.connect()) {
                            String query = "select * from step where idStep = ?";
                            PreparedStatement pQuery = null;
                            try {
                                pQuery = sql.getDbConnect().prepareStatement(query);
                                pQuery.setInt(1, steps.get(finalI).getIdStep());
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
                            int idStep = 0;
                            String nameStep = "";
                            Date initialDateStep = new Date(0, 0, 0);
                            Date finalDateStep = new Date(0, 0, 0);
                            int idBoard = 0;
                            int stateStep = 0;
                            int difficultyStep = 0;
                            String descriptionStep = "";
                            while (true) {
                                try {
                                    assert res != null;
                                    if (!res.next()) break;
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    idStep = res.getInt(1);
                                    nameStep = res.getString(2);
                                    initialDateStep = res.getDate(3);
                                    finalDateStep = res.getDate(4);
                                    idBoard = res.getInt(5);
                                    stateStep = res.getInt(6);
                                    difficultyStep = res.getInt(7);
                                    descriptionStep = res.getString(8);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            step = new Step(idStep, nameStep, initialDateStep, finalDateStep, idBoard, stateStep, difficultyStep, descriptionStep);
                        }
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/stepPage.fxml"));
                            Parent root;
                            root = loader.load();
                            scene.setRoot(root);
                            ((StepPageController) loader.getController()).connectedUser = connectedUser;
                            ((StepPageController) loader.getController()).step = step;
                            ((StepPageController) loader.getController()).board = board;
                            ((StepPageController) loader.getController()).project = project;
                            ((StepPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                            ((StepPageController) loader.getController()).jellyFacade = jellyFacade;
                            ((StepPageController) loader.getController()).setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                stepGripPane.add(vbox, j, k);
                j++;
            }
        }
        else{
            VBox vbox = new VBox();
            vbox.getChildren().add(new Label("No steps in this board."));
            stepGripPane.add(vbox, 0,0);
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