package jelly.ui;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jelly.JellyFacade;
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
			JellyFacade jellyFacade = new JellyFacade();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/user/loginUI.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root, 700, 380);
			((LoginController)loader.getController()).setScene(scene);
			stage.setTitle("Jelly");
			Image image = new Image("https://www.pngrepo.com/png/298931/170/jellyfish.png");
			stage.getIcons().add(image);
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
