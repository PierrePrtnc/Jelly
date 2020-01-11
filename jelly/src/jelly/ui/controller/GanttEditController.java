package jelly.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import jelly.JellyFacade;
import jelly.User;
import jelly.project.Project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GanttEditController {

    protected JellyFacade jellyFacade;
    protected User connectedUser;
    protected Project project;
    private Scene scene;
    private Date startingDateGantt;
    private Date endingDateGantt;

    @FXML
    private Button ganttStepAddingButton;

    @FXML
    private DatePicker ganttStepstartingDateField;

    @FXML
    private DatePicker ganttStepEndingDateField;

    @FXML
    private TextArea ganttStepDescField;

    @FXML
    private TextField ganttStepNameField;

    @FXML
    private ScrollPane scrollPaneGantt;

    public void setScene(Scene scene) throws ParseException {
        this.scene = scene;
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        String date1 = "31/12/2019";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
        List<LocalDate> dates = getDates(date, new Date());
        System.out.println(dates.size());
        for (int i = 0; i < dates.size(); i++) {
            gridPane.add(new Label(dates.get(i).toString()),i+1, 0);
            gridPane.add(new Label(dates.get(i).toString()),0, i+1);
        }
        scrollPaneGantt.setContent(gridPane);

    }

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

    public void handle() {

    }

}
