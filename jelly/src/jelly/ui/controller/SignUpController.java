package jelly.ui.controller;

import java.io.IOException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;

public class SignUpController {
	
	private JellyFacade jellyFacade = new JellyFacade();
	private Scene scene;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField pseudoField;
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField passwordConfirmationField;
	@FXML
	private Button signUpButton;
	@FXML
    private VBox window;
	
	public SignUpController() {
	}
	
    public void setScene(Scene scene) {
        this.scene = scene; 
    }
    
	
	public void handleMouseEntered() {
		this.scene.setCursor(Cursor.HAND);
		signUpButton.setStyle("#ACD6FX");
		
	}

	public void handleMouseExited() {
		this.scene.setCursor(Cursor.DEFAULT);
		signUpButton.setStyle("#ACD6FA");
	}
    
    
    public static boolean isValidEmailAddress(String email) {
    	   boolean result = true;
    	   try {
    	      InternetAddress emailAddr = new InternetAddress(email);
    	      emailAddr.validate();
    	   } catch (AddressException ex) {
    	      result = false;
    	   }
    	   return result;
    	}
    
	public void handleLogin() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/loginUI.fxml"));
			Parent root;
			root = loader.load();
			this.scene.setRoot(root);
			((LoginController)loader.getController()).setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
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
				if(!(passwordField.getText().equals(passwordConfirmationField.getText()))) {
					showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "Password and password confirmaion have to be equals");
				}
				else {
					if(!isValidEmailAddress(emailField.getText()) || passwordField.getText().length() < 5){
						if(!isValidEmailAddress(emailField.getText())){
							showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "This mail address is invalid");
						}
						if(passwordField.getText().length() < 5){
							showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "Your password has to be 5 characters long at least");
						}
					}

					else {
						try {
							User connectedUser = jellyFacade.addUser(firstNameField.getText(), lastNameField.getText(), emailField.getText(), pseudoField.getText(), passwordField.getText());
							showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Successfully logged in", "Welcome" + " " + connectedUser.getFirstNameUser());										}
						catch(Exception e) {
							showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "This mail address is already taken");
					}
					
				}
				}
			}

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
