package jelly.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import jelly.JellyFacade;
import jelly.User;
import jelly.dao.MySqlDAOFactory;
import jelly.database.MySqlClient;
import jelly.project.Project;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HomeController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    @FXML
    private GridPane projectsGridPane;

    @FXML
    private Label welcomeLabel;
    
	@FXML
	protected Label notificationNumber;

    private Scene scene;

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
     * calls the JavaFX page "updateInformationUI" to update the user information
     *
     * @param actionEvent
     */
    public void showPersonalInfo(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/updateInformationUI.fxml"));
                Parent root;
                root = loader.load();
                this.scene.setRoot(root);
                ((UpdateAccountInformationController)loader.getController()).setScene(scene);
                ((UpdateAccountInformationController)loader.getController()).connectedUser = connectedUser;
                ((UpdateAccountInformationController)loader.getController()).firstNameField.setText(connectedUser.getFirstNameUser());
                ((UpdateAccountInformationController)loader.getController()).lastNameField.setText(connectedUser.getLastNameUser());
                ((UpdateAccountInformationController)loader.getController()).pseudoField.setText(connectedUser.getPseudoUser());
                ((UpdateAccountInformationController)loader.getController()).emailField.setText(connectedUser.getMailUser());
				((UpdateAccountInformationController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());



            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * calls the JavaFX page "ProjectCreation" to create a new project
     * @param actionEvent
     */
    public void showCreateProject(ActionEvent actionEvent) {
        if(!(connectedUser.equals(null)||connectedUser.getFirstNameUser().contentEquals(""))) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/ProjectCreation.fxml"));
            Parent root;
            try {
                root = loader.load();
                this.scene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
            ((ProjectCreationController)loader.getController()).connectedUser = connectedUser;
            ((ProjectCreationController)loader.getController()).jellyFacade = jellyFacade;
			((ProjectCreationController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());

            ((ProjectCreationController)loader.getController()).setScene(scene);
        }

    }

    /**
     * logs the user off and displays the login page "loginUI"
     * @param actionEvent
     */
    public void logOff(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/loginUI.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ((LoginController)loader.getController()).jellyFacade = jellyFacade;
        ((LoginController)loader.getController()).setScene(scene);
    }

    /**
     * sets the scene attribute
     * builds the home page with the projects of the connected user
     * @param scene
     */
    public void setScene(Scene scene) {
        welcomeLabel.getScene().getWindow().setWidth(850);
        welcomeLabel.getScene().getWindow().setHeight(540);
        List<Project> projects = jellyFacade.getAllProjectsByUser(connectedUser.getMailUser());
        welcomeLabel.setText("Welcome " + connectedUser.getFirstNameUser());
        projectsGridPane.setPrefHeight(460);
        projectsGridPane.setPrefWidth(600);
        int j = 0;
        int k = 0;
        if (!projects.equals(null) && !projects.isEmpty()){
            for (int i = 0; i < projects.size(); i++) {
                if (j == 3) {
                    j = 0;
                    k++;
                }
                VBox vbox = new VBox();
                vbox.getChildren().add(new Label(projects.get(i).getProjectName()));
                vbox.getChildren().add(new Label(projects.get(i).getProjectDescription()));
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                vbox.getChildren().add(new Label(df.format(projects.get(i).getInitialDate())));
                vbox.getChildren().add(new Label(df.format(projects.get(i).getFinalDate())));
                Button myButton = new Button("View");
                vbox.getChildren().add(myButton);
                int finalI = i;
                myButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        MySqlClient sql = MySqlDAOFactory.getConnection();
                        Project project = new Project();
                        if(sql.connect()) {
                            String query = "select * from project where idProject = ?";
                            PreparedStatement pQuery = null;
                            try {
                                pQuery = sql.getDbConnect().prepareStatement(query);
                                pQuery.setInt(1, projects.get(finalI).getIdProject());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            ResultSet res = null;
                            try {
                                res = pQuery.executeQuery();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            int idProject = 0;
                            int idCreator = 0;
                            String nameProject = "";
                            String descriptionProject = "";
                            Date initialDateProject = new Date(0,0,0);
                            Date finalDateProject = new Date(0,0,0);
                            while (true) {
                                try {
                                    if (!res.next()) break;
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    idProject = res.getInt(1);
                                    nameProject = res.getString(2);
                                    descriptionProject = res.getString(3);
                                    initialDateProject = res.getDate(4);
                                    finalDateProject = res.getDate(5);
                                    idCreator = res.getInt(6);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            project = new Project(idProject, idCreator, nameProject, descriptionProject, initialDateProject, finalDateProject);
                        }
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/projectPage.fxml"));
                            Parent root;
                            root = loader.load();
                            scene.setRoot(root);
                            ((ProjectPageController)loader.getController()).connectedUser = connectedUser;
                            ((ProjectPageController)loader.getController()).project = project;
                            ((ProjectPageController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
                            ((ProjectPageController)loader.getController()).boardDescriptionVBox.getScene().getWindow().setHeight(700);
                            ((ProjectPageController)loader.getController()).jellyFacade = jellyFacade;
                            ((ProjectPageController)loader.getController()).setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                projectsGridPane.add(vbox, j, k);
                j++;
            }
        }
        else{
            VBox vbox = new VBox();
            vbox.getChildren().add(new Label("You have no projects"));
            projectsGridPane.add(vbox, 0,0);
        }
        this.scene = scene;
    }

}