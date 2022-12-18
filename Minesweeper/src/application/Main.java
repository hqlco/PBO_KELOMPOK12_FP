package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Home main = new Home();
			primaryStage = main.getMainStage();
			primaryStage.setTitle("MINESWEEPER");
			primaryStage.show();
		} catch (Exception e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
