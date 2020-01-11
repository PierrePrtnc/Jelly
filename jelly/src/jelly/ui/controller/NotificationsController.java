package jelly.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import jelly.JellyFacade;
import jelly.collaboration.Collaborator;
import jelly.notification.Notification;

public class NotificationsController implements Initializable{
	


	private JellyFacade jellyFacade = new JellyFacade();
	protected String emailUser;
	private Scene scene;
	@FXML
    private VBox window;

	
	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	
    public void setScene(Scene scene) {
        this.scene = scene; 
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		User currentuser = jellyFacade.getUser(emailUser);
//		ArrayList<Notification> notifications = jellyFacade.getUnreadNotificationList(currentuser);
//		for(int i = 0; i < notifications.size(); i++) {
//			VBox notification = new VBox();
//			Label user = new Label(notifications.get(i).getOriginator().user.getPseudoUser());
//			Label message = new Label(notifications.get(i).getOriginator().user.getPseudoUser());
//			Label action = new Label();
//			notification.getChildren().addAll(user, message);
//			Collaborator[] projectCollaborators = (Collaborator[]) notifications.get(i).getOriginator().project.getCollaborators().toArray();
//			boolean invitation = true;
//			for(int j = 0; j < projectCollaborators.length;j++) {
//				
//				if(projectCollaborators[j].getUser().equals(currentuser)) {
//					Hyperlink seeProject = new Hyperlink(notifications.get(i).getAction());
//					seeProject.setOnAction( e->{
//						try {
//							FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectUI.fxml"));
//							Parent root;
//							root = loader.load();
//							this.scene.setRoot(root);
//							((ProjectController)loader.getController()).setScene(scene);
//							notification.getChildren().add(seeProject);
//							invitation = false;
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					});
//				}
//			}
//				if(invitation) {
//					Hyperlink joinProject = new Hyperlink(notifications.get(i).getAction());
//					joinProject.setOnAction( e-> {
//						currentuser.joinProject(projectCollaborators[i].getProject());
//					});
//					notification.getChildren().add(joinProject);
//	
//				}
//				
//	
//				//mettre bouton delete notif
//				Button deleteNotification = new Button("Delete");
//				deleteNotification.setOnAction(e ->{
//					jellyFacade.deleteNotification(notifications.get(i).getIdNotification(), currentuser);
//				});
//				notification.getChildren().add(deleteNotification);
//				window.getChildren().add(notification);
//			}

		}
	
}
