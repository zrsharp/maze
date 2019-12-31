package com.zero.maze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zero.maze.entity.Maze;
import com.zero.maze.factory.MazeFactory;
import com.zero.maze.service.MazeService;
import com.zero.maze.service.PlayerService;

@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    MazeService mazeService;
    
    @Autowired
    PlayerService playerService;
    
    @RequestMapping("/test")
    @ResponseBody
    @CrossOrigin
    public void test() {
        Maze maze = MazeFactory.getInstance().createMaze(35, 40, 2);
        maze.printMazeMap();
    }
}
