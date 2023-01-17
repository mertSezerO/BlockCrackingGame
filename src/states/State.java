package states;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Ahmet Eren Çolak - 150120019
//Mert Sezer Oktay - 150120017

//Base class for states in game
public abstract class State extends Scene {
	protected Stage stage;
	
	public State(Stage stage, Parent root) {
		super(root, 320, 400);
		this.stage = stage;
		initState();
	}
	
	protected abstract void initState();

}
