package com.zero.maze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zero.maze.dto.BaseResultDto;
import com.zero.maze.entity.Player;
import com.zero.maze.service.PlayerService;

import net.sf.json.JSONArray;


@Controller
@RequestMapping("/player")
@CrossOrigin
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	@RequestMapping(value = "/savePlayerInfo")
	@ResponseBody
	public BaseResultDto<String> savePlayerInfo(@RequestParam String playerName, @RequestParam String mazeMap,
			@RequestParam Integer row, @RequestParam Integer col, @RequestParam Integer playerX, 
			@RequestParam Integer playerY, @RequestParam Boolean hasGoneThrough) {
		
		String mazeMapJsonStr = mazeMap;
		int[][] map = new int[row][col];
		
		JSONArray jsonArray = JSONArray.fromObject(mazeMapJsonStr);
		for (int i = 0; i < jsonArray.size(); ++i) {
			JSONArray array = (JSONArray) jsonArray.get(i);
			for (int j = 0; j < array.size(); j++) {
				map[i][j] = array.getInt(j);
			}
		}
		
		Player player = new Player(playerName, map, row, col, playerX, playerY, hasGoneThrough);
		return playerService.savePlayerInfo(player);
	}

	@RequestMapping(value = "/loadPlayerInfo")
	@ResponseBody
	public BaseResultDto<Player> loadPlayerInfo(@RequestParam String playerName) {
		return playerService.loadPlayerInfo(playerName);
	}
}
