package asset;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class labelInfo extends Label{
	public labelInfo(String text) {
		setPrefWidth(130);
		setPrefHeight(10);
//		BackgroundImage backgroundImage = new BackgroundImage(new Image("/view/resources/blue_info_label.png", 130, 50, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
//		setBackground(new Background(backgroundImage));
		setStyle("-fx-border-color:lightgray; -fx-background-color: gray; -fx-text-fill: black;");
		setAlignment(Pos.CENTER);
		setPadding(new Insets(10, 10, 10, 10));
		setFont(Font.font("Cambria", 18));
		setText(text);
	}
}