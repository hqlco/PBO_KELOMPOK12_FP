package application;

import java.util.ArrayList;
import java.util.List;
import asset.Buttons;
import asset.label;
import asset.msSubscene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Home {
	private AnchorPane homePane;
	private Scene homeScene;
	private Stage homeStage;
	private static final int HEIGHT = 800;
	private static final int WIDTH = 600;
	List<Buttons> menuButtons;
	private final static int MENU_BUTTON_START_X = 205;
	private final static int MENU_BUTTON_START_Y = 225;
	private final static int MENU_SCORE_START_X = 170;
	private msSubscene scoreSubscene,startSubscene;
	
	public Home() {
		menuButtons = new ArrayList<>();
		homePane= new AnchorPane();
		homeScene = new Scene(homePane, WIDTH, HEIGHT );
		homeStage = new Stage();
		homeStage.setScene(homeScene);
		CreateButtons();
		createLogo();
		createSubScenes();
	}
	
	public Stage getMainStage() {
		return homeStage;
	}
	
	private void createSubScenes() {
		// TODO Auto-generated method stub
		createScoreSubscene();
		createStartSubScene();
	}
	
	private void createStartSubScene() {
		// TODO Auto-generated method stub
		startSubscene = new msSubscene();
		homePane.getChildren().add(startSubscene);
		label Judul = new label("CHOOSE LEVEL");
		Judul.setLayoutX(110);
		Judul.setLayoutY(70);
		startSubscene.getPane().getChildren().add(Judul);
		startSubscene.getPane().getChildren().add(gameButton("EASY", 60, 205, 170));
		startSubscene.getPane().getChildren().add(gameButton("MEDIUM", 40, 205, 245));
		startSubscene.getPane().getChildren().add(gameButton("HARD", 20, 205, 320));
		startSubscene.getPane().getChildren().add(creatBackButton(startSubscene));
		
	}

	private void createScoreSubscene() {
		// TODO Auto-generated method stub
		scoreSubscene = new msSubscene();
		homePane.getChildren().add(scoreSubscene);
		label Judul = new label("HIGH SCORE");
		Judul.setLayoutX(110);
		Judul.setLayoutY(25);
		scoreSubscene.getPane().getChildren().add(Judul);
	}
	
	private Buttons creatBackButton(msSubscene subScene) {
		// TODO Auto-generated method stub
		Buttons backbtn = new Buttons("BACK");
    	backbtn.setLayoutX(MENU_BUTTON_START_X-100);
    	backbtn.setLayoutY(560);
    	backbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				subScene.moveSubScene();
			}
			
		});
		return backbtn;
	}
	
	private Buttons gameButton(String label, int size, int x, int y) {
    	Buttons button = new Buttons(label);
    	button.setLayoutX(x);
    	button.setLayoutY(y);
    	button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new GameView(homeStage, size, label.toLowerCase());
				startSubscene.moveSubScene();
			}
			
		});
		return button;
	}
	
	private void AddMenuButtons(Buttons button) {
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 75);
		menuButtons.add(button);
		homePane.getChildren().add(button);
	}
	
	private void CreateButtons() {
		createStartButton();
		createScoresButton();
		createExitButton();
	}
	
	private void createStartButton() {
		// TODO Auto-generated method stub
		Buttons startButton = new Buttons("PLAY");
		AddMenuButtons(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				MainMenu start = new MainMenu(homeStage);
				startSubscene.moveSubScene();
			}
		});
	}
	
	private void createScoresButton() {
		Buttons scoresButton = new Buttons("SCORE");
		AddMenuButtons(scoresButton);
		
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				scoreSubscene.moveSubScene();;
			}
		});
	}
	
	private void createExitButton() {
		Buttons exitButton = new Buttons("EXIT");
		AddMenuButtons(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				homeStage.close();
			}
		});
	}
	
	
	private void createLogo() {
		ImageView logo = new ImageView("/resource/logoms.png");
		logo.setLayoutX(25);
		logo.setLayoutY(25);
		logo.setFitHeight(140);
		logo.setFitWidth(550);
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
				
			}
		});
		
		homePane.getChildren().add(logo);	
	}
}
