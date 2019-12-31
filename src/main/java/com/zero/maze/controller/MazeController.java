package com.zero.maze.controller;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zero.maze.dto.BaseResultDto;
import com.zero.maze.dto.MazeDto;
import com.zero.maze.entity.Coordinate;
import com.zero.maze.service.MazeService;

@Controller
@RequestMapping(value = "/maze")
@CrossOrigin
public class MazeController {
	
	@Autowired
	private MazeService mazeService;
	
	@GetMapping(value = "/getMaze")
	@ResponseBody
	public BaseResultDto<MazeDto> getMaze(@RequestParam Integer row, @RequestParam Integer col, 
			@RequestParam(required = false) Integer pattern) {
		return mazeService.getMaze(row, col, pattern);
	}
	
	@GetMapping(value = "/getMazePath")
	@ResponseBody
	public LinkedList<Coordinate> getMazePath(@RequestParam int[][] mazeMap, @RequestParam int row, @RequestParam int col) {
		return mazeService.getMazePath(mazeMap, row, col);
	}
}
