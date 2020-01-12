package jelly.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jelly.JellyFacade;
import jelly.project.Project;

public class UpdateProjectController {
    public Project project;
    public JellyFacade jellyFacade;

    @FXML
    public TextField projectNameField;

    @FXML
    public TextArea projectDescriptionText;

    @FXML
    public DatePicker initialDatePicker;

    @FXML
    public DatePicker finalDatePicker;

    public void setScene(Scene scene) {
    }
}
