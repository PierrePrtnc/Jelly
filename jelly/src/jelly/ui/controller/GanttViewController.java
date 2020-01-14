package jelly.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Project;
import jelly.project.Step;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GanttViewController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    protected Project project;

    private Scene scene;

    @FXML
    private ScrollPane scrollPaneGantt;

    @FXML
    private Label labelTitle;

    @FXML
    protected Label notificationNumber;

    private List<LocalDate> dates;
    private GridPane gridPane;

    /**
     * calls the JavaFX component "home" to display the home page
     * @see HomeController#connectedUser
     * @see HomeController#jellyFacade
     * @see HomeController#notificationNumber
     * @see HomeController#setScene(Scene)
     * @throws IOException
     */
    public void showHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/home.fxml"));
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

    /**
     * sets the attribute scene and builds the Gantt graphic interface
     * @param scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
        this.gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        labelTitle.setText("Gantt Diagram for " + project.getProjectName());
        List<Step> steps ;
        steps = project.getGanttDiagram();
        java.util.Date start = new java.util.Date(project.getInitialDate().getTime());
        java.util.Date end = new java.util.Date(project.getFinalDate().getTime());
        dates = getDates(start, end);
        for (int i = 0; i < dates.size(); i++) {
            Label dateGantt = new Label(dates.get(i).toString());
            dateGantt.setPadding(new Insets(0,2,0,2));
            gridPane.add(dateGantt,i+1, 0);
        }
        Pane p = new Pane();
        for (Step aStep: steps) {
            switch (aStep.getStepDifficulty()) {
                case 1:
                    p.setStyle("-fx-background-color: #78ff72");
                    break;
                case 2:
                    p.setStyle("-fx-background-color: #ffda87");
                    break;
                case 3:
                    p.setStyle("-fx-background-color: #ff1955");
                    break;
            }
            java.util.Date startStep = new java.util.Date(aStep.getInitialDate().getTime());
            java.util.Date endStep = new java.util.Date(aStep.getFinalDate().getTime());
            long dateDiff = endStep.getTime() - startStep.getTime();
            long daysCount = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
            Label stepName = new Label(aStep.getStepName());
            stepName.setPadding(new Insets(0,2,0,2));
            gridPane.add(stepName, 0, gridPane.getRowCount());
            Instant instant = startStep.toInstant();
            LocalDate startDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            int index = dates.indexOf(startDate);
            int j = gridPane.getRowCount();

            for (int i = index+1; i <= daysCount+(index+1); i++) {
                Pane newP = new Pane();
                newP.setStyle(p.getStyle());
                gridPane.add(newP, i, j-1);
            }
        }
        scrollPaneGantt.setContent(gridPane);


    }

    /**
     * date management
     * @param start     the starting date
     * @param end       the ending date
     * @return  an arraylist of LocalDates
     */
    private ArrayList<LocalDate> getDates(java.util.Date start, java.util.Date end) {
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
     * calls the JavaFX component "projectPage" to go back to the project page
     * @throws IOException
     */
    public void showProject() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/project/projectPage.fxml"));
        Parent root;
        root = loader.load();
        scene.setRoot(root);
        ((ProjectPageController)loader.getController()).connectedUser = connectedUser;
        ((ProjectPageController)loader.getController()).project = project;
        ((ProjectPageController)loader.getController()).boardDescriptionVBox.getScene().getWindow().setHeight(780);
        ((ProjectPageController)loader.getController()).boardDescriptionVBox.getScene().getWindow().setWidth(850);
        ((ProjectPageController)loader.getController()).jellyFacade = jellyFacade;
        ((ProjectPageController)loader.getController()).setScene(scene);
    }

    /** calls the JavaFX component "NotificationUI" to display unread notifications
	 * @see NotificationsController#emailUser
	 * @see NotificationsController#currentUser
	 * @see NotificationsController#setScene(Scene)
	 */
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
}
