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

	public Project project;
	private JellyFacade jellyFacade = new JellyFacade();
	protected String emailUser;
	protected User currentUser;
	private Scene scene;

	@FXML
    private VBox window;
	
    @FXML
    private GridPane notificationsGridPane;

    @FXML
	public TextField userMailField;
	
	ArrayList<Notification> notifications;

	/**
	 * gets the attribute emailUser
	 * @return the emailUser attribute
	 */
	public String getEmailUser() {
		return emailUser;
	}

	/**
	 * sets the attribute emailUser
	 * @param emailUser the emailUser value to set
	 */
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	/**
	 * sets the attribute scene
	 * displays the notification of the connected user
	 * @param scene
	 */
	public void setScene(Scene scene) {
		notifications = jellyFacade.getUnreadNotificationList(jellyFacade.getUser(emailUser));
		User currentuser = jellyFacade.getUser(emailUser);
        notificationsGridPane.setPrefHeight(460);
        notificationsGridPane.setPrefWidth(600);
        for (int i = 0; i < notifications.size(); i++) {
            VBox vbox = new VBox();
            System.out.println(notifications.get(i).getOriginator().getUser().getMailUser());
            vbox.getChildren().add(new Label("Sender : " + notifications.get(i).getOriginator().getUser().getMailUser()));
            vbox.getChildren().add(new Label("Project : " + notifications.get(i).getOriginator().getProject().getProjectName()));
            vbox.getChildren().add(new Label("Message : " + notifications.get(i).getMessage()));
            try {
    			Collaborator[] projectCollaborators = new Collaborator[notifications.get(i).getOriginator().project.getCollaborators().size()];
    			for(int k = 0; k < notifications.get(i).getOriginator().project.getCollaborators().size();k++) {
    				projectCollaborators[k] = (Collaborator) notifications.get(i).getOriginator().project.getCollaborators().toArray()[k];
    			}
    			int j = 0;
    			boolean invitation = true;
    			while(j < projectCollaborators.length && invitation) {
    				
    				if(projectCollaborators[j].getUser().getMailUser().equals(currentuser.getMailUser())) {
    					invitation = false;
    					Button seeProject = new Button("View project");
    					seeProject.setOnAction( e->{
    						try {
    							FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectUI.fxml"));
    							Parent root;
    							root = loader.load();
    							this.scene.setRoot(root);
    							((ProjectPageController)loader.getController()).setScene(scene);
    							vbox.getChildren().add(seeProject);						
    						} catch (IOException e2) {
    							e2.printStackTrace();
    						}
    					});
    				}
    			}
    			if(invitation) {
					Button joinProject = new Button("Join this project");
					final int index = i;
					joinProject.setOnAction( e-> {
							currentUser.joinProject(notifications.get(index).getOriginator().getProject());
						});

					vbox.getChildren().add(joinProject);
	
				}
            }
			catch(NullPointerException e3) {
				e3.printStackTrace();
			}
	        jellyFacade.changeStateNotification(notifications.get(i).getIdNotification(), jellyFacade.getUser(emailUser));

		}
        
        this.scene = scene;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/home.fxml"));
        Parent root;
        root = loader.load();
        this.scene.setRoot(root);
        ((HomeController)loader.getController()).connectedUser = jellyFacade.getUser(emailUser);
        ((HomeController)loader.getController()).jellyFacade = jellyFacade;
		((HomeController)loader.getController()).notificationNumber.setText(""+jellyFacade.getUnreadNotificationList(jellyFacade.getUser(emailUser)).size());
        ((HomeController)loader.getController()).setScene(scene);
    }

	/**
	 * calls the JavaFX component "AllNotificationsUI" to display the notifications page
	 * @see AllNotificationsController#emailUser
	 * @see AllNotificationsController#currentUser
	 * @see AllNotificationsController#setScene(Scene)
	 * @throws IOException
	 */
    public void allNotifications() {
    	User currentuser = jellyFacade.getUser(emailUser);
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jelly/ui/view/user/AllNotificationsUI.fxml"));
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
