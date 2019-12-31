package jelly;

import java.io.IOException;

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

public class UpdateAccountInformationController {
	
	private JellyFacade jellyFacade = new JellyFacade();
	private Scene scene;
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
	
	public UpdateAccountInformationController() {
		
	}
	
    public void setScene(Scene scene) {
        this.scene = scene; 
    }
    
	
	public void handleMouseEntered() {
		this.scene.setCursor(Cursor.HAND);
		updateButton.setStyle("#ACD6FX");
		
	}

	public void handleMouseExited() {
		this.scene.setCursor(Cursor.DEFAULT);
		updateButton.setStyle("#ACD6FA");
	}
    
	public void handleDelete() {
		if(jellyFacade.deleteUser(emailField.getText())){
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginUI.fxml"));
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
				if(jellyFacade.updateUser(firstNameField.getText(), lastNameField.getText(), emailField.getText(), pseudoField.getText(), passwordField.getText())){
					showAlert(Alert.AlertType.INFORMATION, window.getScene().getWindow(), "Success", "Your information have been updated");	
				}
				else {
		            showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "Error", "The update has failed, please try again");

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
