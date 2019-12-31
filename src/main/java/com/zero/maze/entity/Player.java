package com.zero.maze.entity;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = -8278548961104433928L;
	
	private String playerName;
	private int[][] mazeMap;
	private int row;
	private int col;
	private int playerX;
	private int playerY;
	private boolean hasGoneThrough;
	
	public Player() {

	}

	public Player(String playerName, int[][] mazeMap, int row, int col, int playerX, int playerY, boolean hasGoneThrough) {
		this.playerName = playerName;
		this.mazeMap = mazeMap;
		this.row = row;
		this.col = col;
		this.playerX = playerX;
		this.playerY = playerY;
		this.hasGoneThrough = hasGoneThrough;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int[][] getMazeMap() {
		return mazeMap;
	}

	public void setMazeMap(int[][] mazeMap) {
		this.mazeMap = mazeMap;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getPlayerX() {
		return playerX;
	}

	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}

	public boolean isHasGoneThrough() {
		return hasGoneThrough;
	}

	public void setHasGoneThrough(boolean hasGoneThrough) {
		this.hasGoneThrough = hasGoneThrough;
	}
}
