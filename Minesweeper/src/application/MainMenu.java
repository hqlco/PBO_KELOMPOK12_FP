package application;

import asset.Buttons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenu {
    private static final int height = 800;
    private static final int width = 600;

    private AnchorPane root;
    private Scene scene;
    private Stage stage;


    public MainMenu() {
        root = new AnchorPane();
        scene = new Scene(root, width, height);
        stage = new Stage();
        stage.setScene(scene);
        root.getChildren().add(gameButton("EASY", 60, 210, 100));
        root.getChildren().add(gameButton("MEDIUM", 40, 210, 200));
        root.getChildren().add(gameButton("HARD", 20, 210, 300));
        root.getChildren().add(backButton("EXIT", 20, 210, 400));
    }

    public Stage getMainStage() {
        return stage;
    }

    private Buttons gameButton(String label, int size, int x, int y) {
        Buttons button = new Buttons(label);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                new GameView(stage);
            }

        });
        return button;
    }

    private Buttons backButton(String label, int size, int x, int y) {
        Buttons backbtn = new Buttons(label);
        backbtn.setLayoutX(x);
        backbtn.setLayoutY(y);
        backbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }

        });
        return backbtn;
    }

}
