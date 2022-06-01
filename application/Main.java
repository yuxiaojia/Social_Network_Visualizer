/**
 * Filename: Main.java 
 * Project: Social Network 
 * Authors: ateam120
 * 
 */
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args;

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_TITLE = "Social Network";

    private SocialNetwork network = new SocialNetwork();
    // Main layout is Border Pane example (top,left,center,right,bottom)
    BorderPane border = new BorderPane();
    Text info = new Text();
    Button buttonClose;
    
    /**
     * Sets background color for the GUI
     */
    private void setBackgroundColor() {
        border.setStyle("-fx-background-color: #BFBFBF");
    }

    /**
     * Sets up GUI's top part
     */
    private void setUpTopBox() {
    	//button initialization
		Button dis = new Button("Friend Map: visualizer");
		dis.setMinSize(200, 40);
		dis.setTranslateY(80);
		dis.setTranslateX(500);
		dis.setStyle("-fx-background-color: #FFC000; "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0, 1 );");

		// sets dis button on action
		dis.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// pop up new window if dis button is pressed
				GridPane gridPane = new GridPane();
				// return error info if press dis without set central user
				if(network.getCentralUser() == null) {
					  infoMessage("Please set a central user first."); 
					  return;
				  }else {
				   refresh(gridPane);
				  }
				ArrayList<Button> buttons = new ArrayList<Button>();			
				BorderPane secondaryLayout = new BorderPane();
				Scene secondScene = new Scene(gridPane, 800, 600);
				// close button setting
				buttonClose = new Button("Quit");
				buttonClose.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						Stage stage = (Stage) buttonClose.getScene().getWindow();// set the button
						stage.close();// to close the window
					}
				});
				
				secondaryLayout.setStyle("-fx-background-color: #BFBFBF");
				secondaryLayout.setBottom(buttonClose);
				// New window
				Stage newWindow = new Stage();
				newWindow.setTitle("Friend Relationship");
				newWindow.setScene(secondScene);
				newWindow.show();
			}
		});
        border.setTop(dis);
    }
    
    /**
     * Refreshes the gidpane when friend is clicked
     * 
     * @param gridPane
     */
    private void refresh(GridPane gridPane) {
    	// display central user
    	ArrayList<Button> buttons = new ArrayList<Button>();
    	// display central user's friend
    	Button center = new Button("Center user: " + network.getCentralUser());
		for (int i = 0; i < network.getCenterFriend(network.getCentralUser()).size(); i++) {
			Button button = new Button(network.getCenterFriend(network.getCentralUser()).get(i));
			buttons.add(button);
		}
		Label lable = new Label("Friend(s): ");
		gridPane.add(lable, 1, 0);
		gridPane.add(center, 0, 0, 1, 1);
		for (int i = 0; i < buttons.size(); i++) {
			gridPane.add(buttons.get(i), 2, i, 1, 1);
		}
		// gridPane setting
        gridPane.setStyle("-fx-background-color: #BFBFBF");
        Button central = new Button("Set Central User");
        central.setStyle("-fx-background-color: #FFC000; "
            + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
        // set central user
        TextField centralUser = new TextField();
        centralUser.setMaxWidth(100);
        central.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String central = centralUser.getText();
                if( network.setCentral(central) == false) {
             	   infoMessage("User Does not Exist!");
                }else {
                 infoMessage("Set " + central + " as central user");
                }
                gridPane.getChildren().clear();
                refresh(gridPane);
            }
        });
        
        HBox hb = new HBox();
        hb.getChildren().addAll(centralUser, central);
        hb.setSpacing(5);
        hb.setTranslateX(580);
        gridPane.getChildren().addAll(hb);
		// make friends clickable
		for (int  i = 0; i < buttons.size(); i++) {
			final Integer innerMi = new Integer(i);
			buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                	network.setCentral(buttons.get(innerMi).getText());
                	gridPane.getChildren().clear();
                	refresh(gridPane);	
            	}
			});
		}
    }
    
    /**
     * Sets up information pop-up window
     * 
     * @param message information for user
     */
    private void infoMessage(String message) {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Message");
        info.setContentText(message);
        info.showAndWait();
    }

    /**
     * Sets up information pop-up window
     * 
     * @param title title of pop-up window
     * @param header header of pop-up window
     * @param message information for user
     */
    private void infoMessage(String title,String header, String message) {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle(title);
        info.setHeaderText(header);
        info.setContentText(message);
        info.showAndWait();
    }
    
    /**
     * Sets up warning message pop-up window
     * 
     * @param header header of the pop-up window
     * @param message for user
     */
    private void warningMessage(String header, String message) { 
    	 Alert info = new Alert(AlertType.WARNING); 
    	 info.setTitle("WARNING"); 
    	 info.setHeaderText(header); 
    	 info.setContentText(message); 
    	 info.showAndWait(); 
    	 } 
    
    /**
     * Sets up GUI's left part
     */
    private void setUpLeftBox() {
        try {
            VBox left = new VBox();
            VBox vb = new VBox();
            // social network image
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
            // info TextField
            // Text info = new Text();
            info.setText(network.updateInfo());
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
            // add user
            Button addUser = new Button("Add User");
            addUser.setStyle("-fx-background-color: #FFC000; "
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
            addUser.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    String name = username.getText();
                    if (name != null && !name.isEmpty()) {
                        if (network.addUser(name) == true) {
                        } else {
                            warningMessage("Warning","can not add duplicate name");
                        }
                    } else {
                        warningMessage("Warning","Write names in order to add user");
                    }
                    info.setText(network.updateInfo());
                }
            });
            // remove user
            Button removeUser = new Button("Remove User");
            removeUser.setStyle("-fx-background-color: #FFC000; "
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
            removeUser.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    String name = username.getText();
                    if (name != null && !name.isEmpty()) {
                        if (network.removeUser(name) == true) {
                            infoMessage("removed user " + name);
			    info.setText(network.updateInfo());
                        } else {
                            warningMessage("Remove User failed", name + " does not in the network");

                        }
                    } else {
                        warningMessage("Warning", "Remove user not working");
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
            // add friendship
            Button addFriendship = new Button("Add Friendship");
            addFriendship.setStyle("-fx-background-color: #FFC000; "
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
            Button removeFriendship = new Button("Remove Friendship");
            removeFriendship.setStyle("-fx-background-color: #FFC000; "
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");

            friendshipButtons.getChildren().addAll(addFriendship,
                removeFriendship);
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
                    if (f1 != null && !f1.isEmpty() && f2 != null
                        && !f2.isEmpty()) {
                        network.addFriends(f1, f2);
                        infoMessage(
                            "added a friendship between " + f1 + " and " + f2);
                    }
                    info.setText(network.updateInfo());
                }
            });
            removeFriendship.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    String f1 = friend1.getText();
                    String f2 = friend2.getText();
                    if (f1 != null && !f1.isEmpty() && f2 != null
                        && !f2.isEmpty()) {
                        network.removeFriends(f1, f2);
                        infoMessage("removed a friendship between " + f1
                            + " and " + f2);
                    }
                    info.setText(network.updateInfo());
                }
            });
            removeAll.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					network.removeAll();
					infoMessage("Remove all the information!");
					info.setText(network.updateInfo());
					
				}
            	
            });
            // Vbox
            vb.setSpacing(20);
            vb.setPadding(new Insets(0, 0, 0, 50));
            vb.getChildren().addAll(r, vt1, userButtons, r3, vt2, friendship,
                friend2, removeAll);
            vb.setAlignment(Pos.CENTER);
            // bigger left VBox
            left.setSpacing(5);
            left.getChildren().addAll(imageView,info, vb);
            left.setAlignment(Pos.TOP_CENTER);

            border.setLeft(left);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets up GUI's right part
     * 
     * @param primaryStage
     */
    private void setUpRightBox(Stage primaryStage) {
        try {
            VBox vb2 = new VBox();
            // mutual friends
            HBox muser = new HBox();
            muser.setSpacing(5);
            TextField muser1 = new TextField();
            TextField muser2 = new TextField();
            muser.getChildren().addAll(muser1, muser2);
            Button mutual = new Button("List Mutual Friends");
            mutual.setStyle("-fx-background-color: #FFC000; "
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
            mutual.setAlignment(Pos.CENTER_RIGHT);
            mutual.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                	String f1 = muser1.getText();
                    String f2 = muser2.getText();
                    Set<String> mFriend = new HashSet<String>();
                	if (f1 != null && !f1.isEmpty() && f2 != null && !f2.isEmpty()) {
                		mFriend = network.getMutualFriends(f1, f2);
                        infoMessage("Mutual Friends","Mutual Friends:",mFriend.toString());
                    } else {
                        warningMessage("Warning","Fill in the names to find our their mutual friends");
                    }
                    
                }
            });
            
            // Textfield 1 for user
            TextField ld = new TextField();
            ld.setPrefHeight(30);

            ld.setPrefWidth(300);
            ld.setPrefColumnCount(10);
            ld.setPadding(new Insets(0, 0, 0, 0));

            // Empty space
            Region r2 = new Region();
            r2.setPrefHeight(30);
            r2.setPrefWidth(200);

            // Textfield 2
            TextField ex = new TextField();
            ex.setPrefHeight(30);
            ex.setPrefWidth(300);
            ex.setPrefColumnCount(10);
            ex.setPadding(new Insets(0, 0, 0, 0));
            
            // export file
            Button exp = new Button("Export File");
            exp.setMinSize(100, 30);
            exp.setTranslateY(0);
            exp.setTranslateX(0);
            exp.setStyle("-fx-background-color: #FFC000; "
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
            exp.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                	String s1 = ex.getText();
                    if (s1 != null && !s1.isEmpty()) {
                    	File file = new File(s1);
                    	if (file.exists()) {
                            try {
                                network.saveToFile(file);
                                infoMessage("saved");
                            } catch (Exception e) {
                                warningMessage("Error","Error occured when save");
                            }
                        }
                    }  else {
                        warningMessage("Error", "File doesn't exist or need to be written" + s1);
                    }
                }
            });
            // load file
            Button load = new Button("Load File");
            load.setMinSize(100, 30);
            load.setTranslateY(0);
            load.setTranslateX(0);
            load.setStyle("-fx-background-color: #FFC000; "
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
            load.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                	String s2 = ld.getText();
                    if (s2 != null && !s2.isEmpty()) {
                    	File file = new File(s2);
                    	if (file.exists()) {
                            try {
                            	network.loadFromFile(file);
                                infoMessage("loaded");
                                info.setText(network.updateInfo());
                            } catch (Exception e) {
                                warningMessage("Error","Error occured when save");
                            }
                        }
                    } else {
                        warningMessage("Error", "File doesn't exist or need to be written" + s2);
                    }
                }
            });
            // shortest path
            Button shortestPath = new Button("Show Shortest Path");

            shortestPath.setStyle("-fx-background-color: #FFC000; "
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");;
            shortestPath.setAlignment(Pos.TOP_RIGHT);
            HBox shortest = new HBox();
            shortest.setSpacing(5);
            TextField user1 = new TextField();
            TextField user2 = new TextField();
            shortest.getChildren().addAll(user1, user2);
            shortestPath.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    String f1 = user1.getText();
                    String f2 = user2.getText();
                    if (f1 != null && !f1.isEmpty() && f2 != null && !f2.isEmpty()) {
	                    List<String> path = new ArrayList<String>();
	                    path = network.getShortestPath(f1, f2);
	                    String header = "Shortest Path between "+f1+" and "+f2+":";
	                    infoMessage("Shortest Path",header,path.toString());
                    } else {
                        warningMessage("Error", "Fill in the names to obtain the shortest path");
                    }
                }
            });
            // set central user
            Button central = new Button("Set Central User");
            central.setStyle("-fx-background-color: #FFC000; "
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
            TextField centralUser = new TextField();
            centralUser.setMaxWidth(100);
            central.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    String central = centralUser.getText();
                    if( network.setCentral(central) == false) {
                 	    warningMessage("Error", "User Does not Exist!");
                    }else {
                    	infoMessage("Set " + central + " as central user");
                   }
                }
            });
            // Vbox2
            vb2.setSpacing(20);
            vb2.setPadding(new Insets(190, 40, 0, 0));
            vb2.getChildren().addAll(muser,mutual,shortest,
                shortestPath, centralUser, central, ld, load, ex, exp, r2);
            vb2.setTranslateY(25);
            vb2.setAlignment(Pos.TOP_CENTER);

            border.setRight(vb2);

        } catch (Exception e) {
        	warningMessage("Error","Error");
        }
    }
    
    /**
     * The main entry point for all JavaFX applications
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        setBackgroundColor();
        setUpLeftBox();
        setUpRightBox(primaryStage);
        setUpTopBox();
        // Add the vertical box to the center of the root pane
        Scene mainScene = new Scene(border, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    
    /**
     * This method is called when the application should stop, and provides 
     * a convenient place to prepare for application exit and destroy resources
     */
    @Override
    public void stop() {
           // infoMessage("Stage is closing");
            ButtonType saveButtonType = new ButtonType("Save", ButtonData.YES);
            ButtonType ENSButtonType =
                new ButtonType("Exit without Save", ButtonData.NO);
            TextInputDialog td = new TextInputDialog("File name or path");
            td.setHeaderText("Save the progress?");
            td.getDialogPane().getButtonTypes().set(0, saveButtonType);
            td.getDialogPane().getButtonTypes().set(1, ENSButtonType);

            Button save =
                (Button) td.getDialogPane().lookupButton(saveButtonType);
            save.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    // ok was pressed
                    // System.out.print("save pressed");
                    File file = new File(td.getEditor().getText());
                    if (file.exists()) {
                        try {
                            network.saveToFile(file);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        infoMessage("Successfully saved, goodbye!");
                        // td.showAndWait();
                    } else {
                        infoMessage("File Doesn't exists, save failed!");
                    }
                    // Save file
                }
            });
            Button ENS =
                (Button) td.getDialogPane().lookupButton(ENSButtonType);
            ENS.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    infoMessage("Quitting, goodbye!");
                }
            });
            td.showAndWait();
        } 

    /**
     * Launches the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
