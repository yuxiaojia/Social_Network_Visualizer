package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "Social Network";
	
	private SocialNetwork network = new SocialNetwork();
	// Main layout is Border Pane example (top,left,center,right,bottom)
	BorderPane border = new BorderPane();
	
	
	Button buttonClose;
	private void setBackgroundColor() {
		border.setStyle("-fx-background-color: #BFBFBF");
	}
	
	private void setUpBottomBox() {
		HBox feedback = new HBox();
		Label feedLabel = new Label("Feedback");
		TextField feedText = new TextField();

		feedLabel.setStyle("-fx-background-color: #FFC000");
		feedLabel.setPrefSize(60, 25);
		feedText.setPrefWidth(750);
		feedback.setSpacing(10);
		feedback.getChildren().addAll(feedLabel, feedText);
		border.setBottom(feedback);
	}
	
	private void setUpTopBox() {
		// set top menu
		Button button1 = new Button("Save And Quit");
		Button button2 = new Button("Quit Without Save");

		button1.setStyle("-fx-background-color: #FFC000; "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
		button2.setStyle("-fx-background-color: #FFC000; "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");

		button1.setTranslateX(595);
		button2.setTranslateX(595);

		HBox hbox = new HBox(button1, button2);
		border.setTop(hbox);
	}
	
	private void setUpLeftBox() {
		try {
			VBox left = new VBox();
			VBox vb = new VBox();
	
	
			FileInputStream inputImage = null;
	
			inputImage = new FileInputStream("ATEAM.png");
	
			Image image = new Image(inputImage);
			ImageView imageView = new ImageView(image);
	
			imageView.setFitHeight(120);
			imageView.setFitWidth(400);
			Label user = new Label("User: ");
			
			// Empty space
			Region r = new Region();
			r.setPrefHeight(10);
			r.setPrefWidth(200);
			// Textfield 1 for user
			TextField username = new TextField();
			username.setPrefHeight(40);
			// t1.setMaxHeight(40);
			username.setPrefWidth(200);
			username.setPrefColumnCount(10);
			username.setPadding(new Insets(0, 0, 0, 0));
			VBox vt1 = new VBox();
			vt1.getChildren().addAll(user, username);
	
			// HBox containing all buttons about user
			HBox userButtons = new HBox();
			userButtons.setSpacing(5);
			Button addUser = new Button("Add User");
			addUser.setStyle("-fx-background-color: #FFC000; "
					+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
			addUser.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					String name = username.getText();
					if (name != null && !name.isEmpty()) {
						network.addUser(name);
						System.out.println("added a user "+name);
					}
				}
			});
			Button removeUser = new Button("Remove User");
			removeUser.setStyle("-fx-background-color: #FFC000; "
					+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
			removeUser.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					String name = username.getText();
					if (name != null && !name.isEmpty()) {
						network.removeUser(name);
						System.out.println("removed a user "+name);
					}
				}
			});
			userButtons.getChildren().addAll(addUser, removeUser);
			userButtons.setAlignment(Pos.CENTER_RIGHT);
	
			// Empty space
			Region r3 = new Region();
			r3.setPrefHeight(30);
			r3.setPrefWidth(200);
	
			// Textfield 2
			TextField friend1 = new TextField();
			friend1.setPrefHeight(40);
			friend1.setPrefWidth(150);
			friend1.setPrefColumnCount(10);
			friend1.setPadding(new Insets(0, 0, 0, 0));
	//		Label user1 = new Label("user1: ");
	//		HBox u1 = new HBox();
	//		u1.getChildren().addAll(user1, t2);
			Label rela = new Label("Relationship: ");
			VBox vt2 = new VBox();
			vt2.getChildren().addAll(rela, friend1);
	
			// HBox for friendship
			HBox friendship = new HBox();
			friendship.setSpacing(5);
			FileInputStream input;
			input = new FileInputStream("FriendshipArrow.png");
			Image image2 = new Image(input); // ImageView of an arrow of friendship
			ImageView imageView2 = new ImageView(image2);
			imageView2.setFitHeight(70);
			imageView2.setFitWidth(50);
			VBox friendshipButtons = new VBox(); // Vbox to contain all button about friendship
			friendshipButtons.setSpacing(5);
	
			Button addFriendship = new Button("Add Friendship");
			addFriendship.setStyle("-fx-background-color: #FFC000; "
					+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
			Button removeFriendship = new Button("Remove Friendship");
			removeFriendship.setStyle("-fx-background-color: #FFC000; "
					+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
			
			friendshipButtons.getChildren().addAll(addFriendship, removeFriendship);
			friendshipButtons.setAlignment(Pos.CENTER_LEFT);
			friendship.getChildren().addAll(imageView2, friendshipButtons);
			friendship.setAlignment(Pos.CENTER);
	
			// Textfield 3
			TextField friend2 = new TextField();
			friend2.setPrefHeight(40);
			friend2.setPrefWidth(150);
			friend2.setPrefColumnCount(10);
			friend2.setPadding(new Insets(0, 0, 0, 0));
			// Remove All Button
			Button removeAll = new Button("Remove All");
			removeAll.setStyle("-fx-background-color: #FFC000; "
					+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
			
			addFriendship.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					String f1 = friend1.getText();
					String f2 = friend2.getText();
					if (f1 != null && !f1.isEmpty() 
							&& f2 != null && !f2.isEmpty()) {
						network.addFriends(f1,f2);
						System.out.println("added a friendship between "+f1+" and "+f2);
					}
				}
			});
			removeFriendship.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					String f1 = friend1.getText();
					String f2 = friend2.getText();
					if (f1 != null && !f1.isEmpty() 
							&& f2 != null && !f2.isEmpty()) {
						network.removeFriends(f1,f2);
						System.out.println("removed a friendship between "+f1+" and "+f2);
					}
				}
			});
			// Vbox
			vb.setSpacing(5);
			vb.setPadding(new Insets(0, 0, 0, 50));
			vb.getChildren().addAll(r,vt1, userButtons, r3, vt2, friendship, friend2, removeAll);
			vb.setAlignment(Pos.CENTER);
			
			//bigger left VBox
			left.setSpacing(5);
			left.getChildren().addAll(imageView, vb);
			left.setAlignment(Pos.TOP_CENTER);
	
			border.setLeft(left);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setUpRightBox(Stage primaryStage) {
		VBox vb2 = new VBox();

		// Textfield 1 for user
		TextField ld = new TextField();
		ld.setPrefHeight(30);

		ld.setPrefWidth(300);
		ld.setPrefColumnCount(10);
		ld.setPadding(new Insets(0, 0, 0, 0));

//         Empty space
		Region r2 = new Region();
		r2.setPrefHeight(30);
		r2.setPrefWidth(200);

		// Textfield 2
		TextField ex = new TextField();
		ex.setPrefHeight(30);
		ex.setPrefWidth(300);
		ex.setPrefColumnCount(10);
		ex.setPadding(new Insets(0, 0, 0, 0));

		Button dis = new Button("Display");
		dis.setMinSize(200, 40);
		dis.setTranslateY(25);
		dis.setTranslateX(0);
		dis.setStyle("-fx-background-color: #FFC000; "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");

		// pop up new window
		dis.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				Label secondLabel = new Label("Friend relationship");

				BorderPane secondaryLayout = new BorderPane();
				secondaryLayout.setStyle("-fx-background-color: #BFBFBF");

				Button button1 = new Button("Set central user");
				TextField t1 = new TextField();
				t1.setMaxWidth(200);
				button1.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						if ((t1.getText() != null && !t1.getText().isEmpty())) {
							network.setCentral(t1.getText());
							System.out.println("Set central: "+t1.getText());
						}
					}
				});
				Button button2 = new Button("User1");
				Button button3 = new Button("User2");
				button3.setTranslateX(100);
				buttonClose = new Button("Quit");
//     		Button button5 = new Button("Quit Without Save");

				button1.setStyle("-fx-background-color: #FFC000; "
						+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");

				VBox vbox = new VBox(button1, t1);
				secondaryLayout.setTop(vbox);
				Scene secondScene = new Scene(secondaryLayout, 800, 600);
				HBox hbox = new HBox(button2, button3);
				hbox.setTranslateX(230);
				hbox.setTranslateY(230);
				secondaryLayout.setCenter(hbox);

				buttonClose.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						Stage stage = (Stage) buttonClose.getScene().getWindow();// set the button
						stage.close();// to close the window
					}

				});

				secondaryLayout.setBottom(buttonClose);
				// New window
				Stage newWindow = new Stage();
				newWindow.setTitle("Friend Relationship");
				newWindow.setScene(secondScene);

				// Set position of second window, related to primary window.
				newWindow.setX(primaryStage.getX());
				newWindow.setY(primaryStage.getY());

				newWindow.show();

			}

		});

		Button exp = new Button("Export File");
		exp.setMinSize(100, 30);
		exp.setTranslateY(0);
		exp.setTranslateX(0);
		exp.setStyle("-fx-background-color: #FFC000; "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
		exp.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				File file = new File(ex.getText());
				if (file.exists()) {
					network.saveToFile(file);
					System.out.println("saved");
				}
			}
		});

		Button load = new Button("Load File");
		load.setMinSize(100, 30);
		load.setTranslateY(0);
		load.setTranslateX(0);
		load.setStyle("-fx-background-color: #FFC000; "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
		load.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				File file = new File(ld.getText());
				if (file.exists()) {
					network.loadFromFile(file);
					System.out.println("loaded");
				}else {
					System.out.println("File doesn't exist: " + ld.getText());
				}
			}
		});
		
		// Vbox2
		vb2.setSpacing(10);
		vb2.setPadding(new Insets(0, 70, 0, 0));
		vb2.getChildren().addAll(ld, load, ex, exp, r2, dis);
		vb2.setTranslateY(25);
		vb2.setAlignment(Pos.CENTER);

		border.setRight(vb2);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		setBackgroundColor();
		// set feedback
		setUpBottomBox();
		setUpTopBox();
		setUpLeftBox();
		setUpRightBox(primaryStage);

		// Add the vertical box to the center of the root pane
		// root.setCenter(vbox);
		Scene mainScene = new Scene(border, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
