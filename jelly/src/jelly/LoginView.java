package jelly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class LoginView.
 * @author Pierre Partinico
 *
 */

public class LoginView extends Application {
	@Override
	public void start(Stage stage){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("loginUI.fxml"));
			Parent root = loader.load();	
			Scene scene = new Scene(root, 800, 500);
			((LoginController)loader.getController()).setScene(scene);	    
			stage.setTitle("Jelly - Login");
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}		       
	}

	
    public static void main(String[] args) {
        launch(args);
    }
}
