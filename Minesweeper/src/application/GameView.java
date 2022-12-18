
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
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Label;
import javafx.application.Platform;
import asset.Labels;
import java.text.DecimalFormat;

public class GameView {

	private static int TILE_SIZE;
    private static final int W = 800;
    private static final int H = 600;
    private Stage mainStage;
    private static int X_TILES;
    private static int Y_TILES;
    private Scene scene;
    private Pane root;
    private Stage MainMenu;
    private Tile[][] grid;
    
    private double time;
	Timer timer;
	private static final DecimalFormat df = new DecimalFormat("0.00");
    
    public GameView(Stage menu, int size) {
        TILE_SIZE = size;
        X_TILES = W / TILE_SIZE;
        Y_TILES = H / TILE_SIZE;
        grid = new Tile[X_TILES][Y_TILES];
    	root = new Pane();
    	scene = new Scene(root);
    	mainStage = new Stage();
    	mainStage.setScene(scene);
        this.MainMenu = menu;
        MainMenu.hide();
        mainStage.show();
    	createContent();
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
    
    private Parent createContent() {
        root.setPrefSize(W+200, H);

        for (int y = 0; y < Y_TILES; y++) {//membuat isi angka
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.2);

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

        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text text = new Text();

        public Tile(int x, int y, boolean hasBomb) {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;

            border.setStroke(Color.LIGHTGRAY);

            text.setFont(Font.font(18));
            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);

            getChildren().addAll(border, text);

            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> open());
        }

        public void open() {
            if (isOpen)
                return;

            if (hasBomb) {//melakukan reset game
               System.out.println("Game Over");
               scene.setRoot(createContent());
               timer.cancel();
               MainMenu.show();
               mainStage.close();
               return;
            }

            isOpen = true;
            text.setVisible(true);
            border.setFill(null);

            if (text.getText().isEmpty()) {//untuk membuka sekitarnya jika kosong isinya
                getNeighbors(this).forEach(Tile::open);
            }
        }
    }
}
