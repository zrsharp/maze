package com.zero.maze.entity;

public class Maze {
	private int[][] mazeMap;
	private int row;
	private int col;
	private Coordinate entrance; // 入口
	private Coordinate exit;     // 出口

	private Maze() {

	}

	private Maze(Integer row, Integer col) {
		this.row = row;
		this.col = col;
	}

	public void printMazeMap() {
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				System.out.print((mazeMap[i][j] == 0 ? "██" : "  "));
			}
			System.out.println();
		}
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

	public Coordinate getEntrance() {
		return entrance;
	}

	public void setEntrance(Coordinate entrance) {
		this.entrance = entrance;
	}

	public Coordinate getExit() {
		return exit;
	}

	public void setExit(Coordinate exit) {
		this.exit = exit;
	}

}
