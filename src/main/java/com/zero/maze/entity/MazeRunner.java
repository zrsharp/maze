package com.zero.maze.entity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class MazeRunner {
	public LinkedList<Coordinate> getShortestPath(Maze maze) {
		Queue<Coordinate> queue = new LinkedList<Coordinate>();
		boolean[][] visited = new boolean[maze.getRow()][maze.getCol()];
		Coordinate[][] pre = new Coordinate[maze.getRow()][maze.getCol()];

		for (int i = 0; i < maze.getRow(); ++i) {
			for (int j = 0; j < maze.getCol(); j++) {
				visited[i][j] = false;
				pre[i][j] = new Coordinate(-1, -1);
			}
		}

		queue.add(maze.getEntrance()); // 入口坐标入队
		visited[maze.getEntrance().getY()][maze.getEntrance().getX()] = true;
		while (!queue.isEmpty()) {
			Coordinate pos = queue.remove(); // 第一个坐标出队
			int x = pos.getX();
			int y = pos.getY();
			
			if (y <= maze.getRow() - 2 && (maze.getMazeMap()[y + 1][x] == 1 || maze.getMazeMap()[y + 1][x] == 3) && !visited[y + 1][x]) {
				visited[y + 1][x] = true;
				pre[y + 1][x].setX(x);
				pre[y + 1][x].setY(y);
				
				if (maze.getMazeMap()[y + 1][x] == 3) {
					break;
				}
				queue.add(new Coordinate(x, y + 1));
			}

			if (y > 0 && (maze.getMazeMap()[y - 1][x] == 1 || maze.getMazeMap()[y - 1][x] == 3) && !visited[y - 1][x]) {
				visited[y - 1][x] = true;
				pre[y - 1][x].setX(x);
				pre[y - 1][x].setY(y);
				if (maze.getMazeMap()[y - 1][x] == 3) {
					break;
				}
				queue.add(new Coordinate(x, y - 1));
			}

			if (x <= maze.getCol() - 2 && (maze.getMazeMap()[y][x + 1] == 1 || maze.getMazeMap()[y][x + 1] == 3) && !visited[y][x + 1]) {
				visited[y][x + 1] = true;
				pre[y][x + 1].setX(x);
				pre[y][x + 1].setY(y);
				if (maze.getMazeMap()[y][x + 1] == 3) {
					break;
				}
				queue.add(new Coordinate(x + 1, y));
			}

			if (x > 0 && (maze.getMazeMap()[y][x - 1] == 1 || maze.getMazeMap()[y][x - 1] == 3) && !visited[y][x - 1]) {
				visited[y][x - 1] = true;
				pre[y][x - 1].setX(x);
				pre[y][x - 1].setY(y);
				if (maze.getMazeMap()[y][x - 1] == 3) {
					break;
				}
				queue.add(new Coordinate(x - 1, y));
			}
		}
		
		LinkedList<Coordinate> path = this.createPath(maze, pre);  // 获取路径列表
		
		return path;
	}
	
	private LinkedList<Coordinate> createPath(Maze maze, Coordinate[][] pre) {
		
		Stack<Coordinate> shortestPathStack = new Stack<Coordinate>();
		Coordinate pos = maze.getExit();
		
		while (!pos.equals(maze.getEntrance())) {
			shortestPathStack.push(pos);          // 从尾结点开始入栈
			pos = pre[pos.getY()][pos.getX()];
		}
		
		shortestPathStack.push(pos);              // 直到头结点入栈
		
		// 开始构造出口序列
		LinkedList<Coordinate> path = new LinkedList<Coordinate>();
		while (!shortestPathStack.empty()) {
			path.add(shortestPathStack.pop());    // 出栈并放入列表中
		}
		
		return path;
	}
}
