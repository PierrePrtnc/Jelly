package jelly.ui;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jelly.ui.controller.LoginController;

/**
 * Class LoginView.
 * @author Pierre Partinico
 *
 */

public class MainView extends Application {
	@Override
	public void start(Stage stage){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/user/loginUI.fxml"));
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