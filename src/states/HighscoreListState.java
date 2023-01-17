package states;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.*;

import application.Level;
import application.Main;

//Ahmet Eren Çolak - 150120019
//Mert Sezer Oktay - 150120017

public class HighscoreListState extends State {

	public HighscoreListState(Stage stage) {
		super(stage, new BorderPane());
		initState();
	}

	@Override
	protected void initState() {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		
		for(int i=0; i< Main.levels.size(); i++) {
			Level l = Main.levels.get(i);
			if(l.isDone()) {
				Text text= new Text(15,15,""+ (i + 1) + ". " + l.getHighscore());
				vbox.getChildren().add(text);
			} 	
			else {
				Text text = new Text(15,15,""+ (i + 1) + ". -----");
				vbox.getChildren().add(text);
			}
		}
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			stage.setScene(new MenuState(stage));
		});
		
		vbox.setSpacing(10);
		vbox.getChildren().add(backButton);
		((BorderPane) getRoot()).setCenter(vbox);
	}
}