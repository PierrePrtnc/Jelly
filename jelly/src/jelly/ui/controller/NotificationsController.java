package jelly.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import jelly.JellyFacade;
import jelly.User;
import jelly.collaboration.Collaborator;
import jelly.notification.Notification;
import jelly.project.Project;

public class NotificationsController {

	private JellyFacade jellyFacade = new JellyFacade();
	protected String emailUser;
	protected User currentUser;
	private Scene scene;
	@FXML
    private VBox window;
	
    @FXML
    private GridPane notificationsGridPane;
	
	ArrayList<Notification> notifications;

	
	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
    
	public void setScene(Scene scene) {
		notifications = jellyFacade.getUnreadNotificationList(jellyFacade.getUser(emailUser));        User currentuser = jellyFacade.getUser(emailUser);
        notificationsGridPane.setPrefHeight(460);
        notificationsGridPane.setPrefWidth(600);
        for (int i = 0; i < notifications.size(); i++) {
            VBox vbox = new VBox();
            System.out.println(notifications.get(i).getOriginator().getUser().getMailUser());
            vbox.getChildren().add(new Label("Sender : " + notifications.get(i).getOriginator().getUser().getMailUser()));
            vbox.getChildren().add(new Label("Project : " + notifications.get(i).getOriginator().getProject().getProjectName()));
            vbox.getChildren().add(new Label("Message : " + notifications.get(i).getMessage()));
			boolean invitation = true;
            try {
    			Collaborator[] projectCollaborators = (Collaborator[]) notifications.get(i).getOriginator().project.getCollaborators().toArray();
    			int j = 0;
    			while(j < projectCollaborators.length && invitation) {
    				
    				if(projectCollaborators[j].getUser().equals(currentuser)) {
    					invitation = false;
    					Button seeProject = new Button("View project");
    					seeProject.setOnAction( e->{
    						try {
    							FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectUI.fxml"));
    							Parent root;
    							root = loader.load();
    							this.scene.setRoot(root);
    							//((ProjectController)loader.getController()).setScene(scene);
    							vbox.getChildren().add(seeProject);						
    						} catch (IOException e2) {
    							
    						}
    					});
    				}
    			}
            }
			catch(NullPointerException e3) {
				
			}
				if(invitation) {
					Button joinProject = new Button("Join this project");
					final int index = i;
					joinProject.setOnAction( e-> {
							currentUser.joinProject(notifications.get(index).getOriginator().getProject());
						});

					vbox.getChildren().add(joinProject);
	
				}
				
				Button deleteNotification = new Button("Delete");
				final int k = i;
				deleteNotification.setOnAction(e ->{
					System.out.println(notifications.get(k).getIdNotification());
					jellyFacade.deleteNotification(notifications.get(k).getIdNotification(), currentuser);
			        try {
			            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/NotificationsUI.fxml"));
			            Parent root;
			            root = loader.load();
			            this.scene.setRoot(root);
						((NotificationsController)loader.getController()).emailUser = emailUser;
						((NotificationsController)loader.getController()).currentUser = currentuser;
			            ((NotificationsController)loader.getController()).setScene(scene);

			        } catch (IOException ex) {
			            ex.printStackTrace();
			        }
				});
				vbox.getChildren().add(deleteNotification);
				notificationsGridPane.add(vbox, 1,i);
	        	jellyFacade.changeStateNotification(notifications.get(i).getIdNotification(), jellyFacade.getUser(emailUser));

			}
        
        this.scene = scene;
    }
	
    public void showHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/home.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((HomeController)loader.getController()).connectedUser = jellyFacade.getUser(emailUser);
        ((HomeController)loader.getController()).jellyFacade = jellyFacade;
		((HomeController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(jellyFacade.getUser(emailUser)).size());
        ((HomeController)loader.getController()).setScene(scene);
    }
    
    public void allNotifications() {
    	User currentuser = jellyFacade.getUser(emailUser);
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/user/AllNotificationsUI.fxml"));
            Parent root;
            root = loader.load();
            this.scene.setRoot(root);
			((AllNotificationsController)loader.getController()).emailUser = emailUser;
			((AllNotificationsController)loader.getController()).currentUser = currentuser;
            ((AllNotificationsController)loader.getController()).setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
}
