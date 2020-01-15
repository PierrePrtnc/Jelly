package jelly.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import javafx.util.Callback;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Board;
import jelly.project.Project;
import jelly.project.State;
import jelly.project.Step;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GanttEditController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    protected Project project;
    private Scene scene;

    @FXML
    protected DatePicker ganttStepStartingDateField;

    @FXML
    protected DatePicker ganttStepEndingDateField;

    @FXML
    private TextArea ganttStepDescField;

    @FXML
    private TextField ganttStepNameField;

    @FXML
    private ScrollPane scrollPaneGantt;

    @FXML
    protected Label notificationNumber;

    @FXML
    private ComboBox<String> difficultiesComboBox;

    private GridPane gridPane;
    private List<LocalDate> dates;
    private Board board;

    /**
     * sets the scene attribute
     * builds the page for the edition of a Gantt diagram
     * @param scene
     * @throws ParseException
     */
    public void setScene(Scene scene) throws ParseException {
        this.scene = scene;
        scrollPaneGantt.getScene().getWindow().setHeight(770);
        scrollPaneGantt.getScene().getWindow().setWidth(1200);
        this.gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        dates = getDates(project.getInitialDate(), project.getFinalDate());
        for (int i = 0; i < dates.size(); i++) {
            Label dateGantt = new Label(dates.get(i).toString());
            dateGantt.setPadding(new Insets(0,2,0,2));
            gridPane.add(dateGantt,i+1, 0);
        }
        scrollPaneGantt.setContent(gridPane);
        restrictDatePicker(ganttStepStartingDateField, dates.get(0), dates.get(dates.size()-1));
        restrictDatePicker(ganttStepEndingDateField, dates.get(0), dates.get(dates.size()-1));
        String difficulties[] = {"Easy", "Medium", "Hard"};
        difficultiesComboBox.getItems().addAll(difficulties);
        List<Board> boards = new ArrayList<>(project.getBoards());
        board = boards.get(0);

    }

    /**
     * Date picker for the Gantt diagram editor
     * @param datePicker
     * @param minDate
     * @param maxDate
     */
    public void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }else if (item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    /**
     * Fetches the dates between two dates
     * @param start     the starting date
     * @param end       the ending date
     * @return  an arraylist of LocalDates
     */
    private ArrayList<LocalDate> getDates(Date start, Date end) {
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = start.toInstant();
        Instant instant2 = end.toInstant();
        LocalDate startDate = instant.atZone(defaultZoneId).toLocalDate();
        LocalDate endDate = instant2.atZone(defaultZoneId).toLocalDate();
        List<LocalDate> totalDates = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            totalDates.add(startDate);
            startDate = startDate.plusDays(1);
        }
        return (ArrayList) totalDates;
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
     * adds a step to the Gantt diagram
     */
    public void addStep() {
        if (ganttStepNameField.getText().isEmpty() || this.ganttStepNameField.getText().length() > 20)
            showAlert(Alert.AlertType.ERROR, ganttStepNameField.getScene().getWindow(), "Error", "Please enter the name of the Step (length < 20 characters)");
        else if (ganttStepDescField.getText().isEmpty() || this.ganttStepNameField.getText().length() > 55)
            showAlert(Alert.AlertType.ERROR, ganttStepNameField.getScene().getWindow(), "Error", "Please enter the description of the Step (length < 55 characters)");
        else if(ganttStepStartingDateField.getValue() == null)
            showAlert(Alert.AlertType.ERROR, ganttStepNameField.getScene().getWindow(), "Error", "Please choose a starting date");
        else if(ganttStepEndingDateField.getValue() == null)
            showAlert(Alert.AlertType.ERROR, ganttStepNameField.getScene().getWindow(), "Error", "Please choose an ending date");
        else {
            int difficulty = 1;
            Pane p = new Pane();
            switch (difficultiesComboBox.getValue()) {
                case "Easy":
                    difficulty = 1;
                    p.setStyle("-fx-background-color: #78ff72");
                    break;
                case "Medium":
                    difficulty = 2;
                    p.setStyle("-fx-background-color: #ffda87");
                    break;
                case "Hard":
                    difficulty = 3;
                    p.setStyle("-fx-background-color: #ff1955");
                    break;
                case "Select a difficulty":
                    showAlert(Alert.AlertType.ERROR, ganttStepNameField.getScene().getWindow(), "Error", "Please choose a difficulty");
                    break;
                default:
                    difficulty = 1;
            }

            Date startingDateStep = Date.from(ganttStepStartingDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endingDateStep = Date.from(ganttStepEndingDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            long dateDiff = endingDateStep.getTime() - startingDateStep.getTime();
            long daysCount = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
            if (dateDiff < 0)
                showAlert(Alert.AlertType.ERROR, ganttStepEndingDateField.getScene().getWindow(), "Error", "Please select appropriate dates.");
            else {
                Step step = new Step(ganttStepNameField.getText(), ganttStepDescField.getText(), startingDateStep, endingDateStep, difficulty);
                board.addStep(step);
                Label stepName = new Label(step.getStepName());
                stepName.setPadding(new Insets(0,2,0,2));
                gridPane.add(stepName, 0, getRowCount(gridPane));
                int index = dates.indexOf(ganttStepStartingDateField.getValue());
                int j = getRowCount(gridPane);

                for (int i = index+1; i <= daysCount+(index+1); i++) {
                    Pane newP = new Pane();
                    newP.setStyle(p.getStyle());
                    gridPane.add(newP, i, j-1);
                }
            }

        }
    }
    
    
    private int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
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
     * cancels the edition of the board and calls back the project creation page
     * @throws IOException
     */
    public void cancel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/project/ProjectCreation.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((ProjectCreationController)loader.getController()).connectedUser = connectedUser;
        ((ProjectCreationController)loader.getController()).jellyFacade = jellyFacade;
        ((ProjectCreationController)loader.getController()).setScene(scene);
        ((ProjectCreationController)loader.getController()).notificationNumber.getScene().getWindow().setWidth(800);
        ((ProjectCreationController)loader.getController()).notificationNumber.getScene().getWindow().setHeight(500);
        ((ProjectCreationController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
    }

    /**
     * saves the Gantt by inserting the newly created project into the database and newly created boards into the database
     * @throws IOException
     */
    public void saveGantt() throws IOException {
        boolean success = true;
        if (board.getSteps().size() == 0)
            showAlert(Alert.AlertType.ERROR, ganttStepNameField.getScene().getWindow(), "Error", "Please add at least one step to save the diagram.");
        else {
            Project temp = jellyFacade.insertProject(project.getProjectName(), project.getProjectDescription(), project.getInitialDate(), project.getFinalDate(), connectedUser);
            if (temp == null) {
                showAlert(Alert.AlertType.ERROR, ganttStepNameField.getScene().getWindow(), "Error", "Project insert failed. Please try again");
                success = false;
            }
            Board temp2 = jellyFacade.insertBoard(board.getBoardName(), board.getSubjectBoard(), board.getDescriptionBoard(), temp.getIdProject());
            if (temp2 == null)
                showAlert(Alert.AlertType.ERROR, ganttStepNameField.getScene().getWindow(), "Error", "Board insert failed. Please try again");
            for (Step step : board.getSteps()) {
                if (jellyFacade.insertStep(step.getStepName(), step.getInitialDate(), step.getFinalDate(), temp2.getIdBoard(), 1, step.getStepDifficulty(), step.getStepDesc()) == null)
                    showAlert(Alert.AlertType.ERROR, ganttStepNameField.getScene().getWindow(), "Error", "Step insert failed. Please try again");
            }
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, ganttStepNameField.getScene().getWindow(), "Success", "Project created ! Home redirection...");
                showHome();
            }
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
    public void showHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/home.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((HomeController)loader.getController()).connectedUser = connectedUser;
        ((HomeController)loader.getController()).jellyFacade = jellyFacade;
        ((HomeController)loader.getController()).setScene(scene);
        ((HomeController)loader.getController()).notificationNumber.getScene().getWindow().setWidth(800);
        ((HomeController)loader.getController()).notificationNumber.getScene().getWindow().setHeight(500);
        ((HomeController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
    }

}