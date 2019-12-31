package com.zero.maze.dto;

import java.io.Serializable;
import java.util.LinkedList;

import com.zero.maze.entity.Coordinate;

public class MazeDto implements Serializable {
    private static final long serialVersionUID = -6063955273752632069L;

    private int[][] mazeMap;
    private int row;
    private int col;
    private Coordinate entrance;    
    private Coordinate exit;         
    private LinkedList<Coordinate> path;
    
    public MazeDto() {
        
    }

    public MazeDto(int[][] mazeMap, int row, int col, Coordinate entrance, Coordinate exit,
            LinkedList<Coordinate> path) {
        this.mazeMap = mazeMap;
        this.row = row;
        this.col = col;
        this.entrance = entrance;
        this.exit = exit;
        this.path = path;
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

    public LinkedList<Coordinate> getPath() {
        return path;
    }

    public void setPath(LinkedList<Coordinate> path) {
        this.path = path;
    }
}
