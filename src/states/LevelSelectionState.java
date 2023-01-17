package states;

import application.Level;
import application.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//Ahmet Eren Çolak - 150120019
//Mert Sezer Oktay - 150120017

public class LevelSelectionState extends State{

	public LevelSelectionState(Stage stage) {
		super(stage, new BorderPane());
	}

	@Override
	protected void initState() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			stage.setScene(new MenuState(stage));
		});
		
		for (int i = 0; i < Main.levels.size(); i++) {
			Level level = Main.levels.get(i);
			Button levelButton = new Button("" + level.getId());
			levelButton.setPrefSize(40, 40);
			levelButton.setFont(new Font(18));
			levelButton.setStyle(level.isDone() ? "-fx-color: #00b37a;" : "-fx-color: crimson;");
			levelButton.setDisable(i == 0 ? false : !Main.levels.get(i - 1).isDone());
			levelButton.setOnAction(e -> {
				stage.setScene(new GameState(stage, level.getId() - 1));
			});
			
			grid.add(levelButton, i % 3, i / 3);
		}
		
		((BorderPane)getRoot()).setCenter(grid);
		((BorderPane)getRoot()).setBottom(backButton);
	}

}
