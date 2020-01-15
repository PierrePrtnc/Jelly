package jelly.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProjectPageController {

    protected JellyFacade jellyFacade;
    protected Project project;
    protected User connectedUser;
    private Scene scene;

    @FXML
    private Label projectLabel;

    @FXML
    protected VBox boardDescriptionVBox;

    @FXML
    private GridPane boardGripPane;

    @FXML
    private Button projectDeletion;

    @FXML
    protected Label notificationNumber;

    public void showMembers(){

    }

    /**
     * calls the JavaFX page "GanttView" of a given project for a given user
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

    public void leaveProject(){

    }

    /**
     * calls the JavaFX component "home" to display the home page
     * @see HomeController#connectedUser
     * @see HomeController#jellyFacade
     * @see HomeController#notificationNumber
     * @see HomeController#setScene(Scene)
     * @throws IOException
     */
    public void returnHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/home.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((HomeController)loader.getController()).connectedUser = connectedUser;
        ((HomeController)loader.getController()).jellyFacade = jellyFacade;
        ((HomeController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((HomeController)loader.getController()).setScene(scene);
    }

    /**
     * calls the JavaFX page "newBoard" to create a new board
     * @param actionEvent
     */
    public void addNewBoard(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/newBoard.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((NewBoardController)loader.getController()).board = new Board();
        ((NewBoardController)loader.getController()).project = project;
        ((NewBoardController)loader.getController()).jellyFacade = jellyFacade;
        ((NewBoardController)loader.getController()).connectedUser = connectedUser;

        ((NewBoardController)loader.getController()).setScene(scene);
    }

    /**
     * sets the scene attribute
     * builds the project page with a project description, the initial date of the project and the ending date of the project
     * @param scene
     */
    public void setScene(Scene scene) {
        List<Board> boards = jellyFacade.getAllBoardsByProject(project.getIdProject());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        projectLabel.setText("Project : "+ project.getProjectName());
        boardDescriptionVBox.getChildren().add(new Label("Description of the project : " + project.getProjectDescription()));
        boardDescriptionVBox.getChildren().add(new Label("Start date project : " + project.getInitialDate()));
        boardDescriptionVBox.getChildren().add(new Label("Expected end date project : " + project.getFinalDate()));
        boardGripPane.setPrefHeight(460);
        boardGripPane.setPrefWidth(600);
        int j = 0;
        int k = 0;
        if (!boards.equals(null) && !boards.isEmpty()){
            for (int i = 0; i < boards.size(); i++) {
                if (j == 3) {
                    j = 0;
                    k++;
                }
                List<Step> steps = jellyFacade.getAllStepsByBoard(boards.get(i).getIdBoard());
                project.addStepGanttDiagram(steps);
                double accomplishmentPercent = 0;
                double stepFinished = 0;
                double totalStep = 0;
                VBox vbox = new VBox();
                vbox.getChildren().add(new Label("Board " + (i+1)));
                vbox.getChildren().add(new Label("Name : " + boards.get(i).getBoardName()));
                for (int l = 1; l < steps.size()+1; l++) {
                    switch(steps.get(l-1).getStepState()) {
                        case 1:
                            vbox.getChildren().add(new Label("Step " + l + " - To do"));
                            totalStep = totalStep + 1;
                            break;
                        case 2:
                            vbox.getChildren().add(new Label("Step " + l + " - In progress"));
                            totalStep = totalStep + 1;
                            break;
                        case 3:
                            vbox.getChildren().add(new Label("Step " + l + " - Finished"));
                            stepFinished = stepFinished + 1;
                            totalStep = totalStep + 1;
                            break;
                        case 4:
                            vbox.getChildren().add(new Label("Step " + l + " - Re do"));
                            totalStep = totalStep + 1;
                            break;
                    }
                }
                if (steps.isEmpty()){
                    vbox.getChildren().add(new Label("This board has no steps "));
                }
                accomplishmentPercent = ((stepFinished / totalStep) * 100);
                if (!Double.isNaN(accomplishmentPercent)){
                    vbox.getChildren().add(new Label("Accomplishment : " + round(accomplishmentPercent, 2)+"%"));
                }
                else {vbox.getChildren().add(new Label("Accomplishment can't be determined "));}
                Button myButton = new Button("View");
                vbox.getChildren().add(myButton);
                int finalI = i;
                myButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        MySqlClient sql = MySqlDAOFactory.getConnection();
                        Board board = new Board();
                        if(sql.connect()) {
                            String query = "select * from board where idBoard = ?";
                            PreparedStatement pQuery = null;
                            try {
                                pQuery = sql.getDbConnect().prepareStatement(query);
                                pQuery.setInt(1, boards.get(finalI).getIdBoard());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            ResultSet res = null;
                            try {
                                res = pQuery.executeQuery();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            int idBoard = 0;
                            int idProject = 0;
                            String nameBoard = "";
                            String descriptionBoard = "";
                            String subjectBoard = "";
                            while (true) {
                                try {
                                    if (!res.next()) break;
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    idBoard = res.getInt(1);
                                    nameBoard = res.getString(2);
                                    idProject = res.getInt(3);
                                    descriptionBoard = res.getString(4);
                                    subjectBoard = res.getString(5);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            board = new Board(idBoard, idProject, nameBoard, descriptionBoard, subjectBoard);
                        }
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/boardPage.fxml"));
                            Parent root;
                            root = loader.load();
                            scene.setRoot(root);
                            ((BoardPageController)loader.getController()).connectedUser = connectedUser;
                            ((BoardPageController)loader.getController()).board = board;
                            ((BoardPageController)loader.getController()).project = project;
                            ((BoardPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                            ((BoardPageController)loader.getController()).jellyFacade = jellyFacade;
                            ((BoardPageController)loader.getController()).setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                boardGripPane.add(vbox, j, k);
                j++;
            }
        }
        else{
            VBox vbox = new VBox();
            vbox.getChildren().add(new Label("No boards in this project"));
            boardGripPane.add(vbox, 0,0);
        }
        this.scene = scene;
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
     * calls the JavaFX component "home" to display the home page
     * @see HomeController#connectedUser
     * @see HomeController#jellyFacade
     * @see HomeController#notificationNumber
     * @see HomeController#setScene(Scene)
     * @throws IOException
     */
    public void returnHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/home.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((HomeController)loader.getController()).connectedUser = jellyFacade.getUser(connectedUser.getMailUser());
        ((HomeController)loader.getController()).jellyFacade = jellyFacade;
        ((HomeController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(jellyFacade.getUser(connectedUser.getMailUser())).size());
        ((HomeController)loader.getController()).setScene(scene);
    }

    /**
     * rounds up a double at a given decimal
     * @param value
     * @param places
     * @return  the rounded up value
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * calls the JavaFX page to edit a project
     */
    public void editProject() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/editProjectPage.fxml"));
            Parent root;
            root = loader.load();
            this.scene.setRoot(root);
            ((UpdateProjectController) loader.getController()).project = project;
            ((UpdateProjectController) loader.getController()).connectedUser = connectedUser;
            ((UpdateProjectController) loader.getController()).jellyFacade = jellyFacade;
            ((UpdateProjectController) loader.getController()).projectNameField.setText(project.getProjectName());
            ((UpdateProjectController) loader.getController()).projectDescriptionText.setText(project.getProjectDescription());
            ((UpdateProjectController) loader.getController()).setScene(scene);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * deletes a given project from the database
     */
    public void deleteProject() {
        if (jellyFacade.deleteProject(project.getIdProject())) {
            try {
                showAlert(Alert.AlertType.INFORMATION, projectDeletion.getScene().getWindow(), "Success", "Project deleted.");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/home.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((HomeController) loader.getController()).connectedUser = connectedUser;
                ((HomeController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                ((HomeController) loader.getController()).jellyFacade = jellyFacade;
                ((HomeController) loader.getController()).setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    /**
     * calls the JavaFX page that contains the form to invite a user to the project
     */
    public void handleInvite() {
        System.out.println("START HANDLE INVITE");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/inviteUserPage.fxml"));
            Parent root;
            root = loader.load();
            this.scene.setRoot(root);
            ((InvitationsController) loader.getController()).connectedUser = connectedUser;
            ((InvitationsController) loader.getController()).project = project;
            //((InvitationsController) loader.getController()).invitation = true;
            ((InvitationsController) loader.getController()).setScene(scene);
            System.out.println("PROJECT VALUE IN PROJECT PAGE HANDLE INVITE "+project.getProjectName());
            System.out.println("END HANDLE INVITE");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
