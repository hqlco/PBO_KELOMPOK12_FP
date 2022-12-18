
package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Label;
import javafx.application.Platform;
import asset.Labels;
import java.text.DecimalFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import asset.Buttons;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

public class GameView {

	private static int TILE_SIZE;
    private static final int W = 800;
    private static final int H = 600;
    private Stage mainStage;
    private static int X_TILES;
    private static int Y_TILES;
    private Scene scene;
    private AnchorPane root;
    private Stage home;
    private Tile[][] grid;
    List<Image> card = new ArrayList<>();
    private double time;
	Timer timer;
	private static final DecimalFormat df = new DecimalFormat("0.00");
    private Buttons backbtn;
    private Buttons againbtn;
    
    public GameView(Stage home, int size) {
        TILE_SIZE = size;
        createImage();
        X_TILES = W / TILE_SIZE;
        Y_TILES = H / TILE_SIZE;
        grid = new Tile[X_TILES][Y_TILES];

    	createContent();
    	scene = new Scene(root);
    	mainStage = new Stage();
    	mainStage.setScene(scene);
	    
        this.home = home;
        this.home.hide();
        
        mainStage.show();
    }
    
    private void createButtons() {
    	backbtn = backButton(W+100, 250);
		againbtn = againButton(W+100, 300);
		root.getChildren().add(backbtn);
		root.getChildren().add(againbtn);
	}
    
    public Stage getMainStage() {
		return mainStage;
	}
    
    private Label timeCounter(int x, int y) {
    	time = 0.0;
    	Labels counter = new Labels(String.valueOf(df.format(time)));
    	counter.setLayoutX(x);
    	counter.setLayoutY(y);
    	
		timer = new Timer();
    	timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    time+=0.01;
			    Platform.runLater(new Runnable(){
					@Override
					public void run() {
						counter.setText(String.valueOf(df.format(time)));
					}
			    });
			  }
			}, 0, 10);
    	return counter;
    }
    
    private Buttons backButton(int x, int y) {
    	Buttons back = new Buttons("HOME");
    	back.setLayoutX(x);
    	back.setLayoutY(y);
    	back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				home.show();
			}
			
		});
    	back.setVisible(false);
    	return back;
    }
    
    private Buttons againButton(int x, int y) {
    	Buttons back = new Buttons("PLAY AGAIN");
    	back.setLayoutX(x);
    	back.setLayoutY(y);
    	back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				scene.setRoot(createContent());
			}
			
		});
    	back.setVisible(false);
    	return back;
    }
    
    private void createImage() {
    	for(int i = 0; i < 13; i++)
        {
            card.add(new Image("/resource/"+i+".png"));
        }
    }
    
    private Parent createContent() {    	
    	root = new AnchorPane();
        root.setPrefSize(W+200, H);

        for (int y = 0; y < Y_TILES; y++) {//membuat isi angka
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.125);

                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y_TILES; y++) {//membuat angka disekitarnya
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];

                if (tile.hasBomb)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0)//utnuk skip
                    tile.text.setText(String.valueOf(bombs));
            }
        }
        
        root.getChildren().add(timeCounter(W+100, 100));
    	
    	createButtons();

        return root;
    }

    private List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();

        // ttt
        // tXt
        // ttt

        int[] points = new int[] {
              -1, -1,
              -1, 0,
              -1, 1,
              0, -1,
              0, 1,
              1, -1,
              1, 0,
              1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < X_TILES
                    && newY >= 0 && newY < Y_TILES) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }
    
    private class Tile extends StackPane {//kelas untuk emngatur kotak
        private int x, y;
        private boolean hasBomb;
        private boolean isOpen = false;
	private ImageView imgTile= new ImageView(card.get(10));
        private Text text = new Text();

        public Tile(int x, int y, boolean hasBomb) {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;

            text.setFont(Font.font(18));
            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);
            imgTile.setFitHeight(TILE_SIZE);
            imgTile.setFitWidth(TILE_SIZE);
            getChildren().addAll(imgTile, text);
	
            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> open());
        }
        
        public void isbomb() {
        	if (hasBomb) {        		
        		imgTile.setImage(card.get(9));
        	}
        	setOnMouseClicked(null);
        }

        public void open() {
            if (isOpen)
                return;

            if (hasBomb) {//melakukan reset game
               System.out.println("Game Over");
               timer.cancel();
               showBomb();
               backbtn.setVisible(true);
               againbtn.setVisible(true);
               return;
            }

            isOpen = true;
            imgTile.setImage(card.get(0));
	    if(!text.getText().isEmpty()) {
            	if(Integer.valueOf(text.getText()) == 1) imgTile.setImage(card.get(1));
                else if(Integer.valueOf(text.getText()) == 2) imgTile.setImage(card.get(2));
                else if(Integer.valueOf(text.getText()) == 3) imgTile.setImage(card.get(3));
                else if(Integer.valueOf(text.getText()) == 4) imgTile.setImage(card.get(4));
                else if(Integer.valueOf(text.getText()) == 5) imgTile.setImage(card.get(5));
                else if(Integer.valueOf(text.getText()) == 6) imgTile.setImage(card.get(6));
                else if(Integer.valueOf(text.getText()) == 7) imgTile.setImage(card.get(7));
                else if(Integer.valueOf(text.getText()) == 8) imgTile.setImage(card.get(8));
            }
            if (text.getText().isEmpty()) {//untuk membuka sekitarnya jika kosong isinya
                getNeighbors(this).forEach(Tile::open);
            }
        }
    }
    
    private void showBomb() {

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
            	grid[x][y].isbomb();
            }
        }
    }
}
