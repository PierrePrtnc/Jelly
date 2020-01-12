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
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.Step;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateBoardController {

    protected JellyFacade jellyFacade;
    protected Board board;
    protected Project project;
    private Scene scene;

    @FXML
    private VBox window;

    @FXML
    protected Label boardNameLabel;

    @FXML
    protected TextField boardNameField;

    @FXML
    protected TextField subjectField;

    @FXML
    protected TextArea descriptionArea;

    @FXML
    private Button cancelButton;

    @FXML
    private Button updateButton;

    public void returnToBoardPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/boardPage.fxml"));
        Parent root;
        try {
            root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((BoardPageController)loader.getController()).board = board;
        ((BoardPageController)loader.getController()).project = project;
        ((BoardPageController)loader.getController()).jellyFacade = jellyFacade;
        ((BoardPageController)loader.getController()).setScene(scene);
    }

    public void updateBoard() {
        if(boardNameField.getText().isEmpty() || subjectField.getText().isEmpty() || descriptionArea.getText().isEmpty()) {
            String emptyFields = "Please enter :\n";
            if(boardNameField.getText().isEmpty()) {
                emptyFields += "board name\n";
            }
            if(subjectField.getText().isEmpty()) {
                emptyFields += "board subject\n";
            }
            if(descriptionArea.getText().isEmpty()) {
                emptyFields += "board description\n";
            }
            showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", emptyFields);
        }
        else {
            if(jellyFacade.updateBoard(board.getIdBoard(), board.getIdProjectOfBoard(), boardNameField.getText(), subjectField.getText(), descriptionArea.getText())){
                showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your information have been updated");
            }
            else {
                showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "The update has failed, please try again");

            }
        }
    }

    public void setScene(Scene scene) {
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
