package jelly.ui.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;

public class UpdateAccountInformationController {
	
	private JellyFacade jellyFacade = new JellyFacade();
	protected Scene scene;
	@FXML
	protected TextField firstNameField;
	@FXML
	protected TextField lastNameField;
	@FXML
	protected TextField pseudoField;
	@FXML
	protected TextField emailField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField passwordConfirmationField;
	@FXML
	private Button updateButton;
	@FXML
    private VBox window;
	@FXML
	protected User connectedUser;
	
	@FXML
	protected Label notificationNumber;
	
	public UpdateAccountInformationController() {
		
	}

	/**
	 * sets the scene attribute
	 * @param scene
	 */
    public void setScene(Scene scene) {
        this.scene = scene; 
    }

	/**
	 * modifies the display of a button when the mouse hovers it
	 */
	public void handleMouseEntered() {
		this.scene.setCursor(Cursor.HAND);
		updateButton.setStyle("#ACD6FX");
		
	}

	public void handleMouseExited() {
		this.scene.setCursor(Cursor.DEFAULT);
		updateButton.setStyle("#ACD6FA");
	}

	/**
	 * removes the account of the connected user
	 */
	public void handleDelete() {
		if(jellyFacade.deleteUser(emailField.getText())){
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/loginUI.fxml"));
				Parent root;
				root = loader.load();
				this.scene.setRoot(root);
				((LoginController)loader.getController()).setScene(scene);
				showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your account has been deleted");	

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * calls the JavaFX component "NotificationUI" to display unread notifications
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

	/**
	 * updates the connected user with new information fetched from the fields of the displayed form
	 */
	public void handle() {
		if(firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || pseudoField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordConfirmationField.getText().isEmpty()) {
			String emptyFields = "Please enter your:\n";
            if(firstNameField.getText().isEmpty()) {
            	emptyFields += "first name\n";
            }
            if(lastNameField.getText().isEmpty()) {
            	emptyFields += "last name\n";
            }
            if(pseudoField.getText().isEmpty()) {
            	emptyFields += "pseudo\n";
            }
            if(emailField.getText().isEmpty()) {
            	emptyFields += "email address\n";
            }
            if(passwordField.getText().isEmpty()) {
            	emptyFields += "password\n";
            }
            if(passwordConfirmationField.getText().isEmpty()) {
            	emptyFields += "password confirmation\n";
            }
            showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", emptyFields);
		}
		else {
			if(passwordField.getText().length() < 5){
				showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "Your password has to be 5 characters long at least");
			} else if(!(passwordField.getText().equals(passwordConfirmationField.getText()))) {
				showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "Password and password confirmaion have to be equals");
			}
			else {
				if(jellyFacade.updateUser(firstNameField.getText(), lastNameField.getText(), emailField.getText(), pseudoField.getText(), passwordField.getText())){
					showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your information have been updated");	
				}
				else {
		            showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "The update has failed, please try again");

				}
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/home.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((HomeController)loader.getController()).connectedUser = connectedUser;
        ((HomeController)loader.getController()).jellyFacade = jellyFacade;
		((HomeController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(connectedUser).size());
        ((HomeController)loader.getController()).setScene(scene);
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
