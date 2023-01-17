package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import states.MenuState;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//Ahmet Eren Çolak - 150120019
//Mert Sezer Oktay - 150120017

public class Main extends Application {

	public static Image wallImage, emptyImage, mirrorImage, woodImage;
	public static ArrayList<Level> levels = new ArrayList<>();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Load images and levels
			wallImage = new Image(new FileInputStream(new File("images/wall01.jpg")));
			emptyImage = new Image(new FileInputStream(new File("images/empty01.jpg")));
			mirrorImage = new Image(new FileInputStream(new File("images/mirror01.jpg")));
			woodImage = new Image(new FileInputStream(new File("images/wood01.jpg")));
			
			File levels = new File("levels/");
			for (int i = 0; i < levels.list().length; i++) {
				Main.levels.add(new Level(new File("levels/level" + (i + 1) + ".txt")));
			}

			primaryStage.setScene(new MenuState(primaryStage));
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
