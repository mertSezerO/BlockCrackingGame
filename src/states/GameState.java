package states;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import application.Cell;
import application.Level;
import application.Main;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

// Ahmet Eren Çolak - 150120019
// Mert Sezer Oktay - 150120017

public class GameState extends State {
	private Label levelLabel;
	private Label scoreLabel;
	private Label highscoreLabel;
	
	private GridPane grid;
	
	private Label infoLabel;
	private Button nextButton;
	private Button saveButton;
	private Button backButton;
	
	private Level level;
	
	public GameState(Stage stage, int levelIndex) {
		super(stage, new BorderPane());
		level = Main.levels.get(levelIndex);
		level.setToDefault();
		
		updateNodes();
		setGrid();
	}
	
	public void initState() {
		level = Main.levels.get(0);
		
		levelLabel = new Label("Level #" + level.getId());
		scoreLabel = new Label("" + level.getScore());
		highscoreLabel = new Label("High Score: " + level.getHighscore());
		
		infoLabel = new Label("No action has been taken");
		
		nextButton = new Button("Next");
		nextButton.setVisible(level.isDone());
		nextButton.setOnAction(e -> {
			if (level.getId() >= Main.levels.size())
				return;
			
			level = Main.levels.get(level.getId());
			level.setToDefault();
			
			updateNodes();
			updateGrid();
		});
		
		backButton = new Button("Back");
		backButton.setOnAction(e -> {
			stage.setScene(new MenuState(stage));
		});
		
		saveButton = new Button("Save");
		saveButton.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			File f = fc.showSaveDialog(stage);
			try (PrintWriter writer = new PrintWriter(f)) {
				for (Level level : Main.levels) {
					writer.print(String.format("level%d %b %d\n", level.getId(), level.isDone(), level.getHighscore()));
				}
				writer.flush();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
		
		BorderPane topPane = new BorderPane();
		topPane.setLeft(levelLabel);
		topPane.setCenter(scoreLabel);
		topPane.setRight(highscoreLabel);
		((BorderPane)getRoot()).setTop(topPane);
		
		grid = new GridPane();
		grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double x = event.getX();
				double y = event.getY();
				
				//Convert mouse positions to array positions (rows and columns)
				x /= Cell.CELL_SIZE;
				y /= Cell.CELL_SIZE;
				
				if (level.breakCell((int) x, (int) y)) {
					updateGrid();
					
					if (level.checkCompleted()) {
						nextButton.setVisible(true);
						//If score is bigger than highscore update highscore
						if (level.getScore() > level.getHighscore()) {
							level.setHighscore(level.getScore());
							highscoreLabel.setText("High Score: " + level.getHighscore());
						}
					}
				}
				
				infoLabel.setText(level.getInfo());
				scoreLabel.setText("" + level.getScore());
			}
		});
		
		((BorderPane)getRoot()).setCenter(grid);
	
		BorderPane bottomPane = new BorderPane();
		bottomPane.setLeft(infoLabel);
		bottomPane.setRight(nextButton);
		
		BorderPane container = new BorderPane();
		container.setLeft(backButton);
		container.setCenter(saveButton);
		container.setRight(nextButton);
		
		bottomPane.setBottom(container);
		((BorderPane)getRoot()).setBottom(bottomPane);
	}
	
	private void setGrid() {
		for (int y = 0; y < level.getCells().length; y++) {
			for (int x = 0; x < level.getCells()[y].length; x++) {
				grid.add(level.getCells()[y][x], x, y);
			}
		}
	}
	
	private void updateGrid() {
		grid.getChildren().clear();
		setGrid();
	}
	
	private void updateNodes() {
		levelLabel.setText("Level #" + level.getId());
		scoreLabel.setText("" + level.getScore());
		highscoreLabel.setText("High Score: " + level.getHighscore());
		nextButton.setVisible(level.isDone());
	}
}
