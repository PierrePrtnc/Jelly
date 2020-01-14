package jelly.ui.controller;

import java.io.IOException;
import java.net.URL;

/**
 * Class LoginController.
 * @author Pierre Partinico
 *
 */
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;

public class LoginController implements Initializable{

	@FXML
	Menu homeMenu;

	public void showMenu() {
		System.out.println("test");
	}
	
	@FXML 
    private TextField emailField; 
	@FXML 
    private PasswordField passwordField;
	@FXML 
    private Button loginButton;
	@FXML
    private VBox window;
    @FXML
    private ResourceBundle resources;
	
	protected JellyFacade jellyFacade = new JellyFacade();
	private Scene scene;
	
	public LoginController() {
	}
	
    public void setScene(Scene scene) {
        this.scene = scene; 
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	/**
	 * handles the button color if the mouse is over it or not
	 */
	public void handleMouseEntered() {
		this.scene.setCursor(Cursor.HAND);
		loginButton.setStyle("#ACD6FX");
		
	}

	public void handleMouseExited() {
		this.scene.setCursor(Cursor.DEFAULT);
		loginButton.setStyle("#ACD6FA");
	}

	/**
	 * calls the JavaFX page "SignupUI" to display the sign up form
	 */
	public void handleSignUp() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/SignupUI.fxml"));
			Parent root;
			root = loader.load();
			this.scene.setRoot(root);
			((SignUpController)loader.getController()).setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * handles the login mechanism
	 * @param arg0
	 */
	public void handle(ActionEvent arg0) {
		
		
		if(emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
		
            if(emailField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "Please enter your email address");
            }
            if(passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "Please enter your password");
            }
		}
		else {
			try {
				User connectedUser = jellyFacade.login(emailField.getText(), passwordField.getText());
				if(!(connectedUser.equals(null)||connectedUser.getFirstNameUser().contentEquals(""))) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/home.fxml"));
					Parent root;
					root = loader.load();
					this.scene.setRoot(root);
					((HomeController)loader.getController()).connectedUser = connectedUser;
					((HomeController)loader.getController()).jellyFacade = jellyFacade;
					((HomeController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
					((HomeController)loader.getController()).setScene(scene);
				}
			}
			catch(Exception e) {
				showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "Invalid information");
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

}
