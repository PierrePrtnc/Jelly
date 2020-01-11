package jelly.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import jelly.JellyFacade;
import jelly.User;


public class HeaderController{
	
	protected User connectedUser;
	private JellyFacade jellyFacade = new JellyFacade();
	
	@FXML
	protected Label notificationNumber;

    @FXML
    Menu homeMenu;

    public String showMenu() {
        return connectedUser.getFirstNameUser();
    }


}
