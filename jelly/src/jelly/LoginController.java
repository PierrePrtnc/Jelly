package jelly;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class LoginController implements Initializable{
	
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
	
	private JellyFacade jellyFacade = new JellyFacade();
	private Scene scene;
	
	public LoginController() {
	}
	
    public void setScene(Scene scene) {
        this.scene = scene; 
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void handleMouseEntered() {
		this.scene.setCursor(Cursor.HAND);
		loginButton.setStyle("#ACD6FX");
		
	}

	public void handleMouseExited() {
		this.scene.setCursor(Cursor.DEFAULT);
		loginButton.setStyle("#ACD6FA");
	}
	
	public void handleSignUp() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SignupUI.fxml"));
			Parent root;
			root = loader.load();
			this.scene.setRoot(root);
			((SignUpController)loader.getController()).setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
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
				FXMLLoader loader = new FXMLLoader(getClass().getResource("updateInformationUI.fxml"));
				Parent root;
				root = loader.load();
				this.scene.setRoot(root);
				((UpdateAccountInformationController)loader.getController()).setScene(scene);
				((UpdateAccountInformationController)loader.getController()).firstNameField.setText(connectedUser.getFirstNameUser());
				((UpdateAccountInformationController)loader.getController()).lastNameField.setText(connectedUser.getLastNameUser());
				((UpdateAccountInformationController)loader.getController()).pseudoField.setText(connectedUser.getPseudoUser());
				((UpdateAccountInformationController)loader.getController()).emailField.setText(connectedUser.getMailUser());			}
			
			}
			catch(Exception e) {
				showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "Invalid information");
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
