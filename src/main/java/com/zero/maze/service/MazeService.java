package com.zero.maze.service;

import java.util.LinkedList;

import org.springframework.stereotype.Service;

import com.zero.maze.dto.BaseResultDto;
import com.zero.maze.dto.MazeDto;
import com.zero.maze.entity.Coordinate;
import com.zero.maze.entity.Maze;
import com.zero.maze.entity.MazeRunner;
import com.zero.maze.factory.MazeFactory;

@Service
public class MazeService {
	public BaseResultDto<MazeDto> getMaze(Integer row, Integer col, Integer pattern) {
		BaseResultDto<MazeDto> result = new BaseResultDto<>();
		
		if (row == null || col == null || row <= 5 || col <= 5 || row > 500 || col > 500) {
			result.setSuccess(false);
			result.setMessage("行和列必须 > 5 且 <= 500");
			result.setData(null);
			return result;
		}
		
		Maze maze = MazeFactory.getInstance().createMaze(row, col, pattern);
		MazeDto mazeDto = new MazeDto();
		mazeDto.setMazeMap(maze.getMazeMap());
		mazeDto.setRow(row);
		mazeDto.setCol(col);
		mazeDto.setEntrance(maze.getEntrance());
		mazeDto.setExit(maze.getExit());
		
		MazeRunner mazeRunner = new MazeRunner();
		LinkedList<Coordinate> path = mazeRunner.getShortestPath(maze);
		mazeDto.setPath(path);
		
		result.setData(mazeDto);
		result.setSuccess(true);
		result.setMessage("迷宫获取成功");
		return result;
	}
	
	public LinkedList<Coordinate> getMazePath(int[][] mazeMap, int row, int col) {
		if (mazeMap == null || row != mazeMap.length || col != mazeMap[0].length) {
			return null;
		}
		
		MazeRunner mazeRunner = new MazeRunner();
		Maze maze = MazeFactory.getInstance().createMaze(mazeMap, row, col);
		LinkedList<Coordinate> path = mazeRunner.getShortestPath(maze);
		return path;
	}
}
