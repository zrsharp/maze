package com.zero.maze.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.zero.maze.entity.Coordinate;
import com.zero.maze.entity.Maze;

public class MazeFactory {

	private static MazeFactory mazeFactory = new MazeFactory();

	private MazeFactory() {

	}

	public static MazeFactory getInstance() {
		return mazeFactory;
	}

	public Maze createMaze(Integer row, Integer col) {
		return createMaze(row, col, 1);
	}

	public Maze createMaze(Integer row, Integer col, Integer pattern) {
		if (pattern == null) {
			pattern = 1;
		}

		Class<Maze> mazeClazz = Maze.class;
		try {
			Constructor<Maze> mazeConstructor = mazeClazz.getDeclaredConstructor(Integer.class, Integer.class);
			mazeConstructor.setAccessible(true);
			Maze maze = mazeConstructor.newInstance(row, col);
			Coordinate entrance = new Coordinate();
			Coordinate exit = new Coordinate();

			int mazeMap[][] = null;
			switch (pattern) {
			case 1:
				mazeMap = this.createMazeMapPrim(row, col, entrance, exit);
				break;
			case 2:
				mazeMap = this.createMazeMapDfs(row, col, entrance, exit);
				break;
			case 3:
				mazeMap = this.createMazeMapRecursiveSubdivision(row, col, entrance, exit);
				break;
			default:
				mazeMap = this.createMazeMapPrim(row, col, entrance, exit);
				break;
			}

			maze.setMazeMap(mazeMap);
			maze.setEntrance(entrance);
			maze.setExit(exit);

			return maze;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Maze createMaze(int[][] mazeMap, Integer row, Integer col) {
		Class<Maze> mazeClazz = Maze.class;
		try {
			Constructor<Maze> mazeConstructor = mazeClazz.getDeclaredConstructor();
			mazeConstructor.setAccessible(true);
			Maze maze = mazeConstructor.newInstance();

			maze.setMazeMap(mazeMap);
			maze.setRow(row);
			maze.setCol(col);

			for (int i = 0; i < row; ++i) { // 找入口和出口
				for (int j = 0; j < col; ++j) {
					if (mazeMap[i][j] == 2) {
						maze.setEntrance(new Coordinate(j, i));
					}

					if (mazeMap[i][j] == 3) {
						maze.setExit(new Coordinate(j, i));
					}
				}
			}

			return maze;

		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 稀疏复杂的地图
	 * 
	 * @param row
	 * @param col
	 * @param entrance
	 * @param exit
	 * @return
	 */
	public int[][] createMazeMapPrim(int row, int col, Coordinate entrance, Coordinate exit) {
		int[][] mazeMap = new int[row + 2][col + 2]; // 为了方便,再加一圈,并设为路
		for (int i = 0; i < row + 2; ++i) {
			for (int j = 0; j < col + 2; j++) {
				if (i == 0 || i == row + 1 || j == 0 || j == col + 1) {
					mazeMap[i][j] = 1;
				} else {
					mazeMap[i][j] = 0;
				}
			}
		}

		List<Coordinate> walls = new LinkedList<>();
		Coordinate pos = new Coordinate(2, 2);        // 先选择(1,1)为遍历开始的点
		walls.add(pos);
		Random random = new Random();
		while (!walls.isEmpty()) {
			int index = random.nextInt(walls.size()); // 从队列中随机选择一个点
			pos = walls.get(index);
			walls.remove(index);        // 删除处理过的墙壁
			int x = pos.getX();
			int y = pos.getY();

			int count = 0; // 用于统计某个坐标上下左右路的个数
			if (mazeMap[y - 1][x] == 1) {
				++count;
			}
			if (mazeMap[y + 1][x] == 1) {
				++count;
			}
			if (mazeMap[y][x - 1] == 1) {
				++count;
			}
			if (mazeMap[y][x + 1] == 1) {
				++count;
			}

			if (count <= 1) { // 如果上下左右只有一个点是路,则打通,并把上下左右的墙加入队列中
				mazeMap[y][x] = 1;

				if (mazeMap[y - 1][x] == 0) {
					walls.add(new Coordinate(x, y - 1));
				}
				if (mazeMap[y + 1][x] == 0) {
					walls.add(new Coordinate(x, y + 1));
				}
				if (mazeMap[y][x - 1] == 0) {
					walls.add(new Coordinate(x - 1, y));
				}
				if (mazeMap[y][x + 1] == 0) {
					walls.add(new Coordinate(x + 1, y));
				}
			}
		}

		for (int i = 2; i <= row - 1; ++i) {
			if (mazeMap[i][2] == 1) {
				mazeMap[i][1] = 2;
				entrance.setX(1 - 1);
				entrance.setY(i - 1);
				break;
			}
		}

		for (int i = row - 1; i >= 2; --i) {
			if (mazeMap[i][col - 1] == 1) {
				mazeMap[i][col] = 3;
				exit.setX(col - 1);
				exit.setY(i - 1);
				break;
			}
		}

		// 把辅助用的最外层的路删除
		int[][] mazeMap1 = new int[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; j++) {
				mazeMap1[i][j] = mazeMap[i + 1][j + 1];
			}
		}
		return mazeMap1;
	}

	/**
	 * 稍微整齐的地图
	 * 
	 * @param row
	 * @param col
	 * @param entrance
	 * @param exit
	 * @return
	 */
	public synchronized int[][] createMazeMapDfs(int row, int col, Coordinate entrance, Coordinate exit) {
		int[][] mazeMap = new int[row + 2][col + 2]; // 为了方便,再加一圈,并设为路
		for (int i = 0; i < row + 2; ++i) {
			for (int j = 0; j < col + 2; j++) {
				if (i == 0 || i == row + 1 || j == 0 || j == col + 1) {
					mazeMap[i][j] = 1;
				} else {
					mazeMap[i][j] = 0;
				}
			}
		}

		this.dfs(mazeMap, 2, 2, 0);   // (2, 2) 为起点

		// 找入口
		for (int i = 2; i <= row - 1; ++i) {
			if (mazeMap[i][2] == 1) {
				mazeMap[i][1] = 2;
				entrance.setX(1 - 1);
				entrance.setY(i - 1);
				break;
			}
		}

		// 找出口
		for (int i = row - 1; i >= 2; --i) {
			if (mazeMap[i][col - 1] == 1) {
				mazeMap[i][col] = 3;
				exit.setX(col - 1);
				exit.setY(i - 1);
				break;
			}
		}

		// 把辅助用的最外层的路删除
		int[][] mazeMap1 = new int[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; j++) {
				mazeMap1[i][j] = mazeMap[i + 1][j + 1];
			}
		}
		return mazeMap1;
	}

	/**
	 * <p>
	 * 随机选择三面墙打通，这时原本隔开的四个区域又被打通了。以此类推， 在四个新区域内继续设墙分割区域，然后把墙打通，直到不能继续分割才结束
	 * </p>
	 * 
	 * @param row
	 * @param col
	 * @param entrance
	 * @param exit
	 * @return
	 */
	private int[][] createMazeMapRecursiveSubdivision(int row, int col, Coordinate entrance, Coordinate exit) {
		int[][] mazeMap = new int[row][col];

		for (int i = 0; i < row; ++i) {      // 最外层设为墙壁, 其他都是路
			for (int j = 0; j < col; j++) {
				if (i == 0 || i == row - 1 || j == 0 || j == col - 1) {
					mazeMap[i][j] = 0;
				} else {
					mazeMap[i][j] = 1;
				}
			}
		}

		this.recursiveSubdivision(mazeMap, 1, 1, col - 2, row - 2);

		// 找入口
		for (int i = 1; i <= row - 2; ++i) {
			if (mazeMap[i][1] == 1) {
				mazeMap[i][0] = 2;
				entrance.setX(0);
				entrance.setY(i);
				break;
			}
		}

		// 找出口
		for (int i = row - 2; i >= 1; --i) {
			if (mazeMap[i][col - 2] == 1) {
				mazeMap[i][col - 1] = 3;
				exit.setX(col - 1);
				exit.setY(i);
				break;
			}
		}

		return mazeMap;
	}

	private void dfs(int[][] mazeMap, int x, int y, int rank) {
		mazeMap[y][x] = 1;
		Random random = new Random();
		int[][] direction = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		for (int i = 0; i < 4; ++i) {
			int r = random.nextInt(4);

			int temp = direction[0][0];
			direction[0][0] = direction[r][0];
			direction[r][0] = temp;

			temp = direction[0][1];
			direction[0][1] = direction[r][1];
			direction[r][1] = temp;
		}

		for (int i = 0; i < 4; ++i) {
			int dx = x;
			int dy = y;

			int range = 1 + (rank == 0 ? 0 : random.nextInt(rank)); // 搜索范围
			while (range > 0) {
				dy += direction[i][0];
				dx += direction[i][1];

				if (mazeMap[dy][dx] == 1) { // 目标已经被打通了，则退出
					break;
				}

				int count = 0; // 用于统计某个坐标上下左右路的个数
				if (mazeMap[dy - 1][dx] == 1) {
					++count;
				}
				if (mazeMap[dy + 1][dx] == 1) {
					++count;
				}
				if (mazeMap[dy][dx - 1] == 1) {
					++count;
				}
				if (mazeMap[dy][dx + 1] == 1) {
					++count;
				}

				if (count > 1) {
					break;            // 超过一条路本次不做处理
				}

				mazeMap[dy][dx] = 1;  // 否则将这个墙打通
				--range;
			}

			if (range <= 0) {
				dfs(mazeMap, dx, dy, rank);
			}
		}
	}

	/**
	 * 尚未完成
	 * @deprecated
	 */
	private void recursiveSubdivision(int[][] mazeMap, int x1, int y1, int x2, int y2) {
		if (y2 - y1 < 2 || x2 - x1 < 2) {
			return;
		}

		Random random = new Random();
		int x = x1 + 1 + random.nextInt(x2 - x1 - 1);
		int y = y1 + 1 + random.nextInt(y2 - y1 - 1);

		// 设置墙壁
		for (int i = y1; i <= y2; ++y) {
			mazeMap[i][x] = 0;
		}
		for (int i = x1; i <= x2; ++i) {
			mazeMap[y][i] = 0;
		}

		// 递归分割
		recursiveSubdivision(mazeMap, x1, y1, x - 1, y - 1);
		recursiveSubdivision(mazeMap, x + 1, y + 1, x2, y2);
		recursiveSubdivision(mazeMap, x + 1, y1, x2, y - 1);
		recursiveSubdivision(mazeMap, x1, y + 1, x - 1, y2);

		// 随机取其中的三面墙, 0表示墙，只要把其中一个设置为路即可
		int r[] = { 0, 0, 0, 0 };
		r[random.nextInt(4)] = 1;
		
		for (int i = 0; i < 4; ++i) {
			if (r[i] == 1) {
				continue;
			}
			
			//如果是墙，则进入下面的逻辑
			int ry = y;
			int rx = x;
			
			switch (i) {
			case 0:
				
				break;
			case 1:
				
				break;
				
			case 2:
				
				break;
				
			case 3:
				
				break;
			default:
				break;
			}
			
			mazeMap[ry][rx] = 1;
		}

	}
}
