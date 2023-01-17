package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Ahmet Eren Çolak - 150120019
//Mert Sezer Oktay - 150120017

//Level class represents the levels
public class Level {
	private Cell[][] defaultCells = new Cell[10][10];
	private Cell[][] cells = new Cell[10][10];
	
	private int id;
	private int score = 0;
	private int highscore;
	private boolean isDone = false;
	private String info;
	
	public Level(File file) {
		for (int y = 0; y < cells.length; y++) {
			for (int x = 0; x < cells[y].length; x++) {
				cells[y][x] = new WallCell(x, y);
				defaultCells[y][x] = new WallCell(x, y);
			}
		}
		
		parseLevelFile(file);
	}
	
	//Sets cells array and id number from file parameter
	private void parseLevelFile(File file) {
		try (Scanner sc = new Scanner(file)) {
			sc.useDelimiter(",|\r\n");
			
			String fileName = file.getName();
			id = Integer.parseInt(fileName.substring(5, fileName.length() - 4));
			
			while (sc.hasNext()) {
				String cellType = sc.next();
				int cellY = sc.nextInt();
				int cellX = sc.nextInt();
				
				switch (cellType) {
					case "Empty":
						cells[cellY][cellX] = new EmptyCell(cellX, cellY);
						defaultCells[cellY][cellX] = new EmptyCell(cellX, cellY);
						break;
					case "Wood":
						cells[cellY][cellX] = new WoodCell(cellX, cellY);
						defaultCells[cellY][cellX] = new WoodCell(cellX, cellY);
						break;
					case "Mirror":
						cells[cellY][cellX] = new MirrorCell(cellX, cellY);
						defaultCells[cellY][cellX] = new MirrorCell(cellX, cellY);
						break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Breaks cells with its breakable neighbours, calculates score and info string, if cell is not breakable returns false
	public boolean breakCell(int x, int y) {
		if (!cellAt(x, y).isBreakable()) {
			info = String.format("(%d,%d) Block is not breakable", x, y);
			return false;
		}
		
		Cell[] neighbours = {cellAt(x, y), cellAt(x+1, y), cellAt(x-1, y), cellAt(x, y+1), cellAt(x, y-1)};
		info = String.format("Box: (%d,%d)", x, y);
		
		int breakableCount = 0;
		for (Cell cell : neighbours) {
			if (cell == null) continue;
			
			if (cell.isBreakable()) {
				breakableCount++;
				info += String.format(" - Hit: (%d,%d)", x, y);
			}
			
			cells[cell.getCellY()][cell.getCellX()] = cell.next(); //update cell with next
		}
		
		int scoreGain = 0;
		
		switch (breakableCount) {
			case 1: scoreGain = -3; break;
			case 2: scoreGain = -1; break;
			case 3: scoreGain = 1; break;
			case 4: scoreGain = 2; break;
			case 5: scoreGain = 3; break;
		}
		
		score += scoreGain;
		info += String.format("(%+d points)", scoreGain);
		
		return true;
	}
	
	//Returns cell at in cells array
	public Cell cellAt(int x, int y) {
		try {
			return cells[y][x];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	//Reset level
	public void setToDefault() {
		score = 0;
		
		for (int y = 0; y < cells.length; y++)
			for (int x = 0; x < cells[y].length; x++)
				cells[y][x] = defaultCells[y][x];
	}
	
	//Returns true if level is completed and sets isDone to true, otherwise returns false
	public boolean checkCompleted() {
		for (Cell[] cellRow : cells) {
			for (Cell cell : cellRow) {
				if (cell.isBreakable()) { 
					return false;
				}
			}
		}
		
		return isDone = true;
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	public int getId() {
		return id;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getHighscore() {
		return highscore;
	}
	
	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}
	
	public String getInfo() {
		return info;
	}
	
	public Cell[][] getCells() {
		return cells;
	}

}
