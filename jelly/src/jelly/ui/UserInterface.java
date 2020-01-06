package jelly.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import jelly.JellyFacade;
import jelly.User;
import jelly.notification.NotificationHandler;

/**
 * Class LoginView.
 * @author Pierre Partinico
 *
 */

public class UserInterface extends Application {
	
	//Facade linked to the GUI
	private JellyFacade jellyFacade = new JellyFacade();
	
    public JellyFacade getJellyFacade() {
		return jellyFacade;
	}

	public void setJellyFacade(JellyFacade jellyFacade) {
		this.jellyFacade = jellyFacade;
	}
	
	//Creates the container for the form
	public GridPane createLoginForm() {
        GridPane loginGridPane = new GridPane();
        loginGridPane.setAlignment(Pos.CENTER);
        loginGridPane.setVgap(20);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        loginGridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return loginGridPane;
    }


	//Creates all the elements to be inserted in the container
	public void addElements(GridPane loginGridPane) {

    	
    	String url = "https://www.pngrepo.com/download/298931/jellyfish.png";
   	 
    	Image image = new Image(url);
    	
    	ImageView imageView = new ImageView(image);
    	imageView.setFitHeight(80);
    	imageView.setFitWidth(80);
    	
        // Create Header
        Label header = new Label("Login");
        header.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        loginGridPane.add(header, 0,0,2,1);
        loginGridPane.add(imageView,0,1,2,1);
        GridPane.setHalignment(header, HPos.CENTER);
        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setMargin(header, new Insets(20, 0,20,0));


        // Create Email Label
        Label emailLabel = new Label("Email address : ");
        emailLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        loginGridPane.add(emailLabel, 0, 3);

        // Create Email Text Field
        TextField emailField = new TextField();
        emailField.setPrefHeight(40);
        loginGridPane.add(emailField, 1, 3);

        // Create Password Label
        Label passwordLabel = new Label("Password : ");
        passwordLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        loginGridPane.add(passwordLabel, 0, 4);

        // Create Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        loginGridPane.add(passwordField, 1, 4);

        // Create Submit Button
        Button submitButton = new Button("Login");
        submitButton.setPrefHeight(45);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(110);
        loginGridPane.add(submitButton, 0, 5, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);

        // Add Submit Button Actions
       submitButton.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			
			if(emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
			
	            if(emailField.getText().isEmpty()) {
	                showAlert(Alert.AlertType.ERROR, loginGridPane.getScene().getWindow(), "Error", "Please enter your email address");
	            }
	            if(passwordField.getText().isEmpty()) {
	                showAlert(Alert.AlertType.ERROR, loginGridPane.getScene().getWindow(), "Error", "Please enter your password");
	            }
			}
			else {
				//User exists
				try {
					User connectedUser = jellyFacade.login(emailField.getText(), passwordField.getText());
					showAlert(Alert.AlertType.INFORMATION, loginGridPane.getScene().getWindow(), "Successfully logged in", "Welcome" + " " + connectedUser.getFirstNameUser());
				}
				//User not found in database
				catch(Exception e) {
					showAlert(Alert.AlertType.ERROR, loginGridPane.getScene().getWindow(), "Error", "Invalid information");
				}
			}
        }

       });
    }

	//Create a popup to notify the user about the current state of the application
    public void showAlert(Alert.AlertType type, Window owner, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
	
    @Override
    public void start(Stage stage) throws Exception {   	
        stage.setTitle("Jelly - login");
        GridPane loginGridPane = createLoginForm();
        addElements(loginGridPane);
        Scene scene = new Scene(loginGridPane, 800, 500);
        stage.setScene(scene);
        stage.show();
    }


    
    public static void main(String[] args) {
        launch(args);
    }
}

