package jelly.ui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Project;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    @FXML
    private GridPane projectsGridPane;

    @FXML
    private Hyperlink myInfoLink;

    @FXML
    private Label welcomeLabel;

    private Scene scene;

    public void showPersonalInfo(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/updateInformationUI.fxml"));
                Parent root;
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Jelly - My information");
                stage.setScene(new Scene(root));
                ((UpdateAccountInformationController)loader.getController()).setScene(scene);
                ((UpdateAccountInformationController)loader.getController()).firstNameField.setText(connectedUser.getFirstNameUser());
                ((UpdateAccountInformationController)loader.getController()).lastNameField.setText(connectedUser.getLastNameUser());
                ((UpdateAccountInformationController)loader.getController()).pseudoField.setText(connectedUser.getPseudoUser());
                ((UpdateAccountInformationController)loader.getController()).emailField.setText(connectedUser.getMailUser());
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void showCreateProject(ActionEvent actionEvent) {
    }

    public void logOff(ActionEvent actionEvent) {
    }

    public void setScene(Scene scene) {
        List<Project> projects = jellyFacade.getAllProjectsByUser(connectedUser.getMailUser());
        welcomeLabel.setText("Welcome " + connectedUser.getFirstNameUser());
        projectsGridPane.setPrefHeight(460);
        projectsGridPane.setPrefWidth(600);
        int j = 0;
        int k = 0;
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
            vbox.getChildren().add(new Button("Consulter"));
            projectsGridPane.add(vbox, j, k);
            j++;
        }
        this.scene = scene;
    }

}
