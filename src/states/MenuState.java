package states;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import application.Level;
import application.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//Ahmet Eren Çolak - 150120019
//Mert Sezer Oktay - 150120017

public class MenuState extends State {
	private Button newButton;
	private Button loadButton;
	private Button highscoresButton;
	private Button levelsButton;
	private VBox center;
	
	private Label titleLabel;
	private Label infoLabel;
	private static String infoText = "";
	
	public MenuState(Stage stage) {
		super(stage, new BorderPane());
	}

	@Override
	protected void initState() {
		center = new VBox();
		center.setAlignment(Pos.CENTER);
		center.setSpacing(10);
		
		
		newButton = new Button("New Game");
		center.getChildren().add(newButton);
		loadButton = new Button("Load Game");
		center.getChildren().add(loadButton);
		highscoresButton = new Button("High Scores") ;
		center.getChildren().add(highscoresButton);
		levelsButton = new Button("Levels");
		center.getChildren().add(levelsButton);
		
		center.setPadding(new Insets(-40, 0, 0, 0));
        ((BorderPane)getRoot()).setCenter(center);
        
        newButton.setPrefSize(100, 30);
		newButton.setOnAction(e -> {
			clearLevels();
			stage.setScene(new GameState(stage, 0));
		});
		
		loadButton.setPrefSize(100, 30);
		loadButton.setOnMouseClicked(e -> {
			FileChooser fc = new FileChooser();
			File f = fc.showOpenDialog(stage);
			
			if (!readSaveFile(f)) {
				infoLabel.setText(f.getName() + " either doesn't exist or it's corrupted");
				return;
			}
			else
				infoLabel.setText(f.getName() + " file is loaded succesfully");

			stage.setScene(new GameState(stage, 0));
		});
        
		highscoresButton.setPrefSize(100, 30);
        highscoresButton.setOnAction(e -> {
    		HighscoreListState highscore = new HighscoreListState(stage);
			stage.setScene(highscore);
		});
        
        levelsButton.setPrefSize(100, 30);
        levelsButton.setOnAction(e -> {
        	stage.setScene(new LevelSelectionState(stage));
		});
		
		infoLabel = new Label(infoText);
		
		try {
			Font font = Font.loadFont(new FileInputStream(new File("GHOSTBUS.TTF")), 20);
			titleLabel = new Label("Block Busters");
			titleLabel.setAlignment(Pos.CENTER);
			titleLabel.setPadding(new Insets(50, 0, 0, 320 / 4 - titleLabel.getWidth() / 4));
			titleLabel.setFont(font);
			((BorderPane)getRoot()).setTop(titleLabel);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		((BorderPane)getRoot()).setBottom(infoLabel);
		
	}
	
	//Reads save file and sets levels in Main
	private boolean readSaveFile(File file) {
		try {
			Scanner sc = new Scanner(file);
			
			while (sc.hasNext()) {
				String levelName = sc.next();
				boolean isCompleted = sc.nextBoolean();
				int highScore = sc.nextInt();
				
				Level l = new Level(new File("levels/"+levelName+".txt"));
				l.setHighscore(highScore);
				l.setDone(isCompleted);
				
				Main.levels.set(l.getId() - 1, l);
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (InputMismatchException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	//Sets variables in levels to their default values
	private void clearLevels() {
		for (Level level : Main.levels) {
			level.setDone(false);
			level.setHighscore(0);
			level.setScore(0);
		}
	}

}
