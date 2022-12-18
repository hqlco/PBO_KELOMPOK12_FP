package asset;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class msSubscene extends SubScene{
	private  boolean isHidden;
	
	
	public msSubscene() {
		super(new AnchorPane(), 600, 800);
		prefWidth(600);
		prefHeight(800);
		isHidden = true ;
		setLayoutX(600);
		setLayoutY(0);
		
	}
	
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if (isHidden) {
			
			transition.setToX(-600);
			isHidden = false;
			
		} else {
			
			transition.setToX(0);
			isHidden = true ;
		}
		
		
		transition.play();
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}

}
