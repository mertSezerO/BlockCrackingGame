package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Ahmet Eren Çolak - 150120019
//Mert Sezer Oktay - 150120017

//Base class for cell objects
public abstract class Cell extends ImageView {
	public static final int CELL_SIZE = 32;
	protected int cellX, cellY;
	
	public Cell(Image image, int cellX, int cellY) {
		setImage(image);
		this.cellX = cellX;
		this.cellY = cellY;
	}
	
	//Returns cell that will replace this cell object after breaking
	public abstract Cell next(); 
	public abstract boolean isBreakable();

	public int getCellX() {
		return cellX;
	}

	public void setCellX(int cellX) {
		this.cellX = cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public void setCellY(int cellY) {
		this.cellY = cellY;
	}
}

class WallCell extends Cell {
	public WallCell(int x, int y) {
		super(Main.wallImage, x, y);
	}
	
	public boolean isBreakable() {
		return false;
	}
	
	public Cell next() {
		return new WallCell(cellX, cellY) ;
	}
}

class EmptyCell extends Cell{
	public EmptyCell(int x, int y) {
		super(Main.emptyImage, x, y);
	}
	
    public boolean isBreakable() {
    	return false;
    }
    
   	public Cell next() {
   		return new EmptyCell(cellX, cellY) ;
   	}
}

class WoodCell extends Cell {
	public WoodCell(int x, int y) {
		super(Main.woodImage, x, y);
	}
	
	public boolean isBreakable() {
		return true;
    }
   
	public Cell next() {
		return new MirrorCell(cellX, cellY) ;
	}
}

class MirrorCell extends Cell {
	public MirrorCell(int x, int y) {
		super(Main.mirrorImage, x, y);
	}
	
	public boolean isBreakable() {
	   return true;
    }
	
	public Cell next() {
		return new EmptyCell(cellX, cellY);
	}
}